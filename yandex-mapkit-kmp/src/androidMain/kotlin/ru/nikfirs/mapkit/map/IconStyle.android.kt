package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.toCommon
import ru.nikfirs.mapkit.toNative
import com.yandex.mapkit.map.IconStyle as NativeIconStyle

public fun IconStyle.toNative(): NativeIconStyle {
    return NativeIconStyle(
        /* anchor = */ anchor?.toNative(),
        /* rotationType = */ rotationType?.toNative(),
        /* zIndex = */ zIndex,
        /* flat = */ flat,
        /* visible = */ isVisible,
        /* scale = */ scale,
        /* tappableArea = */ tappableArea?.toNative(),
    )
}

public fun NativeIconStyle.toCommon(): IconStyle {
    return IconStyle(
        anchor = anchor?.toCommon(),
        rotationType = rotationType?.toCommon(),
        zIndex = zIndex,
        flat = flat,
        isVisible = visible,
        scale = scale,
        tappableArea = tappableArea?.toCommon()
    )
}