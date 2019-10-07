package de.debuglevel.walkingdinner.planners.dietcompatibility

import de.debuglevel.walkingdinner.planners.Meeting
import de.debuglevel.walkingdinner.planners.Team
import mu.KotlinLogging

/**
 * This diet compatibility is defined by a simple "if all teams have the same diet, they are compatible".
 * With 3 diets, this will result in 3 virtual dinners running in parallel.
 */
object HardDietCompatibility : DietCompatibility {
    private val logger = KotlinLogging.logger {}

    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        return meeting.teams.all {
            isCompatibleDiet(
                meeting.teams.first(),
                it
            )
        }
    }

    private fun isCompatibleDiet(a: Team, b: Team): Boolean {
        return a.diet == b.diet
    }
}