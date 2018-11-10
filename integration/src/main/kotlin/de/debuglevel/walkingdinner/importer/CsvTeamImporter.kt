package de.debuglevel.walkingdinner.importer

import com.opencsv.bean.CsvToBeanBuilder
import de.debuglevel.walkingdinner.model.team.Team
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class CsvTeamImporter(private val csvFile: URL) : TeamImporter {
    private val logger = KotlinLogging.logger {}

    override fun import(): List<Team> {
        logger.debug("Importing teams from CSV file '$csvFile'...")

        var bufferedReader: BufferedReader? = null

        try {
            bufferedReader = BufferedReader(InputStreamReader(csvFile.openStream()))
            val csvToBean = CsvToBeanBuilder<TeamDTO>(bufferedReader)
                    .withType(TeamDTO::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

            val teamDTOs = csvToBean.parse()

            val teams = teamDTOs.map { it.toTeam() }
            logger.debug("Imported ${teams.size} teams")

            return teams
        } catch (e: Exception) {
            logger.error("Error occurred while reading CSV", e)
            throw e
        } finally {
            try {
                bufferedReader?.close()
            } catch (e: IOException) {
                logger.error("Error occurred while closing bufferedReader/csvParser", e)
                throw e
            }
        }
    }
}