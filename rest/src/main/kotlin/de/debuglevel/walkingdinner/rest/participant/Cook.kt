package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.PhoneNumber
import java.util.*
import javax.persistence.*

@Entity
data class Cook(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val name: Name,

    @OneToOne(cascade = [CascadeType.ALL])
    val mail: MailAddress,

    @OneToOne(cascade = [CascadeType.ALL])
    val phoneNumber: PhoneNumber
) {
    override fun toString(): String {
        //return "$name ($mail, $phoneNumber)"
        return "Cook(id=$id)"
    }
}