package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.rest.MailAddress
import java.util.*
import javax.persistence.*

@Entity
data class Organisation(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    //val dinners: Set<Dinner>,

    @OneToOne(cascade = [CascadeType.ALL])
    val mail: MailAddress,

    val name: String
)