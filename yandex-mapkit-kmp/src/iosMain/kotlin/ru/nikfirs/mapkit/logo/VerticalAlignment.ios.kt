package ru.nikfirs.mapkit.logo

import YandexMapKit.YMKLogoVerticalAlignment
import ru.nikfirs.mapkit.logo.LogoVerticalAlignment
import YandexMapKit.YMKLogoVerticalAlignment as NativeVerticalAlignment

public fun LogoVerticalAlignment.toNative(): NativeVerticalAlignment {
    return when (this) {
        LogoVerticalAlignment.TOP -> NativeVerticalAlignment.YMKLogoVerticalAlignmentTop
        LogoVerticalAlignment.BOTTOM -> NativeVerticalAlignment.YMKLogoVerticalAlignmentBottom
    }
}

public fun NativeVerticalAlignment.toCommon(): LogoVerticalAlignment {
    return when (this) {
        YMKLogoVerticalAlignment.YMKLogoVerticalAlignmentTop -> LogoVerticalAlignment.TOP
        YMKLogoVerticalAlignment.YMKLogoVerticalAlignmentBottom -> LogoVerticalAlignment.BOTTOM
        else -> throw IllegalArgumentException("Unknown NativeTextStylePlacement ($this)")
    }
}