package ru.nikfirs.mapkit.location

import ru.nikfirs.mapkit.location.UseInBackground
import YandexMapKit.YMKLocationUseInBackground as NativeUseInBackground

public fun UseInBackground.toNative(): NativeUseInBackground {
    return when (this) {
        UseInBackground.ALLOW -> NativeUseInBackground.YMKLocationUseInBackgroundAllow
        UseInBackground.DISALLOW -> NativeUseInBackground.YMKLocationUseInBackgroundDisallow
    }
}

public fun NativeUseInBackground.toCommon(): UseInBackground {
    return when (this) {
        NativeUseInBackground.YMKLocationUseInBackgroundAllow -> UseInBackground.ALLOW
        NativeUseInBackground.YMKLocationUseInBackgroundDisallow -> UseInBackground.DISALLOW
        else -> throw IllegalArgumentException("Unknown YMKLocationUseInBackground ($this)")
    }
}