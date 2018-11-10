package de.debuglevel.walkingdinner.model.team

import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.model.dietcompatibility.Capability
import de.debuglevel.walkingdinner.model.location.Location


data class Team(val cook1: Cook,
                val cook2: Cook,
                val address: String,
                val diet: String,
                val capabilities: List<Capability>,
                var location: Location?) {

    override fun toString(): String {
        return "$cook1 & $cook2 ($diet; $location)"
    }

    companion object {
        fun toMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
            val meetings = mutableSetOf<Meeting>()

            val meetingTeams: Array<Team?> = Array(3, { i -> null })

            for ((index, value) in teams.withIndex()) {
                meetingTeams[index % 3] = value

                if (index % 3 == 2) {
                    meetings.add(Meeting(meetingTeams.filterNotNull(), courseName))
                }
            }

            return meetings
        }
    }
}