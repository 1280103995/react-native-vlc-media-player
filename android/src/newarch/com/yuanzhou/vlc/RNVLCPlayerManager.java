package com.yuanzhou.vlc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.RNVLCPlayerManagerDelegate;
import com.facebook.react.viewmanagers.RNVLCPlayerManagerInterface;

import java.util.Map;

/**
 * Manages instances of {@code ReactVlcPlayerView}.
 */
@ReactModule(name = RNVLCPlayerManagerImpl.NAME)
public class RNVLCPlayerManager extends SimpleViewManager<ReactVlcPlayerView>
        implements RNVLCPlayerManagerInterface<ReactVlcPlayerView> {

    private final ViewManagerDelegate<ReactVlcPlayerView> mDelegate;

    public RNVLCPlayerManager() {
        mDelegate = new RNVLCPlayerManagerDelegate<>(this);
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<ReactVlcPlayerView> getDelegate() {
        return mDelegate;
    }

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
                UIManagerHelper.getEventDispatcherForReactTag(reactContext, view.getId())
                        .dispatchEvent(new VLCPlayerEvent(
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
                UIManagerHelper.getEventDispatcherForReactTag(reactContext, view.getId())
                        .dispatchEvent(event);
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
