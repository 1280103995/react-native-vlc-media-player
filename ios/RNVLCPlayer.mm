#ifdef RCT_NEW_ARCH_ENABLED
#import "RNVLCPlayer.h"
#import <React/RCTConversions.h>
#import <react/renderer/components/rnvlcplayer/ComponentDescriptors.h>
#import <react/renderer/components/rnvlcplayer/EventEmitters.h>
#import <react/renderer/components/rnvlcplayer/Props.h>
#import <react/renderer/components/rnvlcplayer/RCTComponentViewHelpers.h>
#import "RCTFabricComponentsPlugins.h"
#import <MobileVLCKit/MobileVLCKit.h>
#import <AVFoundation/AVFoundation.h>

using namespace facebook::react;

@interface RNVLCPlayer () <RCTRNVLCPlayerViewProtocol, VLCMediaPlayerDelegate>
@end

@implementation RNVLCPlayer {
    VLCMediaPlayer *_player;
    NSDictionary *_source;
    BOOL _paused;
    int _preVolume;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
  return concreteComponentDescriptorProvider<RNVLCPlayerComponentDescriptor>();
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
      static const auto defaultProps = std::make_shared<const RNVLCPlayerProps>();
      _props = defaultProps;

      _player = [[VLCMediaPlayer alloc] init];
      [_player setDrawable:self];
      _player.delegate = self;
      _player.scaleFactor = 0;

      _paused = YES;

      [[AVAudioSession sharedInstance] setActive:NO withOptions:AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation error:nil];

      [[NSNotificationCenter defaultCenter] addObserver:self
                                               selector:@selector(applicationWillResignActive:)
                                                   name:UIApplicationWillResignActiveNotification
                                                 object:nil];

      [[NSNotificationCenter defaultCenter] addObserver:self
                                               selector:@selector(applicationWillEnterForeground:)
                                                   name:UIApplicationWillEnterForegroundNotification
                                                 object:nil];
  }

  return self;
}

- (void)applicationWillResignActive:(NSNotification *)notification
{
    if (!_paused) {
        [self setPaused:_paused];
    }
}

- (void)applicationWillEnterForeground:(NSNotification *)notification
{
    [self applyModifiers];
}

- (void)applyModifiers
{
    if(!_paused)
        [self play];
}

- (void)setPaused:(BOOL)paused
{
    if(_player){
        if(!paused){
            [self play];
        }else {
            [_player pause];
            _paused = YES;
        }
    }
}

-(void)play
{
    if(_player){
        [_player play];
        _paused = NO;
    }
}

- (std::shared_ptr<const RNVLCPlayerEventEmitter>)getEventEmitter
{
    if (!self->_eventEmitter) {
      return nullptr;
    }

    assert(std::dynamic_pointer_cast<RNVLCPlayerEventEmitter const>(self->_eventEmitter));
    return std::static_pointer_cast<RNVLCPlayerEventEmitter const>(self->_eventEmitter);
}

-(void)setSource:(NSDictionary *)source
{
    _source = source;

    NSString *uri = [source objectForKey:@"uri"];
    NSURL *url = [NSURL URLWithString:uri];
    VLCMedia *media = [VLCMedia mediaWithURL:url];

    NSDictionary* initOptions = [source objectForKey:@"initOptions"];
    for (NSString* option in initOptions) {
        [media addOption:[option stringByReplacingOccurrencesOfString:@"--" withString:@""]];
    }

    _player.media = media;

    const auto eventEmitter = [self getEventEmitter];
    if (eventEmitter) {
        eventEmitter->onStateChange(RNVLCPlayerEventEmitter::OnStateChange{
            .isPlaying = !_paused,
            .position = 0,
            .currentTime = 0,
            .duration = 0,
            .bufferRate = static_cast<Float>(0),
            .type = RNVLCPlayerEventEmitter::OnStateChangeType::Start
        });
    }
}

- (void)mediaPlayerTimeChanged:(NSNotification *)aNotification
{
    if(_player){
        int currentTime   = [[_player time] intValue];
        int duration      = [_player.media.length intValue];

        if( currentTime >= 0 && currentTime < duration) {
            const auto eventEmitter = [self getEventEmitter];
            if (eventEmitter) {
                eventEmitter->onStateChange(RNVLCPlayerEventEmitter::OnStateChange{
                    .isPlaying = !_paused,
                    .position = _player.position,
                    .currentTime = static_cast<Float>(currentTime),
                    .duration = static_cast<Float>(duration),
                    .bufferRate = static_cast<Float>(0),
                    .type = RNVLCPlayerEventEmitter::OnStateChangeType::TimeChanged
                });
            }
        }
    }
}

