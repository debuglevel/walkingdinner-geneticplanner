package de.debuglevel.walkingdinner.rest.participant

import java.util.*

data class TeamResponse(
    val id: UUID,
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
    constructor(team: Team) :
            this(
                id = team.id!!,
                address = team.address,
                chef1 = team.cook1.name.name,
                chef2 = team.cook2.name.name,
                mail1 = team.cook1.mail.mail,
                mail2 = team.cook2.mail.mail,
                phone1 = team.cook1.phoneNumber.number,
                phone2 = team.cook2.phoneNumber.number,
                diet = team.diet,
                veganAppetizer = team.cookingCapabilities.any { it == CookingCapability.VeganAppetizer },
                veganMaindish = team.cookingCapabilities.any { it == CookingCapability.VeganMaindish },
                veganDessert = team.cookingCapabilities.any { it == CookingCapability.VeganDessert },
                vegetarianAppetizer = team.cookingCapabilities.any { it == CookingCapability.VegetarianAppetizer },
                vegetarianMaindish = team.cookingCapabilities.any { it == CookingCapability.VegetarianMaindish },
                vegetarianDessert = team.cookingCapabilities.any { it == CookingCapability.VegetarianDessert },
                omnivoreAppetizer = team.cookingCapabilities.any { it == CookingCapability.OmnivoreAppetizer },
                omnivoreMaindish = team.cookingCapabilities.any { it == CookingCapability.OmnivoreMaindish },
                omnivoreDessert = team.cookingCapabilities.any { it == CookingCapability.OmnivoreDessert },
                notes = "TODO",
                city = team.city
            )
}