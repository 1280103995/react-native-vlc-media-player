package com.yuanzhou.vlc;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

public class RNVLCPlayerManagerImpl {
  public static final String NAME = "RNVLCPlayer";

  public static ReactVlcPlayerView createViewInstance(ThemedReactContext context) {
    return new ReactVlcPlayerView(context);
  }

  public static Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
      VLCPlayerEvent.EVENT_NAME, MapBuilder.of("registrationName", "onStateChange"),
      VLCPlayerLayoutEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLayoutChange")
    );
  }

  public static void setRate(ReactVlcPlayerView view, float value) {
    view.setRateModifier(value);
  }

  public static void setSeek(ReactVlcPlayerView view, int value) {
    view.seekTo(Math.round(value * 1000f));
  }

  public static void setVolume(ReactVlcPlayerView view, int value) {
    view.setVolumeModifier(value);
  }

  public static void setMuted(ReactVlcPlayerView view, boolean value) {
    view.setMutedModifier(value);
  }

  public static void setPaused(ReactVlcPlayerView view, boolean value) {
    view.setPausedModifier(value);
  }

  public static void setAutoAspectRatio(ReactVlcPlayerView view, boolean value) {
    view.setAutoAspectRatio(value);
  }

  public static void setVideoAspectRatio(ReactVlcPlayerView view, @Nullable String value) {
    view.setAspectRatio(value);
  }

  public static void setSource(ReactVlcPlayerView view, @Nullable ReadableMap value) {
    String uriString = value.hasKey("uri") ? value.getString("uri") : null;
    if (!TextUtils.isEmpty(uriString)) {
      view.setSource(value);
    }
  }
}
