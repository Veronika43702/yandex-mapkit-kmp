package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Circle
import ru.nikfirs.mapkit.geometry.Polygon
import ru.nikfirs.mapkit.geometry.Polyline

public expect class MapObjectCollection : BaseMapObjectCollection {

    public fun addPlacemark(): PlacemarkMapObject

    public fun addPlacemark(placemarkCreatedCallback: PlacemarkCreatedCallback): PlacemarkMapObject

    public fun addPolyline(polyline: Polyline): PolylineMapObject

    public fun addPolygon(polygon: Polygon): PolygonMapObject

    public fun addCircle(circle: Circle): CircleMapObject

    public fun addCollection(): MapObjectCollection

    public fun addClusterizedPlacemarkCollection(listener: ClusterListener): ClusterizedPlacemarkCollection

    public val placemarksStyler: PlacemarksStyler

}