package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.Color

public data class TextStyle(
    val size: Float = 8f,
    val color: Color = DEFAULT_COLOR,
    val outlineWidth: Float = 1f,
    val outlineColor: Color = DEFAULT_OUTLINE_COLOR,
    val placement: Placement = Placement.CENTER,
    val offset: Float = 0f,
    val offsetFromIcon: Boolean = true,
    val textOptional: Boolean = false,
) {
    public enum class Placement {
        CENTER,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
    }

    private companion object {
        private val DEFAULT_COLOR = Color.fromArgb(-16777216)
        private val DEFAULT_OUTLINE_COLOR = Color.fromArgb(-1)
    }
}