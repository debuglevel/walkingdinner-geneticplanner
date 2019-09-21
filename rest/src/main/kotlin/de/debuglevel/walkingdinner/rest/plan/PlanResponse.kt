package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.rest.Meeting

data class PlanResponse(
    val done: Boolean, // TODO: rename to "calculationFinished" or the like
    @Transient val plan: Plan? = null
) {

    val additionalInformation: String? = plan?.additionalInformation

    val meetings: Set<Meeting>? = plan?.meetings
}