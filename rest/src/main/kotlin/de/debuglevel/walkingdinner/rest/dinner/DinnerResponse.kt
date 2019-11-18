package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime
import java.util.*

data class DinnerResponse(
    val id: UUID,

    val name: String,

    val city: String,

    val begin: LocalDateTime
) {
    constructor(dinner: Dinner) :
            this(
                dinner.id!!,
                dinner.name,
                dinner.city,
                dinner.begin
            )

    override fun toString(): String {
        return "DinnerResponse(id=$id, name='$name', city='$city', begin=$begin)"
    }
}