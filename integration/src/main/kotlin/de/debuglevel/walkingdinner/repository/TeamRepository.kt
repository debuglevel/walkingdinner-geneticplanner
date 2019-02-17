package de.debuglevel.walkingdinner.repository

import de.debuglevel.walkingdinner.model.team.Team
import org.litote.kmongo.*

object TeamRepository {
    fun save(team: Team) {
        // insert or replace
        MongoDatabase.database.getCollection<Team>().save(team)
    }

    fun add(team: Team) {
        MongoDatabase.database.getCollection<Team>().insertOne(team)
    }

    fun getAll(): List<Team> {
        return MongoDatabase.database.getCollection<Team>().find().toList()
    }

    fun get(id: String): Team {
        return MongoDatabase.database
            .getCollection<Team>()
            .findOne(Team::id eq id)
            ?: throw MongoDatabase.ObjectNotFoundException(id)
    }

    fun update(team: Team) {
        MongoDatabase.database.getCollection<Team>().updateOne(team)
    }
}