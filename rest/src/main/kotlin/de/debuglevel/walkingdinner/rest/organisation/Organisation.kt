package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.rest.dinner.Dinner
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Organisation(
    val name: String,
    val dinners: Set<Dinner>,
    @BsonId val id: Id<Organisation> = newId()
)