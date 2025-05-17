package ru.nikfirs.mapkit

import com.yandex.mapkit.ScreenRect as NativeScreenRect

public fun ScreenRect.toNative(): NativeScreenRect {
    return NativeScreenRect(topLeft.toNative(), bottomRight.toNative())
}

public fun NativeScreenRect.toCommon(): ScreenRect {
    return ScreenRect(topLeft.toCommon(), bottomRight.toCommon())
}