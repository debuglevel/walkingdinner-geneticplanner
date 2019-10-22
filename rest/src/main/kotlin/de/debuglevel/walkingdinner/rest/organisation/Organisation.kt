package de.debuglevel.walkingdinner.rest.organisation

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Organisation(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    //val dinners: Set<Dinner>,

    val name: String
)