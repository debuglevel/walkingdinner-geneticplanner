package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Dinner(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val name: String,

//    val teams: Set<Team>,

//    val plans: Set<Plan>,

    // TODO: a location (i.e. a city) would probably also useful
    val begin: LocalDateTime
)