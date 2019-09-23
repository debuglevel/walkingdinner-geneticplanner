package de.debuglevel.walkingdinner.rest.participant

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class MailAddress(
    val mail: String,
    @Id
    @GeneratedValue
    val id: UUID? = null
) {
    override fun toString(): String = mail
}