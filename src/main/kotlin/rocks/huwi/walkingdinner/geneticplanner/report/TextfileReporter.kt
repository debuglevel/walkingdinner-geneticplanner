package rocks.huwi.walkingdinner.geneticplanner.report

import rocks.huwi.walkingdinner.geneticplanner.Meeting
import java.nio.file.Files
import java.nio.file.Path

class TextfileReporter(private val directory: Path) : Reporter {
    init {
        if (!Files.exists(directory)) {
            Files.createDirectory(directory)
        }
    }

    fun writeReport(path: Path, message: String) {
        println("Writing report to $path")
        path.toFile().printWriter().use { out ->
            out.print(message)
        }
    }

    override fun generateReport(meetings: Set<Meeting>) {
        val teams = meetings
                .flatMap { it.teams.asList() }
                .distinct()

        for (team in teams) {
            var message = """
                Liebe*r ${team.cook1} und ${team.cook2},

                """

            val teamMeetings = meetings
                    .filter { it.teams.contains(team) }

            val cookingMeeting = teamMeetings
                    .filter { it.getCookingTeam() == team }
                    .first()

            message += """ihr werdet die/das ${cookingMeeting.course} machen dürfen! """

            message += """Während der/dem ${cookingMeeting.course} werden folgende Teams bei dir sein. Bitte koche für den kleinsten gemeinsamen Nenner!"""

            cookingMeeting.teams.forEach {
                message += """
                     * ${it.cook1} (${it.phone1}) und ${it.cook2} (${it.phone2}) (${it.diet})"""
            }

            message += """

                Bei diesen Teams wirst du zu Gast sein dürfen:"""

            teamMeetings.forEach {
                message += """
                 * ${it.course}: ${it.getCookingTeam().cook1} (${it.getCookingTeam().phone1}) und ${it.getCookingTeam().cook2} (${it.getCookingTeam().phone2}) (${it.getCookingTeam().address}) (${it.getCookingTeam().diet})"""
            }

            message += """

                Wir hoffen, dass alles wie geplant funktioniert.

                Liebe Grüße,
                eure Fachschaft Humanwissenschaften"""

            message = message.trimIndent()

            val file = directory.resolve("${team.id} ${team.cook1}.txt")
            writeReport(file, message)
        }
    }
}