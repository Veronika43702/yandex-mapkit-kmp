package ru.nikfirs.mapkit.mapview

import ru.nikfirs.mapkit.map.MapWindow

public expect class MapView {

    public val mapWindow: MapWindow

    /**
     * Should be called from from corresponding method of activity or fragment containing this view
     */
    public fun onStart()

    /**
     * Should be called from from corresponding method of activity or fragment containing this view
     */
    public fun onStop()

    public fun setNonInteractive(value: Boolean)

}