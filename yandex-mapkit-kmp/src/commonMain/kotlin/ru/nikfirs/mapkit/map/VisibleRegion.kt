package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point

public data class VisibleRegion(
    val topLeft: Point,
    val topRight: Point,
    val bottomLeft: Point,
    val bottomRight: Point,
)