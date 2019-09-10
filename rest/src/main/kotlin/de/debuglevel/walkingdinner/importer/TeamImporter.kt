package de.debuglevel.walkingdinner.importer

import de.debuglevel.walkingdinner.rest.participant.Team

interface TeamImporter {
    fun import(): List<Team>
}