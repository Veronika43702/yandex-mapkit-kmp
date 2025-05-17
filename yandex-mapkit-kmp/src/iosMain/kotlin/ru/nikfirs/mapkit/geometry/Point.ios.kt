package ru.nikfirs.mapkit.geometry

import ru.nikfirs.mapkit.geometry.Latitude
import ru.nikfirs.mapkit.geometry.Longitude
import ru.nikfirs.mapkit.geometry.Point
import YandexMapKit.YMKPoint as NativePoint

public fun Point.toNative(): NativePoint {
    return NativePoint.pointWithLatitude(latitude.value, longitude.value)
}

public fun NativePoint.toCommon(): Point {
    return Point(Latitude(latitude), Longitude(longitude))
}