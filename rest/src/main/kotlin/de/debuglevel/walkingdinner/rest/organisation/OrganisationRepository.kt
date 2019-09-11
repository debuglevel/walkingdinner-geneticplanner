package de.debuglevel.walkingdinner.rest.organisation

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface OrganisationRepository : CrudRepository<Organisation, UUID> {
//    fun find(id: UUID): Organisation


//    //private val logger = KotlinLogging.logger {}
//
////    fun save(team: Team)
////    {
////        // insert or replace
////        MongoDatabase.database.getCollection<Team>().save(team)
////    }
////
////    fun add(team: Team)
////    {
////        MongoDatabase.database.getCollection<Team>().insertOne(team)
////    }
//
//    fun getAll(): List<Organisation> {
//        return MongoDatabase.database.getCollection<Organisation>()
//            .find()
//            .toList()
//    }
//
//    fun get(id: String): Organisation {
//        TODO()
////        logger.debug { "String ID '$id' is ID '${id.toId<Organisation>()}'" }
//
////        return MongoDatabase.database
////            .getCollection<Organisation>()
////            .findOne(Organisation::id eq ObjectId(id))
////            ?: throw MongoDatabase.ObjectNotFoundException(id)
//    }
////
////    fun update(team: Team)
////    {
////        MongoDatabase.database.getCollection<Team>().updateOne(team)
////    }
}