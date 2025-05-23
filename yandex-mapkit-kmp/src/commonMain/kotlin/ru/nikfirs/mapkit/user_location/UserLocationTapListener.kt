package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.geometry.Point

public expect abstract class UserLocationTapListener() {
    public abstract fun onUserLocationObjectTap(point: Point)
}

public inline fun UserLocationTapListener(
    crossinline onUserLocationObjectTap: (point: Point) -> Unit,
): UserLocationTapListener {
    return object : UserLocationTapListener() {
        override fun onUserLocationObjectTap(point: Point) {
            onUserLocationObjectTap.invoke(point)
        }
    }
}