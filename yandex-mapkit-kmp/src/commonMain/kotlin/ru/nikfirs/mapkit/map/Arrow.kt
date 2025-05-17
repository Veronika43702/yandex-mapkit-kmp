package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.geometry.PolylinePosition

public expect class Arrow {

    public val position: PolylinePosition

    public var fillColor: Color

    public var outlineColor: Color

    public var outlineWidth: Float

    public var length: Float

    public var isVisible: Boolean

    public var triangleHeight: Float

}