package de.debuglevel.walkingdinner.rest.dinner.report.teams

import de.debuglevel.walkingdinner.rest.Courses
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.dinner.report.Reporter
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Diet
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

open class TextReporter : Reporter {
    private val model: JtwigModel = JtwigModel.newModel()

    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        val textReports = mutableSetOf<TextReport>()

        val teams = meetings
            .flatMap { it.teams }
            .distinct()

        for (team in teams) {
            val text = generateReport(team, meetings)
            textReports.add(TextReport(team, text))
        }

        return textReports
    }

    private fun generateReport(team: Team, meetings: Set<Meeting>): String {
        model.with("team", team)

        val teamMeetings = meetings
            .filter { it.teams.contains(team) }
            .sortedBy {
                when (it.course) {
                    Courses.course1name -> 1
                    Courses.course2name -> 2
                    Courses.course3name -> 3
                    else -> 0
                }
            }
        model.with("teamMeetings", teamMeetings)

        val cookingMeeting = teamMeetings
            .first { it.getCookingTeam() == team }
        model.with("cookingMeeting", cookingMeeting)

        val cookingDiet = teamMeetings
            .map { it.getCookingTeam().diet }
            .minBy {
                when (it) {
                    Diet.Vegan -> 1
                    Diet.Vegetarisch -> 2
                    Diet.Omnivore -> 3
                    else -> 0
                }
            }
        model.with("cookingDiet", cookingDiet)

        val template = JtwigTemplate.classpathTemplate("templates/mail.twig")

        val text = template.render(model)
        return text
    }
}
