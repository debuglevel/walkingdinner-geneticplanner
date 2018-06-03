package rocks.huwi.walkingdinner.geneticplanner.imports

import com.opencsv.bean.CsvToBeanBuilder
import rocks.huwi.walkingdinner.geneticplanner.team.Team
import rocks.huwi.walkingdinner.geneticplanner.team.TeamDTO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class CsvTeamImporter(private val csvFile: URL): TeamImporter {
    override fun import(): List<Team> {
        println("Importing teams from CSV file '$csvFile'...")

        var bufferedReader: BufferedReader? = null

        try {
            bufferedReader = BufferedReader(InputStreamReader(csvFile.openStream()))
            val csvToBean = CsvToBeanBuilder<TeamDTO>(bufferedReader)
                    .withType(TeamDTO::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

            val teamDTOs = csvToBean.parse()

            val teams = teamDTOs.map { it.toTeam() }
            println("Imported ${teams.size} teams")
            return teams
        } catch (e: Exception) {
            println("Error occurred while reading CSV: ${e.message}")
            e.printStackTrace()
        } finally {
            try {
                bufferedReader!!.close()
            } catch (e: IOException) {
                println("Error occurred while closing bufferedReader/csvParser: $e")
                e.printStackTrace()
            }
        }

        return listOf()
    }
}