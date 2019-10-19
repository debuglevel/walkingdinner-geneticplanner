package de.debuglevel.walkingdinner.rest.plan.client

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.*

data class MeetingResponse(
    val id: UUID,
    val course: String,
    val teams: List<TeamResponse>
) {
    fun toMeeting(fullTeams: List<Team>): Meeting {
        return Meeting(
            teams = this.teams.map { team ->
                fullTeams.first { fullTeam ->
                    fullTeam.id == team.id
                }
            },
            course = this.course,
            id = null
        )
    }
}

