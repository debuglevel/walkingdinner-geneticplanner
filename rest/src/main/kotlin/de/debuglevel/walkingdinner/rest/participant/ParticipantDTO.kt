package de.debuglevel.walkingdinner.rest.participant

// TODO: variables should be in english
// XXX: use nullable types as GSON could always produce null values in non-null types via reflection anyway.
data class ParticipantDTO(
    val address: String?,
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
    val anmerkungen: String?,
    val city: String?
) {

    private val cookingCapabilities: List<CookingCapability>
        get() {
            val capabilities = hashMapOf<CookingCapability, Boolean>()
            capabilities[CookingCapability.VeganAppetizer] = veganVorspeise ?: false
            capabilities[CookingCapability.VeganMaindish] = veganHauptgericht ?: false
            capabilities[CookingCapability.VeganDessert] = veganDessert ?: false
            capabilities[CookingCapability.VegetarianAppetizer] = vegetarischVorspeise ?: false
            capabilities[CookingCapability.VegetarianMaindish] = vegetarischHauptgericht ?: false
            capabilities[CookingCapability.VegetarianDessert] = vegetarischDessert ?: false
            capabilities[CookingCapability.OmnivoreAppetizer] = omnivorVorspeise ?: false
            capabilities[CookingCapability.OmnivoreMaindish] = omnivorHauptgericht ?: false
            capabilities[CookingCapability.OmnivoreDessert] = omnivorDessert ?: false

            return capabilities.filter { it.value }.map { it.key }
        }

    fun toTeam(): Team {
        return Team(
            null,
            Cook(
                name = Name(name = chef1 ?: throw IllegalArgumentException("chef1")),
                mail = MailAddress(mail = mail1 ?: throw IllegalArgumentException("mail1")),
                phoneNumber = PhoneNumber(number = phone1 ?: throw IllegalArgumentException("phone1"))
            ),
            Cook(
                name = Name(name = chef2 ?: throw IllegalArgumentException("chef2")),
                mail = MailAddress(mail = mail2 ?: throw IllegalArgumentException("mail2")),
                phoneNumber = PhoneNumber(number = phone2 ?: throw IllegalArgumentException("phone2"))
            ),
            address ?: throw IllegalArgumentException("address"),
            diet ?: throw IllegalArgumentException("diet"),
            cookingCapabilities,
            null,
            city ?: throw IllegalArgumentException("city")
        )
    }
}