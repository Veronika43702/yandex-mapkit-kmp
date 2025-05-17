package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import com.yandex.mapkit.map.VisibleRegion as NativeVisibleRegion

public fun VisibleRegion.toNative(): NativeVisibleRegion {
    return NativeVisibleRegion(
        topLeft.toNative(),
        topRight.toNative(),
        bottomLeft.toNative(),
        bottomRight.toNative()
    )
}

public fun NativeVisibleRegion.toCommon(): VisibleRegion {
    return VisibleRegion(
        topLeft = topLeft.toCommon(),
        topRight = topRight.toCommon(),
        bottomLeft = bottomLeft.toCommon(),
        bottomRight = bottomRight.toCommon()
    )
}