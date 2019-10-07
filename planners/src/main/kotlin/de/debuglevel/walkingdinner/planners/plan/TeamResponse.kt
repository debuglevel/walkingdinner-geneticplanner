package de.debuglevel.walkingdinner.planners.plan

import de.debuglevel.walkingdinner.planners.Team
import java.util.*

data class TeamResponse(val id: UUID)

fun Team.toTeamResponse(): TeamResponse {
    return TeamResponse(
        id = this.id
    )
}