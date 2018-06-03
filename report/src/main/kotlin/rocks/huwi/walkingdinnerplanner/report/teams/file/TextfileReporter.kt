package rocks.huwi.walkingdinnerplanner.report.teams.file

import rocks.huwi.walkingdinnerplanner.model.Meeting
import rocks.huwi.walkingdinnerplanner.report.teams.TextReport
import rocks.huwi.walkingdinnerplanner.report.teams.TextReporter
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path

class TextfileReporter(private val directory: Path) : TextReporter() {
    init {
        if (!Files.exists(directory)) {
            Files.createDirectory(directory)
        }
    }

    private fun writeReport(path: Path, text: String) {
        println("Writing report to $path")
        PrintWriter(path.toFile()).use { it.print(text) }
    }

    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        val reports = super.generateReports(meetings)
        reports.forEach {
            run {
                val team = it.team
                val file = directory.resolve("${team.cook1.mail} & ${team.cook2.mail}.txt")
                writeReport(file, it.text)
            }
        }
        return reports
    }
}