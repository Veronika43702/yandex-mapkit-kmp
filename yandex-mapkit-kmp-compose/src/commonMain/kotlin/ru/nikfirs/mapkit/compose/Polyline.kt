package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import ru.nikfirs.mapkit.compose.utils.toComposeColor
import ru.nikfirs.mapkit.compose.utils.toMapkitColor
import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.geometry.Polyline
import ru.nikfirs.mapkit.geometry.PolylinePosition
import ru.nikfirs.mapkit.geometry.Subpolyline
import ru.nikfirs.mapkit.map.Arrow
import ru.nikfirs.mapkit.map.PolylineMapObject

/**
 * Uses [rememberSaveable] to retain [PolylineState.geometry] across configuration changes,
 * for simple use cases.
 *
 * Other use cases may be better served syncing [PolylineState.geometry] with a data model.
 */
@Composable
public fun rememberPolylineState(geometry: Polyline, key: String? = null): PolylineState {
    return rememberSaveable(key = key, saver = PolylineState.Saver) { PolylineState(geometry) }
}

@Immutable
public class PolylineState(geometry: Polyline) {

    public var geometry: Polyline by mutableStateOf(geometry)

    // The marker associated with this MarkerState.
    private val mapObjectState: MutableState<PolylineMapObject?> = mutableStateOf(null)
    internal var mapObject: PolylineMapObject?
        get() = mapObjectState.value
        set(value) {
            if (mapObjectState.value == null && value == null) return
            if (mapObjectState.value != null && value != null) {
                error("MarkerState may only be associated with one Marker at a time.")
            }
            mapObjectState.value = value
        }

    public fun select(color: Color, subpolyline: Subpolyline) {
        mapObject?.select(color.toMapkitColor(), subpolyline)
    }

    public fun hide(subpolyline: Subpolyline) {
        mapObject?.hide(subpolyline)
    }

    public fun hide(subpolylines: List<Subpolyline>) {
        mapObject?.hide(subpolylines)
    }

    public fun setStrokeColors(colors: List<Color>, weights: List<Double>) {
        mapObject?.setStrokeColors(colors.map { it.toMapkitColor() }, weights)
    }

    public fun setStrokeColors(colors: List<Color>) {
        mapObject?.setStrokeColors(colors.map { it.toMapkitColor() })
    }

    public fun getStrokeColor(segmentIndex: Int): Color {
        return mapObject?.getStrokeColor(segmentIndex)?.toComposeColor()
            ?: throw IllegalStateException("PolylineMapObject is not attached to PolylineState")
    }

    public fun setPaletteColor(colorIndex: Int, color: Color) {
        mapObject?.setPaletteColor(colorIndex, color.toMapkitColor())
    }

    public fun getPaletteColor(colorIndex: Int): Color {
        return mapObject?.getPaletteColor(colorIndex)?.toComposeColor()
            ?: throw IllegalStateException("PolylineMapObject is not attached to PolylineState")
    }

    public fun addArrow(
        position: PolylinePosition,
        length: Float,
        fillColor: Color
    ): Arrow {
        return mapObject?.addArrow(position, length, fillColor.toMapkitColor())
            ?: throw IllegalStateException("PolylineMapObject is not attached to PolylineState")
    }

    public fun arrows(): List<Arrow> {
        return mapObject?.arrows
            ?: throw IllegalStateException("PolylineMapObject is not attached to PolylineState")
    }

    public companion object {
        public val Saver: Saver<PolylineState, Any> = listSaver(
            save = {
                val points = mutableListOf<Double>()
                it.geometry.points.forEach { point ->
                    points.add(point.latitude.value)
                    points.add(point.longitude.value)
                }
                points
            },
            restore = {
                val points = mutableListOf<Point>()
                for (i in 0 until it.size / 2) {
                    points.add(Point(it[i * 2], it[i * 2 + 1]))
                }
                PolylineState(
                    geometry = Polyline(
                        points = points,
                    )
                )
            }
        )
    }

}

