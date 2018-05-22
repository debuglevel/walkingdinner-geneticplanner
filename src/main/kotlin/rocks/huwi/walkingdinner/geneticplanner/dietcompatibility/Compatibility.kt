package rocks.huwi.walkingdinner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinner.geneticplanner.Team

interface Compatibility {
    fun areCompatibleTeams(teams: Array<Team>): Boolean
    fun isCompatibleDiet(a: Team, b: Team): Boolean
}