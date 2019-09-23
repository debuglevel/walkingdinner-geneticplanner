package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.rest.Meeting
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Plan(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    @OneToMany
    val meetings: Set<Meeting>,
    val additionalInformation: String
)