@[YandexMapComposable Composable]
public fun Polyline(
    state: PolylineState,
    strokeColor: Color = DefaultStrokeColor,
    strokeWidth: Float = DefaultStrokeWidth,
    gradientLength: Float = DefaultGradientLength,
    outlineWidth: Float = DefaulOutlineWidth,
    outlineColor: Color = DefaultOutlineColor,
    innerOutlineEnabled: Boolean = false,
    turnRadius: Float = DefaultTurnRadius,
    dashLength: Float = DefaultDashLength,
    gapLength: Float = DefaultGapLength,
    dashOffset: Float = DefaultDashOffset,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onTap: ((Point) -> Boolean)? = null,
) {
    PolylineImpl(
        state = state,
        strokeColor = strokeColor,
        strokeWidth = strokeWidth,
        gradientLength = gradientLength,
        outlineWidth = outlineWidth,
        outlineColor = outlineColor,
        innerOutlineEnabled = innerOutlineEnabled,
        turnRadius = turnRadius,
        dashLength = dashLength,
        gapLength = gapLength,
        dashOffset = dashOffset,
        visible = visible,
        zIndex = zIndex,
        onTap = onTap,
    )
}

@[YandexMapComposable Composable]
internal fun PolylineImpl(
    state: PolylineState,
    strokeColor: Color = DefaultStrokeColor,
    strokeWidth: Float = DefaultStrokeWidth,
    gradientLength: Float = DefaultGradientLength,
    outlineWidth: Float = DefaulOutlineWidth,
    outlineColor: Color = DefaultOutlineColor,
    innerOutlineEnabled: Boolean = false,
    turnRadius: Float = DefaultTurnRadius,
    dashLength: Float = DefaultDashLength,
    gapLength: Float = DefaultGapLength,
    dashOffset: Float = DefaultDashOffset,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onTap: ((Point) -> Boolean)? = null,
) {
    val collection = LocalMapObjectCollection.current
    MapObjectNode(
        visible = visible,
        zIndex = zIndex,
        onTap = onTap,
        factory = {
            val mapObject = collection.addPolyline(state.geometry)
            mapObject.style.strokeWidth = strokeWidth
            mapObject.style.gradientLength = gradientLength
            mapObject.style.outlineWidth = outlineWidth
            mapObject.style.outlineColor = outlineColor.toMapkitColor()
            mapObject.style.innerOutlineEnabled = innerOutlineEnabled
            mapObject.style.turnRadius = turnRadius
            mapObject.style.dashLength = dashLength
            mapObject.style.gapLength = gapLength
            mapObject.style.dashOffset = dashOffset
            mapObject.setStrokeColor(strokeColor.toMapkitColor())
            PolylineNode(
                mapObject = mapObject,
                tapListener = onTap,
            )
        },
        update = {
            update(state.geometry) { this.mapObject.geometry = it }
            update(strokeWidth) { mapObject.style.strokeWidth = strokeWidth }
            update(gradientLength) { mapObject.style.gradientLength = gradientLength }
            update(outlineColor) { mapObject.style.outlineColor = outlineColor.toMapkitColor() }
            update(innerOutlineEnabled) { mapObject.style.innerOutlineEnabled = innerOutlineEnabled }
            update(turnRadius) { mapObject.style.turnRadius = turnRadius }
            update(dashLength) { mapObject.style.dashLength = dashLength }
            update(gapLength) { mapObject.style.gapLength = gapLength }
            update(dashOffset) { mapObject.style.dashOffset = dashOffset }
            update(strokeColor) { mapObject.setStrokeColor(strokeColor.toMapkitColor()) }
        }
    )
}


internal class PolylineNode(
    mapObject: PolylineMapObject,
    tapListener: ((Point) -> Boolean)?
) : MapObjectNode<PolylineMapObject>(mapObject, tapListener)

private val DefaultStrokeColor = Color(0x0066FFFF)
private const val DefaultGradientLength = 0f
private const val DefaultStrokeWidth = 1f
private const val DefaulOutlineWidth = 0f
private const val DefaultTurnRadius = 10f
private val DefaultOutlineColor = Color(0x00000000)
private const val DefaultDashLength = 0f
private const val DefaultDashOffset = 0f
private const val DefaultGapLength = 0f
private val DefaultFillColor = Color(0x0066FF99)
private const val DefaultGeodesic = false