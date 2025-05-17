package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.layers.ObjectEvent

public expect class UserLocationIconChanged: ObjectEvent {

    public val iconType: UserLocationIconType

}