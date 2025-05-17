package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.toCommon
import ru.nikfirs.mapkit.toNative
import com.yandex.mapkit.map.Rect as NativeRect

public fun Rect.toNative(): NativeRect {
    return NativeRect(min.toNative(), max.toNative())
}

public fun NativeRect.toCommon(): Rect {
    return Rect(min.toCommon(), max.toCommon())
}