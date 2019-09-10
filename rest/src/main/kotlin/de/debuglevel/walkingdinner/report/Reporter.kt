package de.debuglevel.walkingdinner.report

import de.debuglevel.walkingdinner.rest.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}