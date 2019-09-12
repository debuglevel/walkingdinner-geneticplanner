package de.debuglevel.walkingdinner.rest.dinner

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class DinnerRequest(
    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val beginDateTime: LocalDateTime
)