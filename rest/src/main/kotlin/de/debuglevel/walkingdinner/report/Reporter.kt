package de.debuglevel.walkingdinner.report

import de.debuglevel.walkingdinner.model.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}