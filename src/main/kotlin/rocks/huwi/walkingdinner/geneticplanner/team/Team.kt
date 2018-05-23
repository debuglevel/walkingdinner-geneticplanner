package rocks.huwi.walkingdinner.geneticplanner.team

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import rocks.huwi.walkingdinner.geneticplanner.Meeting
import rocks.huwi.walkingdinner.geneticplanner.dietcompatibility.Capability
import rocks.huwi.walkingdinner.geneticplanner.location.Location


class Team {
    override fun toString(): String {
        return "Team $id ($diet)"
    }

    var id: Long = -1

    lateinit var cook1: Cook
    lateinit var cook2: Cook

    @CsvCustomBindByName(column = "Koch1", converter = Name.ConvertName::class)
    lateinit var name1: Name

    @CsvCustomBindByName(column = "Koch2", converter = Name.ConvertName::class)
    lateinit var name2: Name

    @CsvCustomBindByName(column = "Telefon1", converter = PhoneNumber.ConvertPhoneNumber::class)
    lateinit var phone1: PhoneNumber

    @CsvCustomBindByName(column = "Telefon2", converter = PhoneNumber.ConvertPhoneNumber::class)
    lateinit var phone2: PhoneNumber

    @CsvCustomBindByName(column = "Mail1", converter = MailAddress.ConvertMailAddress::class)
    lateinit var mail1: MailAddress

    @CsvCustomBindByName(column = "Mail2", converter = MailAddress.ConvertMailAddress::class)
    lateinit var mail2: MailAddress

    @CsvBindByName(column = "Adresse")
    lateinit var address: String

    @CsvBindByName(column = "Diet")
    lateinit var diet: String

    @CsvCustomBindByName(column = "Capabilities", converter = Capability.ConvertCapabilities::class)
    val capabilities: List<Capability?> = listOf()

    lateinit var location: Location

    fun constructComplexProperties() {
        initializeCooks()
    }

    private fun initializeCooks() {
        cook1 = Cook(name1, mail1, phone1)
        cook2 = Cook(name2, mail2, phone2)
    }

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