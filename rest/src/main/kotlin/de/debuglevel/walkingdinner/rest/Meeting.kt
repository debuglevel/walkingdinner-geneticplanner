package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.*
import javax.persistence.*

@Entity
data class Meeting(
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val teams: List<Team>,

    val course: String,

    @Id
    @GeneratedValue
    val id: UUID? = null
) {

    /**
     * Get the team which is cooking in this meeting
     * @implNote The cooking team is defined as the first one in the list
     */
    fun getCookingTeam(): Team {
        return teams.first()
    }

    override fun toString(): String {
        return "Meeting(id=$id)"
    }
}