package de.debuglevel.walkingdinner.repository

import com.mongodb.ConnectionString
import de.debuglevel.walkingdinner.Configuration
import org.litote.kmongo.KMongo

object MongoDatabase {
    private val client = KMongo.createClient(ConnectionString(Configuration.mongoDbConnectionString))
    val database = client.getDatabase("walkingdinner")

    data class ObjectNotFoundException(val id: String) : Exception("Object with id '$id' not found")
}