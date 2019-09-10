package de.debuglevel.walkingdinner.repository

import de.debuglevel.walkingdinner.rest.organisation.Organisation
import org.litote.kmongo.getCollection

fun main() {
    MongoDatabase.database.getCollection<Organisation>().find().forEach {
        println(it)
    }

//    println(
//        MongoDatabase.database.getCollection<Organisation>()
//            .findOne(Organisation::id eq ObjectId("5c685281945ce01588cd1d78"))
//    )
}