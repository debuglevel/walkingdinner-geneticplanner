package de.debuglevel.walkingdinner.planners.plan

import de.debuglevel.walkingdinner.planners.Meeting
import java.util.*

data class Plan(
    /**
     * UUID of the calculation
     */
    val id: UUID,
    val meetings: Set<Meeting>,
    val additionalInformation: String
)