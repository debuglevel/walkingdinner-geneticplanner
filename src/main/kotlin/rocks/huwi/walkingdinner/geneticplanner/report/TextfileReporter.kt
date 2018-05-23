package rocks.huwi.walkingdinner.geneticplanner.report

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import rocks.huwi.walkingdinner.geneticplanner.Courses
import rocks.huwi.walkingdinner.geneticplanner.Meeting
import java.nio.file.Files
import java.nio.file.Path

class TextfileReporter(private val directory: Path) : Reporter {
    init {
        if (!Files.exists(directory)) {
            Files.createDirectory(directory)
        }
    }

    private fun writeReport(path: Path, model: JtwigModel) {
        println("Writing report to $path")
        val template = JtwigTemplate.classpathTemplate("templates/mail.twig")

        path.toFile().outputStream().use { out ->
            template.render(model, out)
        }
    }

    override fun generateReport(meetings: Set<Meeting>) {
        val model = JtwigModel.newModel()

        val teams = meetings
                .flatMap { it.teams }
                .distinct()

        for (team in teams) {
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
                            "Vegan" -> 1
                            "Vegetarisch" -> 2
                            "Omnivore" -> 3
                            else -> 0
                        }
                    }
            model.with("cookingDiet", cookingDiet)

            val file = directory.resolve("${team.cook1.mail} & ${team.cook2.mail}.txt")
            writeReport(file, model)
        }
    }
}