package ru.nikfirs.mapkit.location

import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toNSDate
import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import ru.nikfirs.mapkit.toNSNumber
import YandexMapKit.YMKLocation as NativeLocation

public fun Location.toNative(): NativeLocation {
    return NativeLocation.locationWithPosition(
        position = position.toNative(),
        accuracy = accuracy?.toNSNumber(),
        altitude = altitude?.toNSNumber(),
        altitudeAccuracy = altitudeAccuracy?.toNSNumber(),
        heading = heading?.toNSNumber(),
        speed = speed?.toNSNumber(),
        indoorLevelId = indoorLevelId,
        absoluteTimestamp = absoluteTimestamp.toNSDate(),
        relativeTimestamp = relativeTimestamp.toNSDate(),
    )
}

public fun NativeLocation.toCommon(): Location {
    return Location(
        position = position.toCommon(),
        accuracy = accuracy?.doubleValue,
        altitude = altitude?.doubleValue,
        altitudeAccuracy = altitudeAccuracy?.doubleValue,
        heading = heading?.doubleValue,
        speed = speed?.doubleValue,
        indoorLevelId = indoorLevelId,
        absoluteTimestamp = absoluteTimestamp.toKotlinInstant(),
        relativeTimestamp = relativeTimestamp.toKotlinInstant(),
    )
}