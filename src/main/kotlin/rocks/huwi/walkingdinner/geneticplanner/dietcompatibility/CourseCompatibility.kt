package rocks.huwi.walkingdinner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinner.geneticplanner.Courses
import rocks.huwi.walkingdinner.geneticplanner.Meeting
import rocks.huwi.walkingdinner.geneticplanner.team.Team

object CourseCompatibility : Compatibility {
    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        val cook = meeting.getCookingTeam()
        val otherTeams = meeting.teams.filter { it != cook }

        return isCapabilityCompatible(meeting.course, cook.capabilities.filterNotNull(), otherTeams)
    }

    private fun isCapabilityCompatible(course: String, capabilities: List<Capability>, otherTeam: Team): Boolean {
        when (otherTeam.diet) {
            "Vegan" -> {
                when (course) {
                    Courses.course1name -> return capabilities.contains(Capability.VeganVorspeise)
                    Courses.course2name -> return capabilities.contains(Capability.VeganHauptgericht)
                    Courses.course3name -> return capabilities.contains(Capability.VeganDessert)
                }
            }
            "Vegetarisch" -> {
                when (course) {
                    Courses.course1name -> return capabilities.contains(Capability.VeganVorspeise) || capabilities.contains(Capability.VegetarischVorspeise)
                    Courses.course2name -> return capabilities.contains(Capability.VeganHauptgericht) || capabilities.contains(Capability.VegetarischHauptgericht)
                    Courses.course3name -> return capabilities.contains(Capability.VeganDessert) || capabilities.contains(Capability.VegetarischDessert)
                }
            }
            "Omnivore" -> {
                when (course) {
                    Courses.course1name -> return capabilities.contains(Capability.VeganVorspeise) || capabilities.contains(Capability.VegetarischVorspeise) || capabilities.contains(Capability.OmnivorVorspeise)
                    Courses.course2name -> return capabilities.contains(Capability.VeganHauptgericht) || capabilities.contains(Capability.VegetarischHauptgericht) || capabilities.contains(Capability.OmnivorHauptgericht)
                    Courses.course3name -> return capabilities.contains(Capability.VeganDessert) || capabilities.contains(Capability.VegetarischDessert) || capabilities.contains(Capability.OmnivorDessert)
                }
            }
        }

        println("CourseCompatibility.isCapabilityCompatible(): Some case was not caught. This should not happen.")
        return false
    }

    private fun isCapabilityCompatible(course: String, capabilities: List<Capability>, teams: List<Team>): Boolean {
        return teams.all { isCapabilityCompatible(course, capabilities, it) }
    }
}