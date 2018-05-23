package rocks.huwi.walkingdinner.geneticplanner

import com.opencsv.bean.CsvToBeanBuilder
import rocks.huwi.walkingdinner.geneticplanner.location.DatabasecacheGeolocator
import rocks.huwi.walkingdinner.geneticplanner.team.Team
import rocks.huwi.walkingdinner.geneticplanner.team.TeamDTO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL


class Database(private val csvFile: URL) {
    val teams = mutableListOf<Team>()

    fun print() {
        println("Teams in database:")
        teams.forEach { println(it) }
        println()
    }

    fun initialize() {
        println("Initializing database...")
        initializeTeams()
    }

    private fun initializeTeams() {
        println("Initializing teams...")
        importTeams(csvFile)
        initializeTeamLocations()
    }

    private fun initializeTeamLocations() {
        println("Fetching geo-information for teams...")
        val databasecacheGeolocator = DatabasecacheGeolocator("Bamberg, Germany")
        this.teams.parallelStream()
                .forEach { databasecacheGeolocator.initializeTeamLocation(it) }
    }

    private fun importTeams(url: URL) {
        println("Importing teams from CSV file '$csvFile'...")

        var bufferedReader: BufferedReader? = null

        try {
            bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
            val csvToBean = CsvToBeanBuilder<TeamDTO>(bufferedReader)
                    .withType(TeamDTO::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

            val teamDTOs = csvToBean.parse()

            this.teams.addAll(teamDTOs.map { it.toTeam() })

            println("Imported ${teams.size} teams")
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
    }
}