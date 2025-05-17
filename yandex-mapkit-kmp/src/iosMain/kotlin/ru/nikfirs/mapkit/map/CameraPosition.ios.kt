package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import YandexMapKit.YMKCameraPosition as NativeCameraPosition

public fun CameraPosition.toNative(): NativeCameraPosition {
    return NativeCameraPosition.cameraPositionWithTarget(target.toNative(), zoom, azimuth, tilt)
}

public fun NativeCameraPosition.toCommon(): CameraPosition {
    return CameraPosition(target.toCommon(), zoom, azimuth, tilt)
}