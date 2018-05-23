package rocks.huwi.walkingdinner.geneticplanner.team

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import rocks.huwi.walkingdinner.geneticplanner.Meeting
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

    @CsvCustomBindByName(column = "Telefon1", converter = PhoneNumber.ConvertPhoneNumber::class)
    var phone1 = PhoneNumber("")

    @CsvCustomBindByName(column = "Telefon2", converter = PhoneNumber.ConvertPhoneNumber::class)
    var phone2 = PhoneNumber("")

    @CsvBindByName(column = "Mail1")
    var mail1: String = ""

    @CsvBindByName(column = "Mail2")
    var mail2: String = ""

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