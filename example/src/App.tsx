import React, { useState } from 'react';
import { StyleSheet, View, Text } from 'react-native';
import {VLCPlayer} from 'react-native-vlc-media-player';
import type {VLCStateEvent, VLCLayoutEvent, VLCSource } from 'react-native-vlc-media-player';

export default function App() {
  const [paused, setPaused] = useState(false);
  const source: VLCSource = {
    uri: 'https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4',
    initOptions: ['-vvv', '--input-repeat=1000'],
  };
  const onStateChange = (event: VLCStateEvent) => {
    console.log('VLC Player StateChange', event.nativeEvent);
  };
  const onLayoutChange = (event: VLCLayoutEvent) => {
    console.log('VLC Player LayoutChange', event.nativeEvent);
  };
  return (
    <View style={styles.container}>
      <VLCPlayer
        paused={paused}
        source={source}
        style={{ width: '100%', height: 230, marginBottom: 20}}
        onStateChange={onStateChange}
        onLayoutChange={onLayoutChange}
      />
      <Text
        style={{ padding: 10, borderColor: 'gray', borderWidth: 1 }}
        onPress={() => {
          setPaused(!paused);
        }}
      >
        {paused ? 'Play' : 'Pause'}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
