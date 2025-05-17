package ru.nikfirs.mapkit

import kotlin.time.Duration

public data class Animation(
    val type: Type,
    val duration: Duration,
) {
    public enum class Type {
        SMOOTH, LINEAR
    }
}