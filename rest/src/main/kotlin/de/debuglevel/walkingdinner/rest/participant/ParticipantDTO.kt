package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.model.dietcompatibility.Capability
import de.debuglevel.walkingdinner.model.dietcompatibility.Diet
import de.debuglevel.walkingdinner.model.team.*

// XXX: use nullable types as GSON could always produce null values in non-null types via reflection anway.
data class ParticipantDTO(val address: String?,
                          val chef1: String?,
                          val chef2: String?,
                          val mail1: String?,
                          val mail2: String?,
                          val phone1: String?,
                          val phone2: String?,
                          val diet: Diet?,
                          val veganVorspeise: Boolean?,
                          val veganHauptgericht: Boolean?,
                          val veganDessert: Boolean?,
                          val vegetarischVorspeise: Boolean?,
                          val vegetarischHauptgericht: Boolean?,
                          val vegetarischDessert: Boolean?,
                          val omnivorVorspeise: Boolean?,
                          val omnivorHauptgericht: Boolean?,
                          val omnivorDessert: Boolean?,
                          val anmerkungen: String?) {

    private val capabilities: List<Capability>
        get() {
            val capabilities = hashMapOf<Capability, Boolean>()
            capabilities[Capability.VeganVorspeise] = veganVorspeise ?: false
            capabilities[Capability.VeganHauptgericht] = veganHauptgericht ?: false
            capabilities[Capability.VeganDessert] = veganDessert ?: false
            capabilities[Capability.VegetarischVorspeise] = vegetarischVorspeise ?: false
            capabilities[Capability.VegetarischHauptgericht] = vegetarischHauptgericht ?: false
            capabilities[Capability.VegetarischDessert] = vegetarischDessert ?: false
            capabilities[Capability.OmnivorVorspeise] = omnivorVorspeise ?: false
            capabilities[Capability.OmnivorHauptgericht] = omnivorHauptgericht ?: false
            capabilities[Capability.OmnivorDessert] = omnivorDessert ?: false

            return capabilities.filter { it.value }.map { it.key }
        }

    fun toTeam(): Team {
        return Team(
                Cook(
                        Name(chef1 ?: throw IllegalArgumentException("chef1")),
                        MailAddress(mail1 ?: throw IllegalArgumentException("mail1")),
                        PhoneNumber(phone1 ?: throw IllegalArgumentException("phone1"))
                ),
                Cook(
                        Name(chef2 ?: throw IllegalArgumentException("chef2")),
                        MailAddress(mail2 ?: throw IllegalArgumentException("mail2")),
                        PhoneNumber(phone2 ?: throw IllegalArgumentException("phone2"))
                ),
                address ?: throw IllegalArgumentException("address"),
                diet?.toString() ?: throw IllegalArgumentException("diet"),
                capabilities,
                null
        )
    }
}