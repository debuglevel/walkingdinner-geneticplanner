package rocks.huwi.walkingdinnerplanner.geneticplanner.team

import rocks.huwi.walkingdinnerplanner.geneticplanner.Meeting
import rocks.huwi.walkingdinnerplanner.geneticplanner.dietcompatibility.Capability
import rocks.huwi.walkingdinnerplanner.geneticplanner.location.Location


data class Team(val cook1: Cook,
                val cook2: Cook,
                val address: String,
                val diet: String,
                val capabilities: List<Capability>,
                var location: Location?) {

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