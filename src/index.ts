import type { NativeSyntheticEvent } from 'react-native';

export { default as VLCPlayer } from './RNVLCPlayerNativeComponent';

import type {
  VLCSource,
  VLCStateData,
  VLCLayoutData,
  VLCPlayerProps,
} from './RNVLCPlayerNativeComponent';

type VLCStateEvent = NativeSyntheticEvent<VLCStateData>;

type VLCLayoutEvent = NativeSyntheticEvent<VLCLayoutData>;

export type { VLCSource, VLCPlayerProps, VLCStateEvent, VLCLayoutEvent };
