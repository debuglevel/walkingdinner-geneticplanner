package de.debuglevel.walkingdinner.model.location.locator

import de.debuglevel.walkingdinner.model.location.Location
import de.debuglevel.walkingdinner.rest.participant.Team

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}
