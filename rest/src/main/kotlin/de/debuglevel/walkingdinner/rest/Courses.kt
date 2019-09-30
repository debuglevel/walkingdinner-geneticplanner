package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.rest.participant.Team

data class Courses(
    val course1teams: Iterable<Team>,
    val course2teams: Iterable<Team>,
    val course3teams: Iterable<Team>
) {

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

        courseMeetings[course1name] = Team.toMeetings(
            course1teams,
            course1name
        )
        courseMeetings[course2name] = Team.toMeetings(
            course2teams,
            course2name
        )
        courseMeetings[course3name] = Team.toMeetings(
            course3teams,
            course3name
        )

        return courseMeetings
    }
}