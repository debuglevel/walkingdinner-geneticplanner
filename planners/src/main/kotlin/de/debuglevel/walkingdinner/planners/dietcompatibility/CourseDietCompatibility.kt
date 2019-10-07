package de.debuglevel.walkingdinner.planners.dietcompatibility

import de.debuglevel.walkingdinner.planners.Courses
import de.debuglevel.walkingdinner.planners.Meeting
import de.debuglevel.walkingdinner.planners.Team
import mu.KotlinLogging

/**
 * This diet compatibility will check for a "downwards compatible" diet while checking for the team's cooking capability:
 * A vegetarian can eat vegan food, but a vegan cannot eat vegetarian food.
 * One vegetarian might by able to cook a vegan meal while another one does not know how to do that.
 * This mixes teams based on their diet and capabilities.
 */
object CourseDietCompatibility : DietCompatibility {
    private val logger = KotlinLogging.logger {}

    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        val cook = meeting.getCookingTeam()
        val otherTeams = meeting.teams.filter { it != cook }

        return isCapabilityCompatible(
            meeting.course,
            cook.cookingCapabilities.filterNotNull(),
            otherTeams
        )
    }

    private fun isCapabilityCompatible(
        course: String,
        cookingCapabilities: List<CookingCapability>,
        teams: List<Team>
    ): Boolean {
        return teams.all {
            isCapabilityCompatible(
                course,
                cookingCapabilities,
                it
            )
        }
    }

    private fun isCapabilityCompatible(
        course: String,
        cookingCapabilities: List<CookingCapability>,
        otherTeam: Team
    ): Boolean {
        when (otherTeam.diet) {
            Diet.Vegan -> {
                when (course) {
                    Courses.course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganVorspeise)
                    Courses.course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganHauptgericht)
                    Courses.course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert)
                }
            }

            Diet.Vegetarisch -> {
                when (course) {
                    Courses.course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganVorspeise) ||
                                cookingCapabilities.contains(CookingCapability.VegetarischVorspeise)
                    Courses.course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganHauptgericht) ||
                                cookingCapabilities.contains(CookingCapability.VegetarischHauptgericht)
                    Courses.course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert) ||
                                cookingCapabilities.contains(CookingCapability.VegetarischDessert)
                }
            }

            Diet.Omnivore -> {
                when (course) {
                    Courses.course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganVorspeise) ||
                                cookingCapabilities.contains(CookingCapability.VegetarischVorspeise) ||
                                cookingCapabilities.contains(CookingCapability.OmnivorVorspeise)
                    Courses.course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganHauptgericht) ||
                                cookingCapabilities.contains(CookingCapability.VegetarischHauptgericht) ||
                                cookingCapabilities.contains(CookingCapability.OmnivorHauptgericht)
                    Courses.course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert) ||
                                cookingCapabilities.contains(CookingCapability.VegetarischDessert) ||
                                cookingCapabilities.contains(CookingCapability.OmnivorDessert)
                }
            }
        }

        logger.error("CourseCompatibility.isCapabilityCompatible(): Some case was not caught. This should not happen.")
        return false
    }
}