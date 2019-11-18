package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.calculation.Calculation
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Dinner(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val name: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val teams: Set<Team> = setOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    val calculations: Set<Calculation> = setOf(),

    val city: String,

    val begin: LocalDateTime
) {
    override fun toString(): String {
        return "Dinner(id=$id, name='$name', city='$city', begin=$begin)"
    }
}