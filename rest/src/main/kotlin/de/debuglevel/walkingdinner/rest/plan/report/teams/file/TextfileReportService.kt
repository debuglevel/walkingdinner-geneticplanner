package de.debuglevel.walkingdinner.rest.plan.report.teams.file

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReport
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import mu.KotlinLogging
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path

@Deprecated("This class is not a really good idea for a microservice; creating a zip archive with text files into a stream would be more useful")
class TextfileReportService(private val directory: Path) : TextReportService() {
    private val logger = KotlinLogging.logger {}

    init {
        if (!Files.exists(directory)) {
            logger.debug { "Creating directory $directory" }
            Files.createDirectory(directory)
        }
    }

    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        logger.trace { "Generating text file reports..." }
        val reports = super.generateReports(meetings)
        reports.forEach {
            run {
                val team = it.team
                val file = directory.resolve("${team.cook1.mail} & ${team.cook2.mail}.txt")
                writeReport(file, it.text)
            }
        }
        logger.trace { "Generated text file reports" }
        return reports
    }

    private fun writeReport(path: Path, text: String) {
        logger.debug("Writing report to $path")
        PrintWriter(path.toFile()).use { it.print(text) }
    }
}