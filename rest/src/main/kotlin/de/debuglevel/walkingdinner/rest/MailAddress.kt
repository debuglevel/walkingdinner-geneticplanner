package de.debuglevel.walkingdinner.rest

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class MailAddress(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val mail: String
) {
    override fun toString(): String {
        return mail
    }
}