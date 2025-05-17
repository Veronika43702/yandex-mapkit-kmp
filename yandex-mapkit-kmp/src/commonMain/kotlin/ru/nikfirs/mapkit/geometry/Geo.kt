package ru.nikfirs.mapkit.geometry

public expect object Geo {

    public fun distance(firstPoint: Point, secondPoint: Point): Double

    public fun closestPoint(point: Point, segment: Segment): Point

    public fun pointOnSegmentByFactor(segment: Segment, factor: Double): Point

    public fun course(firstPoint: Point, secondPoint: Point): Double
}