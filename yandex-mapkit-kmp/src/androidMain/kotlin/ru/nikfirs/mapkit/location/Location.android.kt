package ru.nikfirs.mapkit.location

import kotlinx.datetime.Instant
import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import com.yandex.mapkit.location.Location as NativeLocation

public fun Location.toNative(): NativeLocation {
    return NativeLocation(
        position.toNative(),
        accuracy,
        altitude,
        altitudeAccuracy,
        heading,
        speed,
        indoorLevelId,
        absoluteTimestamp.toEpochMilliseconds(),
        relativeTimestamp.toEpochMilliseconds(),
    )
}

public fun NativeLocation.toCommon(): Location {
    return Location(
        position = position.toCommon(),
        accuracy = accuracy,
        altitude = altitude,
        altitudeAccuracy = altitudeAccuracy,
        heading = heading,
        speed = speed,
        indoorLevelId = indoorLevelId,
        absoluteTimestamp = Instant.fromEpochMilliseconds(absoluteTimestamp),
        relativeTimestamp = Instant.fromEpochMilliseconds(relativeTimestamp),
    )
}