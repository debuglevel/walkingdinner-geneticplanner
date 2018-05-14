package rocks.huwi.walkingdinner.geneticplanner

import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class Database(val csvFile: String) {
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
        val geolocator = Geolocator("Bamberg, Germany")
        this.teams.parallelStream()
                .forEach { geolocator.initializeTeamLocation(it) }
    }

    private fun importTeamCsv(filename: String) {
        println("Importing teams from CSV file '$csvFile'...")

        var fileReader: BufferedReader? = null
        val csvToBean: CsvToBean<Team>?

        try {
            fileReader = BufferedReader(FileReader(filename))
            csvToBean = CsvToBeanBuilder<Team>(fileReader)
                    .withType(Team::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

            this.teams = csvToBean.parse()

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
                fileReader!!.close()
            } catch (e: IOException) {
                println("Error occurred while closing fileReader/csvParser: $e")
                e.printStackTrace()
            }
        }
    }
}