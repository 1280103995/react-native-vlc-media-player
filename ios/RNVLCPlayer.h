// This guard prevent this file to be compiled in the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#import <React/RCTComponent.h>
#import <UIKit/UIKit.h>

#ifndef VlcMediaPlayerViewNativeComponent_h
#define VlcMediaPlayerViewNativeComponent_h

NS_ASSUME_NONNULL_BEGIN

@interface RNVLCPlayer : RCTViewComponentView

@property (nonatomic, copy) RCTBubblingEventBlock onVideoStateChange;

@end

NS_ASSUME_NONNULL_END

#endif /* VlcMediaPlayerViewNativeComponent_h */
#endif /* RCT_NEW_ARCH_ENABLED */
