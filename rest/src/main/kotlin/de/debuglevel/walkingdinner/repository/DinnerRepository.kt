package de.debuglevel.walkingdinner.repository

import de.debuglevel.walkingdinner.model.dinner.Dinner
import mu.KotlinLogging

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
        TODO()
//        val objectId = ObjectId(id)
//        val dinner = MongoDatabase.database
//            .getCollection<Organisation>()
//            .find(Organisation::dinners / Dinner::id eq objectId)
//            .toList()
//            .flatMap { it.dinners }
//            .find { it.id == objectId.toId<Dinner>() }
//
//        return dinner ?: throw MongoDatabase.ObjectNotFoundException(id)
    }
//
//    fun update(team: Team)
//    {
//        MongoDatabase.database.getCollection<Team>().updateOne(team)
//    }
}