- (void)mediaPlayerStateChanged:(NSNotification *)aNotification
{
    if(_player){
        int currentTime   = [[_player time] intValue];
        int duration      = [_player.media.length intValue];
        RNVLCPlayerEventEmitter::OnStateChangeType type = RNVLCPlayerEventEmitter::OnStateChangeType::Unknown;
        VLCMediaPlayerState state = _player.state;
        switch (state) {
            case VLCMediaPlayerStateOpening:
                NSLog(@"VLCMediaPlayerStateOpening %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Opening;
                break;
            case VLCMediaPlayerStatePaused:
                _paused = YES;
                NSLog(@"VLCMediaPlayerStatePaused %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Paused;
                break;
            case VLCMediaPlayerStateStopped:
                NSLog(@"VLCMediaPlayerStateStopped %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Stopped;
                break;
            case VLCMediaPlayerStateBuffering:
                NSLog(@"VLCMediaPlayerStateBuffering %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Buffering;
                break;
            case VLCMediaPlayerStatePlaying:
                _paused = NO;
                NSLog(@"VLCMediaPlayerStatePlaying %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Playing;
                break;
            case VLCMediaPlayerStateEnded:
                NSLog(@"VLCMediaPlayerStateEnded %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Ended;
                break;
            case VLCMediaPlayerStateError:
                NSLog(@"VLCMediaPlayerStateError %i",1);
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Error;
                [self _release];
                break;
            default:
                type = RNVLCPlayerEventEmitter::OnStateChangeType::Unknown;
                break;
        }
        const auto eventEmitter = [self getEventEmitter];
        if (eventEmitter) {
            eventEmitter->onStateChange(RNVLCPlayerEventEmitter::OnStateChange{
                .isPlaying = !_paused,
                .position = _player.position,
                .currentTime = static_cast<Float>(currentTime),
                .duration = static_cast<Float>(duration),
                .bufferRate = static_cast<Float>(0),
                .type = type
            });
        }
    }
}

- (void)jumpBackward:(int)interval
{
    if(interval>=0 && interval <= [_player.media.length intValue])
        [_player jumpBackward:interval];
}

- (void)jumpForward:(int)interval
{
    if(interval>=0 && interval <= [_player.media.length intValue])
        [_player jumpForward:interval];
}

-(void)setSeek:(float)pos
{
    if([_player isSeekable]){
        if(pos>=0 && pos <= 1){
            [_player setPosition:pos];
        }
    }
}

-(void)setSnapshotPath:(NSString*)path
{
    if(_player)
        [_player saveVideoSnapshotAt:path withWidth:0 andHeight:0];
}

-(void)setVideoAspectRatio:(NSString *)ratio{
    char *char_content = (char*)[ratio cStringUsingEncoding:NSASCIIStringEncoding];
    [_player setVideoAspectRatio:char_content];
}

- (void)_release
{
    if(_player){
        [_player pause];
        [_player stop];
        [[NSNotificationCenter defaultCenter] removeObserver:self];
    }
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<RNVLCPlayerProps const>(_props);
    const auto &newViewProps = *std::static_pointer_cast<RNVLCPlayerProps const>(props);

    if (oldViewProps.source.uri != newViewProps.source.uri) {
        NSMutableDictionary* source = [[NSMutableDictionary alloc] init];
        if (!newViewProps.source.uri.empty()) {
            [source setValue: RCTNSStringFromString(newViewProps.source.uri) forKey:@"uri"];
        }
        if (!newViewProps.source.initOptions.empty()) {
            NSMutableArray *array = [NSMutableArray array];
            for (auto &str : *&newViewProps.source.initOptions) {
                [array addObject: RCTNSStringFromString(str)];
            }
            [source setValue: array forKey:@"initOptions"];
        }
        [self setSource: source];
    }

    if (oldViewProps.rate != newViewProps.rate) {
        [_player setRate: newViewProps.rate];
    }

    if (oldViewProps.seek != newViewProps.seek) {
        [self setSeek: newViewProps.seek];
    }

    if (oldViewProps.volume != newViewProps.volume) {
        [[_player audio] setVolume: newViewProps.volume];
    }

    if (oldViewProps.muted != newViewProps.muted) {
        _preVolume = _player.audio.volume;
        if (newViewProps.muted) {
            [[_player audio] setVolume: 0];
        } else {
            [[_player audio] setVolume: _preVolume];
        }
    }

    if (oldViewProps.videoAspectRatio != newViewProps.videoAspectRatio) {
        [self setVideoAspectRatio: RCTNSStringFromString(newViewProps.videoAspectRatio)];
    }

    if(_paused != newViewProps.paused) {
        _paused = newViewProps.paused;
        [self setPaused: _paused];
    }

    [super updateProps:props oldProps:oldProps];
}

@end

Class<RCTComponentViewProtocol> RNVLCPlayerCls(void)
{
    return RNVLCPlayer.class;
}

#endif
