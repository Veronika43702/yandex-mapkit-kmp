package ru.nikfirs.mapkit.location

import ru.nikfirs.mapkit.location.LocationStatus
import YandexMapKit.YMKLocationStatus as NativeLocationStatus

public fun LocationStatus.toNative(): NativeLocationStatus {
    return when (this) {
        LocationStatus.NOT_AVAILABLE -> NativeLocationStatus.YMKLocationStatusNotAvailable
        LocationStatus.AVAILABLE -> NativeLocationStatus.YMKLocationStatusAvailable
        LocationStatus.RESET -> NativeLocationStatus.YMKLocationStatusReset
    }
}

public fun NativeLocationStatus.toCommon(): LocationStatus {
    return when (this) {
        NativeLocationStatus.YMKLocationStatusAvailable -> LocationStatus.AVAILABLE
        NativeLocationStatus.YMKLocationStatusReset -> LocationStatus.RESET
        NativeLocationStatus.YMKLocationStatusNotAvailable -> LocationStatus.NOT_AVAILABLE
        else -> throw IllegalArgumentException("Unknown NativeLocationStatus ($this)")
    }
}