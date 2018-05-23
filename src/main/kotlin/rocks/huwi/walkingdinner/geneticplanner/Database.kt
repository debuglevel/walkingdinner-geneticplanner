package rocks.huwi.walkingdinner.geneticplanner

import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import rocks.huwi.walkingdinner.geneticplanner.location.DatabasecacheGeolocator
import rocks.huwi.walkingdinner.geneticplanner.team.Team
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL


class Database(private val csvFile: URL) {
    lateinit var teams: List<Team>

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
        importTeamCsv(csvFile)
        initializeTeamLocations()
    }

    private fun initializeTeamLocations() {
        println("Fetching geo-information for teams...")
        val databasecacheGeolocator = DatabasecacheGeolocator("Bamberg, Germany")
        this.teams.parallelStream()
                .forEach { databasecacheGeolocator.initializeTeamLocation(it) }
    }

    private fun importTeamCsv(url: URL) {
        println("Importing teams from CSV file '$csvFile'...")

        var bufferedReader: BufferedReader? = null
        val csvToBean: CsvToBean<Team>?

        try {
            bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
            csvToBean = CsvToBeanBuilder<Team>(bufferedReader)
                    .withType(Team::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

            this.teams = csvToBean.parse()

            this.teams.forEach { it.constructComplexProperties() }

            // add team IDs
            for ((index, team) in teams.withIndex()) {
                team.id = index.toLong() + 1
            }

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