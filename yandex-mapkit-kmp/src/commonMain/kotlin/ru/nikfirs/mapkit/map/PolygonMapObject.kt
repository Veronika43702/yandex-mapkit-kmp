package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.geometry.Polygon

public expect class PolygonMapObject : MapObject {

    public var geometry: Polygon

    public var strokeColor: Color

    public var strokeWidth: Float

    public var fillColor: Color

    public var isGeodesic: Boolean

    public fun setPattern(image: ImageProvider, scale: Float)

    public fun resetPattern()

}