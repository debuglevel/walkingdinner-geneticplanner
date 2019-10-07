package de.debuglevel.walkingdinner.rest.plan.calculation.client

import de.debuglevel.walkingdinner.rest.participant.location.Location

class LocationRequest(
    val lng: Double,
    val lat: Double
) {
    constructor(location: Location) : this(
        lng = location.lng,
        lat = location.lat
    )
}