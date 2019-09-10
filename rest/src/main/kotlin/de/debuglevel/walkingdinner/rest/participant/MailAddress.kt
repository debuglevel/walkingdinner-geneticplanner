package de.debuglevel.walkingdinner.rest.participant

data class MailAddress(val mail: String) {
    override fun toString(): String = mail
}