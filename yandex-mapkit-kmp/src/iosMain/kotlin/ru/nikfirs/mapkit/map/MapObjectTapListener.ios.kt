package ru.nikfirs.mapkit.map

import platform.darwin.NSObject
import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.geometry.toCommon
import YandexMapKit.YMKMapObject as NativeMapObject
import YandexMapKit.YMKMapObjectTapListenerProtocol as NativeMapObjectTapListener
import YandexMapKit.YMKPoint as NativePoint

public actual abstract class MapObjectTapListener actual constructor() {
    private val nativeListener = object : NativeMapObjectTapListener, NSObject() {
        override fun onMapObjectTapWithMapObject(
            mapObject: NativeMapObject,
            point: NativePoint
        ): Boolean {
            return onMapObjectTap(mapObject.toCommon(), point.toCommon())
        }
    }

    public fun toNative(): NativeMapObjectTapListener {
        return nativeListener
    }

    public actual abstract fun onMapObjectTap(
        mapObject: MapObject,
        point: Point
    ): Boolean
}