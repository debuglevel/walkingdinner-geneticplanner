package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.Plan
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.LocalDateTime

data class Dinner(
    val name: String,
    val datetime: LocalDateTime,
    val teams: Set<Team>,
    val plans: Set<Plan>,
    @BsonId val id: Id<Dinner> = newId()
)