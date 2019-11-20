package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.rest.participant.MailAddress

data class OrganisationRequest(
    val name: String,
    val mail: String
) {
    fun toOrganisation(): Organisation {
        return Organisation(
            name = this.name,
            mail = MailAddress(mail = this.mail)
        )
    }
}