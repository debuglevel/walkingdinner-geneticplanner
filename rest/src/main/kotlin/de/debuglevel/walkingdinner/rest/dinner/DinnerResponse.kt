package de.debuglevel.walkingdinner.rest.dinner

import java.time.ZonedDateTime
import java.util.*

data class DinnerResponse(
    val id: UUID?,
    val name: String,
    val datetime: ZonedDateTime
)