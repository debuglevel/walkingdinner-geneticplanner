package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Dinner(
    val name: String,
    val begin: LocalDateTime,
//    val teams: Set<Team>,
//    val plans: Set<Plan>,
    // TODO: a location (i.e. a city) would probably also useful
    @Id
    @GeneratedValue
    val id: UUID? = null
)