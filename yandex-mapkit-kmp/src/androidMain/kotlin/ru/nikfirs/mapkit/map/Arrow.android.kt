package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.geometry.PolylinePosition
import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.toColor
import ru.nikfirs.mapkit.toArgb
import com.yandex.mapkit.map.Arrow as NativeArrow

public actual class Arrow internal constructor(private val nativeArrow: NativeArrow) {

    public fun toNative(): NativeArrow {
        return nativeArrow
    }

    public actual val position: PolylinePosition
        get() = nativeArrow.position.toCommon()

    public actual var fillColor: Color
        get() = nativeArrow.fillColor.toColor()
        set(value) {
            nativeArrow.fillColor = value.toArgb()
        }
    public actual var outlineColor: Color
        get() = nativeArrow.outlineColor.toColor()
        set(value) {
            nativeArrow.outlineColor = value.toArgb()
        }
    public actual var outlineWidth: Float
        get() = nativeArrow.outlineWidth
        set(value) {
            nativeArrow.outlineWidth = value
        }
    public actual var length: Float
        get() = nativeArrow.length
        set(value) {
            nativeArrow.length = value
        }
    public actual var isVisible: Boolean
        get() = nativeArrow.isVisible
        set(value) {
            nativeArrow.isVisible = value
        }
    public actual var triangleHeight: Float
        get() = nativeArrow.triangleHeight
        set(value) {
            nativeArrow.triangleHeight = value
        }

}

public fun NativeArrow.toCommon(): Arrow {
    return Arrow(this)
}