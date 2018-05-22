package rocks.huwi.walkingdinner.geneticplanner

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import rocks.huwi.walkingdinner.geneticplanner.dietcompatibility.Capability
import rocks.huwi.walkingdinner.geneticplanner.dietcompatibility.ConvertCapabilities
import rocks.huwi.walkingdinner.geneticplanner.location.Location

class Team {
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

    @CsvCustomBindByName(column = "Capabilities", converter = ConvertCapabilities::class)
    var capabilities: List<Capability?> = listOf()

    lateinit var location: Location

    companion object {
        fun toMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
            val meetings = mutableSetOf<Meeting>()

            val meetingTeams = Array(3, { i -> Team() })

            for ((index, value) in teams.withIndex()) {
                meetingTeams[index % 3] = value

                if (index % 3 == 2) {
                    meetings.add(Meeting(meetingTeams.clone(), courseName))
                }
            }

            return meetings
        }
    }
}