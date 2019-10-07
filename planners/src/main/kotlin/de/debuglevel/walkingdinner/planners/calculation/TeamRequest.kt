package de.debuglevel.walkingdinner.planners.calculation

import de.debuglevel.walkingdinner.planners.Team
import de.debuglevel.walkingdinner.planners.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planners.dietcompatibility.Diet
import java.util.*

data class TeamRequest(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: LocationRequest
) {
    fun toTeam(): Team {
        return Team(id, diet, cookingCapabilities, location.toLocation())
    }
}