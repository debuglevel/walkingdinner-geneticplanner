package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.participant.location.Location
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
    val cookingCapabilities: List<CookingCapability>,
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
}