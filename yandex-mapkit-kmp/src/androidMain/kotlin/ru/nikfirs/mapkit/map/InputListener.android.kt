package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.geometry.toCommon
import com.yandex.mapkit.geometry.Point as NativePoint
import com.yandex.mapkit.map.InputListener as NativeInputListener
import com.yandex.mapkit.map.Map as NativeMap

public actual abstract class InputListener actual constructor() {
    private val nativeListener = object : NativeInputListener {
        override fun onMapTap(p0: NativeMap, p1: NativePoint) {
            onMapTap(p0.toCommon(), p1.toCommon())
        }

        override fun onMapLongTap(p0: NativeMap, p1: NativePoint) {
            onMapLongTap(p0.toCommon(), p1.toCommon())
        }
    }

    public fun toNative(): NativeInputListener {
        return nativeListener
    }

    public actual abstract fun onMapTap(
        map: Map,
        point: Point,
    )

    public actual abstract fun onMapLongTap(
        map: Map,
        point: Point,
    )
}