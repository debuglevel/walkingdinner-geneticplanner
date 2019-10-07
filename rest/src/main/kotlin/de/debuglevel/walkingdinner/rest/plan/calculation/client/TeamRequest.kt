package de.debuglevel.walkingdinner.rest.plan.calculation.client

import de.debuglevel.walkingdinner.rest.participant.CookingCapability
import de.debuglevel.walkingdinner.rest.participant.Diet
import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.*

data class TeamRequest(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: LocationRequest
) {
    constructor(team: Team) : this(
        id = team.id!!,
        diet = team.diet,
        cookingCapabilities = team.cookingCapabilities,
        location = LocationRequest(team.location!!)
    )
}