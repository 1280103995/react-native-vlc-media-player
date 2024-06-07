package com.yuanzhou.vlc;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;

public class VLCPlayerEvent extends Event<VLCPlayerEvent> {
    public static final String EVENT_NAME = "topStateChange";
    private final boolean isPlaying;
    private final double position;
    private final double currentTime;
    private final double duration;
    private final double bufferRate;
    private final String type;

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    public VLCPlayerEvent(int viewTag, boolean isPlaying, double position, double currentTime, double duration, double bufferRate, String type) {
        super(viewTag);
        this.isPlaying = isPlaying;
        this.position = position;
        this.currentTime = currentTime;
        this.duration = duration;
        this.bufferRate = bufferRate;
        this.type = type;
    }

    @Nullable
    @Override
    protected WritableMap getEventData() {
        return serializeEventData();
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putBoolean("isPlaying", isPlaying);
        eventData.putDouble("position", position);
        eventData.putDouble("currentTime", currentTime);
        eventData.putDouble("duration", duration);
        eventData.putDouble("bufferRate", bufferRate);
        eventData.putString("type", type);
        return eventData;
    }
}
