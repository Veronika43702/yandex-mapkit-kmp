package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.map.CircleMapObject
import ru.nikfirs.mapkit.map.PlacemarkMapObject

public expect class UserLocationView{
    public val arrow: PlacemarkMapObject
    public val pin: PlacemarkMapObject
    public val accuracyCircle: CircleMapObject
}