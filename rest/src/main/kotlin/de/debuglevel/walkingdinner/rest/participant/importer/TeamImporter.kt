package de.debuglevel.walkingdinner.rest.participant.importer

import de.debuglevel.walkingdinner.rest.participant.Team

interface TeamImporter {
    fun import(): List<Team>
}