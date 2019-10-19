package de.debuglevel.walkingdinner.rest.participant

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class TeamService(
    private val teamRepository: TeamRepository
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Team {
        logger.debug { "Getting team '$id'..." }
        val team = teamRepository.findById(id).orElseThrow { TeamNotFoundException(id) }
        logger.debug { "Got team: $team" }
        return team
    }

    fun getAll(): Set<Team> {
        logger.debug { "Getting all teams..." }
        val teams = teamRepository
            .findAll()
            .toSet()
        logger.debug { "Got all teams" }
        return teams
    }

    fun add(team: Team): Team {
        logger.debug { "Saving team '$team'..." }

        val savedTeam = teamRepository.save(team)

        logger.debug { "Saved team: $savedTeam" }
        return savedTeam
    }

    class TeamNotFoundException(teamId: UUID) : Exception("Team $teamId not found")
}