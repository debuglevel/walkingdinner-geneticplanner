package rocks.huwi.walkingdinnerplanner.geneticplanner.imports

import rocks.huwi.walkingdinnerplanner.geneticplanner.team.Team

interface TeamImporter
{
    fun import(): List<Team>
}