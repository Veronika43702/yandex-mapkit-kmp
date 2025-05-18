package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.geometry.Circle
import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import ru.nikfirs.mapkit.toColor
import ru.nikfirs.mapkit.toArgb
import com.yandex.mapkit.map.CircleMapObject as NativeCircleMapObject

public actual class CircleMapObject internal constructor(private val nativeCircleMapObject: NativeCircleMapObject) :
    MapObject(nativeCircleMapObject) {

    override fun toNative(): NativeCircleMapObject {
        return nativeCircleMapObject
    }

    public actual var geometry: Circle
        get() = nativeCircleMapObject.geometry.toCommon()
        set(value) {
            nativeCircleMapObject.geometry = value.toNative()
        }
    public actual var strokeColor: Color
        get() = nativeCircleMapObject.strokeColor.toColor()
        set(value) {
            nativeCircleMapObject.strokeColor = value.toArgb()
        }
    public actual var strokeWidth: Float
        get() = nativeCircleMapObject.strokeWidth
        set(value) {
            nativeCircleMapObject.strokeWidth = value
        }
    public actual var fillColor: Color
        get() = nativeCircleMapObject.fillColor.toColor()
        set(value) {
            nativeCircleMapObject.fillColor = value.toArgb()
        }
    public actual var isGeodesic: Boolean
        get() = nativeCircleMapObject.isGeodesic
        set(value) {
            nativeCircleMapObject.isGeodesic = value
        }
}

public fun NativeCircleMapObject.toCommon(): CircleMapObject {
    return CircleMapObject(this)
}