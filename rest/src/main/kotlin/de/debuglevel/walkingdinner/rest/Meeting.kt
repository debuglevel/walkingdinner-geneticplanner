package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.*
import java.util.stream.Collectors
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

    fun getCookingTeam(): Team = teams.first()

    private fun isCookingTeam(team: Team): Boolean = this.getCookingTeam() == team

    override fun toString(): String {
        return teams.stream()
            .map { it ->
                val teamText = "${it.cook1.name} & ${it.cook2.name}"
                if (isCookingTeam(it)) "[$teamText]" else teamText
            }
            .collect(Collectors.joining("\t"))
    }
}