package de.debuglevel.walkingdinner.rest.plan.report.teams

import de.debuglevel.walkingdinner.rest.participant.Team

data class TextReport(
    val team: Team,
    val text: String
)