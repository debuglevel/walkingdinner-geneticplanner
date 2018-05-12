package rocks.huwi.walkingdinner.geneticplanner

import com.opencsv.bean.CsvBindByName
import java.util.*

class Team {
    fun isCompatibleDiet(o: Team) = diet == o.diet

    override fun toString(): String {
        return "Team $id ($diet)"
    }

    var id: Long = -1

    @CsvBindByName(column = "Koch1")
    var cook1: String = ""

    @CsvBindByName(column = "Koch2")
    var cook2: String = ""

    @CsvBindByName(column = "Adresse")
    var address: String = ""

    @CsvBindByName(column = "Diet")
    private var diet: String = ""

    lateinit var location: Location

    companion object {
        fun toMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
            val meetings = HashSet<Meeting>()

            val meetingTeams = mutableListOf<Team>()
            for (team in teams) {
                meetingTeams.add(team)

                if (meetingTeams.size == 3) {
                    meetings.add(Meeting(meetingTeams.toTypedArray(), courseName))
                    meetingTeams.clear()
                }
            }

            return meetings
        }
    }
}