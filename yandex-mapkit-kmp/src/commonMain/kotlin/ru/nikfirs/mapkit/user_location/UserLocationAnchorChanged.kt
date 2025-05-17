package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.layers.ObjectEvent

public expect class UserLocationAnchorChanged: ObjectEvent {

    public val anchorType: UserLocationAnchorType

}