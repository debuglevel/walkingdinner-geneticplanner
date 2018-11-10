package de.debuglevel.walkingdinner.importer

import de.debuglevel.walkingdinner.model.team.Team

interface TeamImporter {
    fun import(): List<Team>
}