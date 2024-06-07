package com.yuanzhou.vlc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

/**
 * Manages instances of {@code ReactVlcPlayerView}.
 */
public class RNVLCPlayerManager extends SimpleViewManager<ReactVlcPlayerView> {

    @NonNull
    @Override
    public String getName() {
        return RNVLCPlayerManagerImpl.NAME;
    }

    @NonNull
    @Override
    protected ReactVlcPlayerView createViewInstance(@NonNull ThemedReactContext context) {
        return RNVLCPlayerManagerImpl.createViewInstance(context);
    }

    @Override
    protected void addEventEmitters(@NonNull ThemedReactContext reactContext, @NonNull ReactVlcPlayerView view) {
        view.setEventListener(new ReactVlcPlayerView.EventListener() {
            @Override
            public void onEventUpdate(boolean playing, double position, double currentTime, double duration, double bufferRate, String type) {
                reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(
                        new VLCPlayerEvent(
                                view.getId(),
                                playing,
                                position,
                                currentTime,
                                duration,
                                bufferRate,
                                type
                        ));
            }

            @Override
            public void onLayoutChange(VLCPlayerLayoutEvent event) {
                reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(event);
            }
        });
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return RNVLCPlayerManagerImpl.getExportedCustomDirectEventTypeConstants();
    }

    @ReactProp(name = "rate")
    public void setRate(ReactVlcPlayerView view, float value) {
        RNVLCPlayerManagerImpl.setRate(view, value);
    }

    @ReactProp(name = "seek")
    public void setSeek(ReactVlcPlayerView view, int value) {
        RNVLCPlayerManagerImpl.setSeek(view, Math.round(value * 1000f));
    }

    @ReactProp(name = "volume", defaultInt = 1)
    public void setVolume(ReactVlcPlayerView view, int value) {
        RNVLCPlayerManagerImpl.setVolume(view, value);
    }

    @ReactProp(name = "muted")
    public void setMuted(ReactVlcPlayerView view, boolean value) {
        RNVLCPlayerManagerImpl.setMuted(view, value);
    }

    @ReactProp(name = "paused")
    public void setPaused(ReactVlcPlayerView view, boolean value) {
        RNVLCPlayerManagerImpl.setPaused(view, value);
    }

    @ReactProp(name = "autoAspectRatio")
    public void setAutoAspectRatio(ReactVlcPlayerView view, boolean value) {
        RNVLCPlayerManagerImpl.setAutoAspectRatio(view, value);
    }

    @ReactProp(name = "videoAspectRatio")
    public void setVideoAspectRatio(ReactVlcPlayerView view, @Nullable String value) {
        RNVLCPlayerManagerImpl.setVideoAspectRatio(view, value);
    }

    @ReactProp(name = "source")
    public void setSource(ReactVlcPlayerView view, @Nullable ReadableMap value) {
        RNVLCPlayerManagerImpl.setSource(view, value);
    }
}
