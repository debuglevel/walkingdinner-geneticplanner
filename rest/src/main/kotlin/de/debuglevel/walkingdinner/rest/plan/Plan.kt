package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.rest.Meeting

data class Plan(
    val additionalInformation: String,
    val meetings: Set<Meeting>
)
