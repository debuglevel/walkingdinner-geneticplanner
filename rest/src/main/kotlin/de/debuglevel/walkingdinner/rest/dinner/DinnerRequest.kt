package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime

data class DinnerRequest(
    val name: String,

    val beginDateTime: LocalDateTime
)