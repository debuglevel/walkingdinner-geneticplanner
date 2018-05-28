package rocks.huwi.walkingdinner.geneticplanner.imports

import rocks.huwi.walkingdinner.geneticplanner.team.Team

interface TeamImporter
{
    fun import(): List<Team>
}