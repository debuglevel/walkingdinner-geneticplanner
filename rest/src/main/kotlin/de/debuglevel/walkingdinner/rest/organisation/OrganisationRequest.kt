package de.debuglevel.walkingdinner.rest.organisation

data class OrganisationRequest(
    val name: String
) {
    fun toOrganisation(): Organisation {
        return Organisation(
            name = this.name
        )
    }
}