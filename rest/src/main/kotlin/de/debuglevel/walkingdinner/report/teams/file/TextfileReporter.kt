package de.debuglevel.walkingdinner.report.teams.file

import de.debuglevel.walkingdinner.report.teams.TextReport
import de.debuglevel.walkingdinner.report.teams.TextReporter
import de.debuglevel.walkingdinner.rest.Meeting
import mu.KotlinLogging
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path

class TextfileReporter(private val directory: Path) : TextReporter() {
    private val logger = KotlinLogging.logger {}

    init {
        if (!Files.exists(directory)) {
            Files.createDirectory(directory)
        }
    }

    private fun writeReport(path: Path, text: String) {
        logger.debug("Writing report to $path")
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