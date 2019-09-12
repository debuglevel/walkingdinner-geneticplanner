package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime
import java.util.*

data class DinnerResponse(
    val id: UUID?,
    val name: String,
    val beginDateTime: LocalDateTime
)