package de.debuglevel.walkingdinner.rest.participant

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Transient

@Entity
data class Name(
    val name: String,
    @Id
    @GeneratedValue
    val id: UUID? = null
) {

    @delegate:Transient
    @get:Transient
    val firstname: String by lazy { extractFirstname() }

    @delegate:Transient
    @get:Transient
    val lastname: String by lazy { extractLastname() }

    @delegate:Transient
    @get:Transient
    val abbreviatedName: String by lazy {
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