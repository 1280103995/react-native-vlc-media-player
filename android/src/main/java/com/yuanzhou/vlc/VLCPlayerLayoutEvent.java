package com.yuanzhou.vlc;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class VLCPlayerLayoutEvent extends Event<VLCPlayerLayoutEvent> {
    public static final String EVENT_NAME = "topLayoutChange";
    private Double width;
    private Double height;
    private Double visibleWidth;
    private Double visibleHeight;
    private Double sarNum;
    private Double sarDen;

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    public VLCPlayerLayoutEvent(int viewTag, Double width, Double height, Double visibleWidth, Double visibleHeight, Double sarNum, Double sarDen) {
        super(viewTag);
        this.width = width;
        this.height = height;
        this.visibleWidth = visibleWidth;
        this.visibleHeight = visibleHeight;
        this.sarNum = sarNum;
        this.sarDen = sarDen;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putDouble("videoWidth", width);
        eventData.putDouble("videoHeight", height);
        eventData.putDouble("videoVisibleWidth", visibleWidth);
        eventData.putDouble("videoVisibleHeight", visibleHeight);
        eventData.putDouble("sarNum", sarNum);
        eventData.putDouble("sarDen", sarDen);
        return eventData;
    }
}
