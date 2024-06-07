#import <React/RCTUIManager.h>
#import <React/RCTViewManager.h>

@interface RNVLCPlayerManager : RCTViewManager
@end

@implementation RNVLCPlayerManager

RCT_EXPORT_MODULE(RNVLCPlayer)

- (UIView *)view
{
  return [[UIView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(source, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(paused, BOOL)
RCT_EXPORT_VIEW_PROPERTY(muted, BOOL)
RCT_EXPORT_VIEW_PROPERTY(volume, float)
RCT_EXPORT_VIEW_PROPERTY(rate, float)
RCT_EXPORT_VIEW_PROPERTY(seek, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(autoAspectRatio, BOOL)
RCT_EXPORT_VIEW_PROPERTY(videoAspectRatio, NSString)

RCT_EXPORT_VIEW_PROPERTY(onStateChangeEvent, RCTDirectEventBlock)

@end
