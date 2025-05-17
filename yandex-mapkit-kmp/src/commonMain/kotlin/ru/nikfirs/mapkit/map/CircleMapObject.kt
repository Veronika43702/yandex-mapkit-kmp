package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.geometry.Circle

public expect class CircleMapObject: MapObject {

    public var geometry: Circle

    public var strokeColor: Color

    public var strokeWidth: Float

    public var fillColor: Color

    public var isGeodesic: Boolean

}