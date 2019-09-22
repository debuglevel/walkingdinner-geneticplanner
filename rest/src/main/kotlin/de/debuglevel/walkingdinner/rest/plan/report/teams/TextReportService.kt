package de.debuglevel.walkingdinner.rest.plan.report.teams

import de.debuglevel.walkingdinner.rest.Courses
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Diet
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import mu.KotlinLogging
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import javax.inject.Singleton

@Singleton
open class TextReportService : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        logger.trace { "Generating text reports..." }

        val textReports = meetings
            .flatMap { it.teams }
            .distinct()
            .map { team ->
                val text = generateReport(team, meetings)
                TextReport(team, text)
            }.toSet()

        logger.trace { "Generated text reports" }
        return textReports
    }

    private fun generateReport(team: Team, meetings: Set<Meeting>): String {
        logger.trace { "Generating text report..." }

        val model = JtwigModel.newModel()
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

        logger.trace { "Generated text report" }
        return text
    }
}
