package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.location.Location
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Capability
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Diet
import java.util.*
import javax.persistence.*

@Entity
data class Team(
    @OneToOne
    val cook1: Cook,
    @OneToOne
    val cook2: Cook,
    val address: String,
    @Enumerated(EnumType.STRING)
    val diet: Diet,

    @ElementCollection()
    @Enumerated(EnumType.STRING)
    val capabilities: List<Capability>,
    @OneToOne
    var location: Location?,
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val city: String
) {

    override fun toString(): String {
        return "Team $id: $cook1 & $cook2 ($diet; $location)"
    }

    companion object {
        fun toMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
            val meetings = mutableSetOf<Meeting>()

            val meetingTeams: Array<Team?> = Array(3) { null }

            for ((index, value) in teams.withIndex()) {
                meetingTeams[index % 3] = value

                if (index % 3 == 2) {
                    meetings.add(
                        Meeting(
                            meetingTeams.filterNotNull(),
                            courseName
                        )
                    )
                }
            }

            return meetings
        }
    }
}