package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.PointF
import ru.nikfirs.mapkit.toNative
import com.yandex.mapkit.map.PlacemarksStyler as NativePlacemarksStyler

public actual class PlacemarksStyler internal constructor(private val nativePlacemarksStyler: NativePlacemarksStyler) {

    public fun toNative(): NativePlacemarksStyler {
        return nativePlacemarksStyler
    }

    public actual fun setScaleFunction(points: List<PointF>) {
        nativePlacemarksStyler.setScaleFunction(points.map { it.toNative() })
    }

}

public fun NativePlacemarksStyler.toCommon(): PlacemarksStyler {
    return PlacemarksStyler(this)
}