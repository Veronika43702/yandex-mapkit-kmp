package ru.nikfirs.mapkit.compose

import ru.nikfirs.mapkit.logo.Logo
import ru.nikfirs.mapkit.logo.LogoAlignment
import ru.nikfirs.mapkit.logo.LogoPadding
import ru.nikfirs.mapkit.map.Map

/**
 * Config to control Yandex logo object with [Map.getLogo]
 */
public data class MapLogoConfig(
    /**
     * Set Yandex Map logo alignment via [Logo.setAlignment].
     *
     * If null use map's default value
     */
    val alignment: LogoAlignment? = null,
    /**
     * Set Yandex Map logo alignment via [Logo.setPadding].
     *
     * If null use map's default value
     */
    val padding: LogoPadding? = null,
)