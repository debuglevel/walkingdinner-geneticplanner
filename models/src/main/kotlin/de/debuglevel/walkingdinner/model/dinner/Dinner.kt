package de.debuglevel.walkingdinner.model.dinner

import de.debuglevel.walkingdinner.model.Plan
import de.debuglevel.walkingdinner.model.team.Team
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