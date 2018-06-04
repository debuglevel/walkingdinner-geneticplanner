package rocks.huwi.walkingdinnerplanner.model

import io.jenetics.util.ISeq
import rocks.huwi.walkingdinnerplanner.model.team.Team
import java.util.*
import java.util.stream.Collectors

data class Courses(val course1teams: ISeq<Team>,
                   val course2teams: ISeq<Team>,
                   val course3teams: ISeq<Team>) {

    companion object {
        const val course1name = "Vorspeise"
        const val course2name = "Hauptspeise"
        const val course3name = "Dessert"
    }

    fun toMeetings(): Set<Meeting> {
        return toCourseMeetings().values.stream()
                .flatMap { it.stream() }
                .collect(Collectors.toSet())
    }

    fun toCourseMeetings(): Map<String, Set<Meeting>> {
        val courseMeetings = hashMapOf<String, Set<Meeting>>()

        courseMeetings[course1name] = Team.toMeetings(course1teams, course1name)
        courseMeetings[course2name] = Team.toMeetings(course2teams, course2name)
        courseMeetings[course3name] = Team.toMeetings(course3teams, course3name)

        return courseMeetings
    }
}