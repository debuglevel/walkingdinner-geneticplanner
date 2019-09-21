package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.rest.Meeting
import java.util.*

data class Plan(
    val additionalInformation: String,
    val meetings: Set<Meeting>,
    val id: UUID
)
