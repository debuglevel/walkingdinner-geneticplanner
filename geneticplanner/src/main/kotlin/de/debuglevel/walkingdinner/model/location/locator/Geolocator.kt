package de.debuglevel.walkingdinner.model.location.locator

import de.debuglevel.walkingdinner.model.location.Location
import de.debuglevel.walkingdinner.model.team.Team

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}
