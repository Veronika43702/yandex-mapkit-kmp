package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import com.yandex.mapkit.map.PlacemarkMapObject as NativePlacemarkMapObject

public actual class PlacemarkMapObject internal constructor(private val nativePlacemarkMapObject: NativePlacemarkMapObject) :
    MapObject(nativePlacemarkMapObject) {

    override fun toNative(): NativePlacemarkMapObject {
        return nativePlacemarkMapObject
    }

    public actual var geometry: Point
        get() = nativePlacemarkMapObject.geometry.toCommon()
        set(value) {
            nativePlacemarkMapObject.geometry = value.toNative()
        }
    public actual var direction: Float
        get() = nativePlacemarkMapObject.direction
        set(value) {
            nativePlacemarkMapObject.direction = value
        }
    public actual var opacity: Float
        get() = nativePlacemarkMapObject.opacity
        set(value) {
            nativePlacemarkMapObject.opacity = value
        }

    public actual fun setText(text: String, style: TextStyle) {
        nativePlacemarkMapObject.setText(text, style.toNative())
    }

    public actual fun setTextStyle(style: TextStyle) {
        nativePlacemarkMapObject.setTextStyle(style.toNative())
    }

    public actual fun setIcon(
        image: ImageProvider,
        style: IconStyle,
        onFinished: Callback?,
    ) {
        if (onFinished != null) nativePlacemarkMapObject.setIcon(
            image.toNative(),
            style.toNative(),
            onFinished.toNative(),
        )
        else nativePlacemarkMapObject.setIcon(image.toNative(), style.toNative())
    }

}

public fun NativePlacemarkMapObject.toCommon(): PlacemarkMapObject {
    return PlacemarkMapObject(this)
}