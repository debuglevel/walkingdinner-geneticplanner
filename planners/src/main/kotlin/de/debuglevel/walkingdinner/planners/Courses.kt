package de.debuglevel.walkingdinner.planners

data class Courses(
    val course1teams: Iterable<Team>,
    val course2teams: Iterable<Team>,
    val course3teams: Iterable<Team>
) {

    // TODO: should be labeled in english
    companion object {
        const val course1name = "Vorspeise"
        const val course2name = "Hauptspeise"
        const val course3name = "Dessert"
    }

    fun toMeetings(): Set<Meeting> {
        return toCourseMeetings()
            .values
            .flatten()
            .toSet()
    }

    fun toCourseMeetings(): Map<String, Set<Meeting>> {
        val courseMeetings = hashMapOf<String, Set<Meeting>>()

        courseMeetings[course1name] = teamToMeetings(
            course1teams,
            course1name
        )
        courseMeetings[course2name] = teamToMeetings(
            course2teams,
            course2name
        )
        courseMeetings[course3name] = teamToMeetings(
            course3teams,
            course3name
        )

        return courseMeetings
    }

    private fun teamToMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
        val meetings = mutableSetOf<Meeting>()

        val meetingTeams: Array<Team?> = Array(3) { null }

        for ((index, value) in teams.withIndex()) {
            meetingTeams[index % 3] = value

            if (index % 3 == 2) {
                meetings.add(
                    Meeting(
                        courseName,
                        meetingTeams.filterNotNull()
                    )
                )
            }
        }

        return meetings
    }
}