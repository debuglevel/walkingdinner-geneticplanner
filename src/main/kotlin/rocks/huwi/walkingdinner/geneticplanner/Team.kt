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
    var diet: String = ""

    lateinit var location: Location

    constructor()
    constructor(cook1: String, cook2: String, address: String, diet: String) {
        this.cook1 = cook1
        this.cook2 = cook2
        this.address = address
        this.diet = diet
    }

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