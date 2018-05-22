package rocks.huwi.walkingdinner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinner.geneticplanner.Team

object HardCompatibility : Compatibility {
    override fun areCompatibleTeams(teams: Array<Team>): Boolean {
        return teams.all { isCompatibleDiet(teams.first(), it) }
    }

    override fun isCompatibleDiet(a: Team, b: Team) = a.diet == b.diet
}