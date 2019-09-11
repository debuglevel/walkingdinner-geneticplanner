package de.debuglevel.walkingdinner.rest.organisation

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Organisation(
    val name: String,
    //val dinners: Set<Dinner>,
    @Id
    @GeneratedValue
    val id: UUID? = null
)