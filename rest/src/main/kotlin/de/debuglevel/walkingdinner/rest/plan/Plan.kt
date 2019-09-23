package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.rest.Meeting
import java.util.*

data class Plan(
    val id: UUID,
    val meetings: Set<Meeting>,
    val additionalInformation: String
)
