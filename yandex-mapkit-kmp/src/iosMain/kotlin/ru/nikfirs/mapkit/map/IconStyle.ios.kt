package ru.nikfirs.mapkit.map

import YandexMapKit.YMKIconStyle
import ru.nikfirs.mapkit.toNSNumber
import ru.nikfirs.mapkit.toPointF

public fun IconStyle.toNative(): YMKIconStyle {
    return YMKIconStyle.iconStyleWithAnchor(
        anchor = null,
        rotationType = rotationType?.ordinal?.toNSNumber(),
        zIndex = zIndex?.toNSNumber(),
        flat = flat?.toNSNumber(),
        visible = isVisible?.toNSNumber(),
        scale = scale?.toNSNumber(),
        tappableArea = null,
    )
}

public fun YMKIconStyle.toCommon(): IconStyle {
    return IconStyle(
        anchor = anchor?.toPointF(),
        rotationType = rotationType?.let { RotationType.entries[it.intValue()] },
        zIndex = zIndex?.floatValue,
        flat = flat?.boolValue,
        isVisible = visible?.boolValue,
        scale = scale?.floatValue,
    )
}