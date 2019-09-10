package de.debuglevel.walkingdinner.model.dietcompatibility

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team

object HardCompatibility : Compatibility {
    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        return meeting.teams.all { isCompatibleDiet(meeting.teams.first(), it) }
    }

    private fun isCompatibleDiet(a: Team, b: Team) = a.diet == b.diet
}