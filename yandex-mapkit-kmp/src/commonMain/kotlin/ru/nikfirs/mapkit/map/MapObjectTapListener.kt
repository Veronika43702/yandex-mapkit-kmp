package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point

public expect abstract class MapObjectTapListener() {
    public abstract fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean
}

public inline fun MapObjectTapListener(
    crossinline onMapObjectTap: (mapObject: MapObject, point: Point) -> Boolean
): MapObjectTapListener {
    return object : MapObjectTapListener() {
        override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
            return onMapObjectTap.invoke(mapObject, point)
        }
    }
}