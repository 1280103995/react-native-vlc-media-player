/// <reference types="react-native/types/modules/Codegen" />
import type { Float, Int32, WithDefault, DirectEventHandler } from 'react-native/Libraries/Types/CodegenTypes';
import type { HostComponent, ViewProps } from 'react-native';
export type VLCSource = Readonly<{
    uri: string;
    initOptions?: readonly string[];
    /** Android only */
    mediaOptions?: readonly string[];
    hwDecoderEnabled?: Int32;
    hwDecoderForced?: Int32;
}>;
export type VLCStateData = Readonly<{
    isPlaying: boolean;
    position: Float;
    currentTime: Float;
    duration: Float;
    bufferRate?: Float;
    type: 'Start' | 'Opening' | 'Buffering' | 'Playing' | 'Paused' | 'Stopped' | 'Ended' | 'Error' | 'TimeChanged' | 'Unknown';
}>;
export type VLCLayoutData = Readonly<{
    videoWidth: Float;
    videoHeight: Float;
    videoVisibleWidth: Float;
    videoVisibleHeight: Float;
    sarNum?: Float;
    sarDen?: Float;
}>;
export interface VLCPlayerProps extends ViewProps {
    source: VLCSource;
    rate?: Float;
    seek?: Int32;
    volume?: Int32;
    muted?: WithDefault<boolean, false>;
    paused?: WithDefault<boolean, false>;
    videoAspectRatio?: string;
    onStateChange?: DirectEventHandler<VLCStateData>;
    /** Android only */
    autoAspectRatio?: WithDefault<boolean, false>;
    onLayoutChange?: DirectEventHandler<VLCLayoutData>;
}
declare const _default: HostComponent<VLCPlayerProps>;
export default _default;
//# sourceMappingURL=RNVLCPlayerNativeComponent.d.ts.map