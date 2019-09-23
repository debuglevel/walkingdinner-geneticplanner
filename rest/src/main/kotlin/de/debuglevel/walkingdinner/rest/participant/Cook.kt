package de.debuglevel.walkingdinner.rest.participant

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Cook(
    @OneToOne
    val name: Name,
    @OneToOne
    val mail: MailAddress,
    @OneToOne
    val phoneNumber: PhoneNumber,
    @Id
    @GeneratedValue
    val id: UUID? = null
) {
    override fun toString(): String {
        return "$name ($mail, $phoneNumber)"
    }
}