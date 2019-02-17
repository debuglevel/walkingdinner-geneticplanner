package de.debuglevel.walkingdinner.repository

import de.debuglevel.walkingdinner.model.organisation.Organisation
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.litote.kmongo.*

object OrganisationRepository {
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

    fun getAll(): List<Organisation> {
        return MongoDatabase.database.getCollection<Organisation>()
            .find()
            .toList()
    }

    fun get(id: String): Organisation {
        logger.debug { "String ID '$id' is ID '${id.toId<Organisation>()}'" }

        return MongoDatabase.database
            .getCollection<Organisation>()
            .findOne(Organisation::id eq ObjectId(id))
            ?: throw MongoDatabase.ObjectNotFoundException(id)
    }
//
//    fun update(team: Team)
//    {
//        MongoDatabase.database.getCollection<Team>().updateOne(team)
//    }
}