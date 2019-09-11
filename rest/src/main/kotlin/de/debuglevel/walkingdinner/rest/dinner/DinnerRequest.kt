package de.debuglevel.walkingdinner.rest.dinner

import java.time.ZonedDateTime

data class DinnerRequest(
    val name: String,
    val begin: ZonedDateTime
)