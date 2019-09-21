package de.debuglevel.walkingdinner.rest.participant


import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.location.Location
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Capability
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Diet
import java.util.*


data class Team(
    val cook1: Cook,
    val cook2: Cook,
    val address: String,
    val diet: Diet,
    val capabilities: List<Capability>,
    var location: Location?,
    val id: UUID? = null
) {

    override fun toString(): String {
        return "Team $id: $cook1 & $cook2 ($diet; $location)"
    }

    companion object {
        fun toMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
            val meetings = mutableSetOf<Meeting>()

            val meetingTeams: Array<Team?> = Array(3) { _ -> null }

            for ((index, value) in teams.withIndex()) {
                meetingTeams[index % 3] = value

                if (index % 3 == 2) {
                    meetings.add(
                        Meeting(
                            meetingTeams.filterNotNull(),
                            courseName
                        )
                    )
                }
            }

            return meetings
        }
    }
}