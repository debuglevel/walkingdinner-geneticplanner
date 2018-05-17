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
            val meetings = mutableSetOf<Meeting>()

            val meetingTeams = Array<Team>(3, { i -> Team() })

            for ((index, value) in teams.withIndex())
            {
                meetingTeams[index%3] = value

                if (index%3 == 2) {
                    meetings.add(Meeting(meetingTeams.clone(), courseName))
                }
            }

            return meetings
        }
    }
}