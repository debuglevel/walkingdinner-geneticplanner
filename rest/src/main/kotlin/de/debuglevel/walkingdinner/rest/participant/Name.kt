package de.debuglevel.walkingdinner.rest.participant

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Name(
    val name: String,

    @Id
    @GeneratedValue
    val id: UUID? = null
) {
    val firstname = extractFirstname()

    val lastname = extractLastname()

    val abbreviatedName = run {
        val firstletter = firstname.toCharArray().first()
        "$firstletter. $lastname"
    }

    private fun extractFirstname(): String {
        return this.name.split(" ").first()
    }

    private fun extractLastname(): String {
        return this.name.split(" ").last()
    }

    override fun toString(): String {
        return name
    }
}