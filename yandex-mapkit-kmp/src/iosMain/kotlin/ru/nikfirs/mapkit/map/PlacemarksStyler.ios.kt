package ru.nikfirs.mapkit.map

import platform.Foundation.NSValue
import platform.UIKit.valueWithCGPoint
import ru.nikfirs.mapkit.PointF
import ru.nikfirs.mapkit.toNative
import YandexMapKit.YMKPlacemarksStyler as NativePlacemarksStyler

public actual class PlacemarksStyler internal constructor(private val nativePlacemarkStyle: NativePlacemarksStyler) {

    public fun toNative(): NativePlacemarksStyler {
        return nativePlacemarkStyle
    }

    public actual fun setScaleFunction(points: List<PointF>) {
        nativePlacemarkStyle.setScaleFunctionWithPoints(points.map { NSValue.valueWithCGPoint(it.toNative()) })
    }

}

public fun NativePlacemarksStyler.toCommon(): PlacemarksStyler {
    return ru.nikfirs.mapkit.map.PlacemarksStyler(this)
}