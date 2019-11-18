package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime

data class DinnerRequest(
    val name: String,

    val city: String,

    val begin: LocalDateTime
) {
    fun toDinner(): Dinner {
        return Dinner(
            name = this.name,
            city = this.city,
            begin = this.begin
        )
    }
}