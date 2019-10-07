package de.debuglevel.walkingdinner.planners

import de.debuglevel.walkingdinner.planners.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planners.dietcompatibility.Diet
import java.util.*

data class Team(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: Location
)