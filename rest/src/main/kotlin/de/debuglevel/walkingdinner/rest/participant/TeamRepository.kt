//package de.debuglevel.walkingdinner.rest.participant
//
//import de.debuglevel.walkingdinner.repository.MongoDatabase
//import org.litote.kmongo.getCollection
//import org.litote.kmongo.save
//import org.litote.kmongo.toList
//import org.litote.kmongo.updateOne
//
//object TeamRepository {
//    fun save(team: Team) {
//        // insert or replace
//        MongoDatabase.database.getCollection<Team>().save(team)
//    }
//
//    fun add(team: Team) {
//        MongoDatabase.database.getCollection<Team>().insertOne(team)
//    }
//
//    fun getAll(): List<Team> {
//        return MongoDatabase.database.getCollection<Team>().find().toList()
//    }
//
//    fun get(id: String): Team {
//        TODO()
////        return MongoDatabase.database
////            .getCollection<Team>()
////            .findOne(Team::id eq id)
////            ?: throw MongoDatabase.ObjectNotFoundException(id)
//    }
//
//    fun update(team: Team) {
//        MongoDatabase.database.getCollection<Team>().updateOne(team)
//    }
//}