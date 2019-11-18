package de.debuglevel.walkingdinner.rest.organisation

import java.util.*

data class OrganisationResponse(
    val id: UUID?,

    val name: String
) {
    constructor(organisation: Organisation) :
            this(
                organisation.id!!,
                organisation.name
            )

    override fun toString(): String {
        return "OrganisationResponse(id=$id, name='$name')"
    }
}