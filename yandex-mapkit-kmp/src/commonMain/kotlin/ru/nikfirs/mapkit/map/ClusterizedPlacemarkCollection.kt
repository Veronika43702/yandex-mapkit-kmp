package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point

public expect class ClusterizedPlacemarkCollection : BaseMapObjectCollection {

    public fun addPlacemark(): PlacemarkMapObject

    public fun addPlacemark(placemarkCreatedCallback: PlacemarkCreatedCallback): PlacemarkMapObject

    public fun addEmptyPlacemarks(points: List<Point>): List<PlacemarkMapObject>

    public fun addPlacemarks(
        points: List<Point>,
        image: ImageProvider,
        style: IconStyle,
    ): List<PlacemarkMapObject>

    public fun clusterPlacemarks(clusterRadius: Double, minZoom: Int)

}