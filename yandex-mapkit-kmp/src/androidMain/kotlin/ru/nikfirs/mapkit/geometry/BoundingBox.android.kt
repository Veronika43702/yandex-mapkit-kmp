package ru.nikfirs.mapkit.geometry

import com.yandex.mapkit.geometry.BoundingBox as NativeBoundingBox

public fun BoundingBox.toNative(): NativeBoundingBox {
    return NativeBoundingBox(southWest.toNative(), northEast.toNative())
}

public fun NativeBoundingBox.toCommon(): BoundingBox {
    return BoundingBox(
        southWest = southWest.toCommon(),
        northEast = northEast.toCommon(),
    )
}