package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.toCommon
import ru.nikfirs.mapkit.toNative
import YandexMapKit.YMKLineStyle as NativeLineStyle

public actual class LineStyle internal constructor(private val nativeLineStyle: NativeLineStyle) {
    public actual var strokeWidth: Float
        get() = nativeLineStyle.strokeWidth
        set(value) {
            nativeLineStyle.setStrokeWidth(value)
        }
    public actual var gradientLength: Float
        get() = nativeLineStyle.gradientLength
        set(value) {
            nativeLineStyle.setGradientLength(value)
        }
    public actual var outlineColor: Color
        get() = nativeLineStyle.outlineColor.toCommon()
        set(value) {
            nativeLineStyle.setOutlineColor(value.toNative())
        }
    public actual var outlineWidth: Float
        get() = nativeLineStyle.outlineWidth
        set(value) {
            nativeLineStyle.setOutlineWidth(value)
        }
    public actual var innerOutlineEnabled: Boolean
        get() = nativeLineStyle.innerOutlineEnabled
        set(value) {
            nativeLineStyle.setInnerOutlineEnabled(value)
        }
    public actual var turnRadius: Float
        get() = nativeLineStyle.turnRadius
        set(value) {
            nativeLineStyle.setTurnRadius(value)
        }
    public actual var arcApproximationStep: Float
        get() = nativeLineStyle.arcApproximationStep
        set(value) {
            nativeLineStyle.setArcApproximationStep(value)
        }
    public actual var dashLength: Float
        get() = nativeLineStyle.dashLength
        set(value) {
            nativeLineStyle.setDashLength(value)
        }
    public actual var gapLength: Float
        get() = nativeLineStyle.gapLength
        set(value) {
            nativeLineStyle.setGapLength(value)
        }
    public actual var dashOffset: Float
        get() = nativeLineStyle.dashOffset
        set(value) {
            nativeLineStyle.setDashOffset(value)
        }

    public actual constructor(
        strokeWidth: Float,
        gradientLength: Float,
        outlineColor: Color,
        outlineWidth: Float,
        innerOutlineEnabled: Boolean,
        turnRadius: Float,
        arcApproximationStep: Float,
        dashLength: Float,
        gapLength: Float,
        dashOffset: Float
    ) : this(
        NativeLineStyle.lineStyleWithStrokeWidth(
            strokeWidth = strokeWidth,
            gradientLength = gradientLength,
            outlineColor = outlineColor.toNative(),
            outlineWidth = outlineWidth,
            innerOutlineEnabled = innerOutlineEnabled,
            turnRadius = turnRadius,
            arcApproximationStep = arcApproximationStep,
            dashLength = dashLength,
            gapLength = gapLength,
            dashOffset = dashOffset,
        )
    )

    public fun toNative(): NativeLineStyle {
        return nativeLineStyle
    }
}

public fun NativeLineStyle.toCommon(): LineStyle {
    return ru.nikfirs.mapkit.map.LineStyle(this)
}
