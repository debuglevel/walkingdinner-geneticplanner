package de.debuglevel.walkingdinner.planners.plan

import java.util.*

data class PlanResponse(
    val id: UUID,
    val meetings: Set<MeetingResponse>
)

fun Plan.toPlanResponse(): PlanResponse {
    return PlanResponse(
        id = this.id,
        meetings = this.meetings.map { it.toMeetingResponse() }.toSet()
    )
}
