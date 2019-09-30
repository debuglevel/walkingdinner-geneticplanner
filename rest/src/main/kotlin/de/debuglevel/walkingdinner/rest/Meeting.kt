package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Meeting(
    @OneToMany
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

    private fun isCookingTeam(team: Team): Boolean {
        return this.getCookingTeam() == team
    }

    override fun toString(): String {
        return teams
            .map { it ->
                val teamText = "${it.cook1.name} & ${it.cook2.name}"
                if (isCookingTeam(it)) "[$teamText]" else teamText
            }
            .joinToString("\t")
    }
}