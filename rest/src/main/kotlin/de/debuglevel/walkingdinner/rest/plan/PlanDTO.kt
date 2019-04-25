package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.model.Plan

data class PlanDTO(val done: Boolean, // TODO: rename to "calculationFinished" or the like
                   @Transient val plan: Plan? = null) {

    val additionalInformation: String? = plan?.additionalInformation

    val meetings: Set<Meeting>? = plan?.meetings
}