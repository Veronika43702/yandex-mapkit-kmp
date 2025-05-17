package ru.nikfirs.mapkit.map

import kotlinx.cinterop.useContents
import ru.nikfirs.mapkit.toCommon
import ru.nikfirs.mapkit.toNative
import YandexMapKit.YMKRect as NativeRect

public fun Rect.toNative(): NativeRect {
    return NativeRect.rectWithMin(min.toNative(), max.toNative())
}

public fun NativeRect.toCommon(): Rect {
    return Rect(min.useContents { toCommon() }, max.useContents { toCommon() })
}