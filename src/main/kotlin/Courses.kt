import io.jenetics.util.ISeq
import java.util.HashSet

data class Courses(val course1teams: ISeq<Team>,
                   val course2teams: ISeq<Team>,
                   val course3teams: ISeq<Team>) {

    fun toMeetings(): Set<Meeting> {
        val meetings = HashSet<Meeting>()
        meetings.addAll(Team.toMeetings(course1teams, "Vorspeise"))
        meetings.addAll(Team.toMeetings(course2teams, "Hauptspeise"))
        meetings.addAll(Team.toMeetings(course3teams, "Dessert"))

        return meetings
    }
}