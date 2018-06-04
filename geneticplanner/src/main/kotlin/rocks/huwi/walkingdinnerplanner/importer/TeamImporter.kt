package rocks.huwi.walkingdinnerplanner.importer

import rocks.huwi.walkingdinnerplanner.model.team.Team

interface TeamImporter {
    fun import(): List<Team>
}