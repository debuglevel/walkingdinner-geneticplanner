package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.PhoneNumber

data class TeamRequest(
    val address: String?,
    val chef1: String?,
    val chef2: String?,
    val mail1: String?,
    val mail2: String?,
    val phone1: String?,
    val phone2: String?,
    val diet: Diet?,
    val veganAppetizer: Boolean?,
    val veganMaindish: Boolean?,
    val veganDessert: Boolean?,
    val vegetarianAppetizer: Boolean?,
    val vegetarianMaindish: Boolean?,
    val vegetarianDessert: Boolean?,
    val omnivoreAppetizer: Boolean?,
    val omnivoreMaindish: Boolean?,
    val omnivoreDessert: Boolean?,
    val notes: String?,
    val city: String?
) {
    private val cookingCapabilities: List<CookingCapability>
        get() {
            val capabilities = hashMapOf<CookingCapability, Boolean>()
            capabilities[CookingCapability.VeganAppetizer] = veganAppetizer ?: false
            capabilities[CookingCapability.VeganMaindish] = veganMaindish ?: false
            capabilities[CookingCapability.VeganDessert] = veganDessert ?: false
            capabilities[CookingCapability.VegetarianAppetizer] = vegetarianAppetizer ?: false
            capabilities[CookingCapability.VegetarianMaindish] = vegetarianMaindish ?: false
            capabilities[CookingCapability.VegetarianDessert] = vegetarianDessert ?: false
            capabilities[CookingCapability.OmnivoreAppetizer] = omnivoreAppetizer ?: false
            capabilities[CookingCapability.OmnivoreMaindish] = omnivoreMaindish ?: false
            capabilities[CookingCapability.OmnivoreDessert] = omnivoreDessert ?: false

            return capabilities.filter { it.value }.map { it.key }
        }

    fun toTeam(): Team {
        return Team(
            null,
            Cook(
                name = Name(name = chef1 ?: throw IllegalArgumentException("chef1")),
                mail = MailAddress(
                    mail = mail1 ?: throw IllegalArgumentException("mail1")
                ),
                phoneNumber = PhoneNumber(
                    number = phone1 ?: throw IllegalArgumentException("phone1")
                )
            ),
            Cook(
                name = Name(name = chef2 ?: throw IllegalArgumentException("chef2")),
                mail = MailAddress(
                    mail = mail2 ?: throw IllegalArgumentException("mail2")
                ),
                phoneNumber = PhoneNumber(
                    number = phone2 ?: throw IllegalArgumentException("phone2")
                )
            ),
            address ?: throw IllegalArgumentException("address"),
            diet ?: throw IllegalArgumentException("diet"),
            cookingCapabilities,
            null,
            city ?: throw IllegalArgumentException("city")
        )
    }
}