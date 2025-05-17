package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point

public data class CameraPosition(
    val target: Point,
    val zoom: Float,
    val azimuth: Float,
    val tilt: Float,
)
