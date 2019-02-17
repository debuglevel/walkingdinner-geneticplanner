package de.debuglevel.walkingdinner.repository

import de.debuglevel.walkingdinner.model.dinner.Dinner
import de.debuglevel.walkingdinner.model.organisation.Organisation
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import org.litote.kmongo.id.toId
import org.litote.kmongo.toList

object DinnerRepository {
    private val logger = KotlinLogging.logger {}

//    fun save(team: Team)
//    {
//        // insert or replace
//        MongoDatabase.database.getCollection<Team>().save(team)
//    }
//
//    fun add(team: Team)
//    {
//        MongoDatabase.database.getCollection<Team>().insertOne(team)
//    }

//    fun getAll(): List<Dinner>
//    {
//        return MongoDatabase.database.getCollection<Dinner>()
//            .find()
//            .toList()
//    }

    fun get(id: String): Dinner {
        val objectId = ObjectId(id)
        val dinner = MongoDatabase.database
            .getCollection<Organisation>()
            .find(Organisation::dinners / Dinner::id eq objectId)
            .toList()
            .flatMap { it.dinners }
            .find { it.id == objectId.toId<Dinner>() }

        return dinner ?: throw MongoDatabase.ObjectNotFoundException(id)
    }
//
//    fun update(team: Team)
//    {
//        MongoDatabase.database.getCollection<Team>().updateOne(team)
//    }
}