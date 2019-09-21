package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.Location

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}
