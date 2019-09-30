package de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner

import de.debuglevel.walkingdinner.rest.Courses
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.Location
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.CourseDietCompatibility
import io.jenetics.EnumGene
import io.jenetics.Genotype
import io.jenetics.PermutationChromosome
import io.jenetics.engine.Codec
import io.jenetics.engine.Problem
import io.jenetics.util.ISeq
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

class CoursesProblem(private val teams: ISeq<Team>) : Problem<Courses, EnumGene<Team>, Double> {
    override fun codec(): Codec<Courses, EnumGene<Team>> {
        val encoding: Genotype<EnumGene<Team>> = Genotype.of(
            PermutationChromosome.of(teams),
            PermutationChromosome.of(teams),
            PermutationChromosome.of(teams)
        )

        val decoder: Function<Genotype<EnumGene<Team>>, Courses> =
            Function { genotype: Genotype<EnumGene<Team>> ->
                Courses(
                    genotype.getChromosome(0).toSeq().map { it.allele },
                    genotype.getChromosome(1).toSeq().map { it.allele },
                    genotype.getChromosome(2).toSeq().map { it.allele }
                )
            }

        val codec: Codec<Courses, EnumGene<Team>> = Codec.of<EnumGene<Team>, Courses>(
            encoding,
            decoder
        )

        return codec
    }

    override fun fitness(): Function<Courses, Double> {
        return Function { courses ->
            val courseMeetings = courses.toCourseMeetings()
            val meetings = courseMeetings.entries.stream()
                .flatMap { cm -> cm.value.stream() }
                .collect(Collectors.toSet<Meeting>())

            val d = (1 * calculateMultipleCookingTeams(meetings)
                    + 1 * calculateIncompatibleTeams(courseMeetings.getValue(Courses.course1name))
                    + 1 * calculateIncompatibleTeams(courseMeetings.getValue(Courses.course2name))
                    + 1 * calculateIncompatibleTeams(courseMeetings.getValue(Courses.course3name))
                    + 0.00001 * calculateOverallDistance(courses))

            d
        }
    }

    companion object {
        private fun calculateLocationsDistance(locations: List<Location?>): Double {
            val location0 = locations[0]!!
            val location1 = locations[1]!!
            val location2 = locations[2]!!

            return location0.calculateDistance(location1) + location1.calculateDistance(location2)
        }

        private fun getTeamLocations(courseMeetings: Map<String, List<Meeting>>): HashMap<Team, MutableList<Location?>> {
            val teamsLocations = HashMap<Team, MutableList<Location?>>()

            addLocations(teamsLocations, courseMeetings[Courses.course1name])
            addLocations(teamsLocations, courseMeetings[Courses.course2name])
            addLocations(teamsLocations, courseMeetings[Courses.course3name])

            return teamsLocations
        }

        fun calculateIncompatibleTeams(meetings: Set<Meeting>): Double {
            return meetings.stream()
                //                .filter(m -> !HardCompatibility.INSTANCE.areCompatibleTeams(m))
                .filter { m -> !CourseDietCompatibility.areCompatibleTeams(m) }
                .count()
                .toDouble()
        }

        fun calculateOverallDistance(courses: Courses): Double {
            val meetings = courses.toMeetings()

            val courseMeetings = meetings.groupBy { it.course }

            val teamsLocations = CoursesProblem.getTeamLocations(courseMeetings)

            return teamsLocations.values
                .map { CoursesProblem.calculateLocationsDistance(it) }
                .sum()
        }

        private fun addLocations(teamsLocations: HashMap<Team, MutableList<Location?>>, meetings: List<Meeting>?) {
            if (meetings != null) {
                for (meeting in meetings) {
                    for (team in meeting.teams) {
                        // get item in HashMap or create empty List if not already available
                        val teamLocations =
                            teamsLocations.computeIfAbsent(team) {
                                mutableListOf()
                            }

                        teamLocations.add(meeting.getCookingTeam().location)
                    }
                }
            }
        }

        private fun calculateMultipleCookingTeams(meetings: Set<Meeting>): Double {
            val teamCookings = meetings.map { it.getCookingTeam() }
                .groupBy { it }
                .mapValues { it.value.count() }

            val countMultipleCookingTeams = teamCookings.entries
                .filter { kv -> kv.value > 1 }
                .map { it.value }
                .sum()

            return countMultipleCookingTeams.toDouble()
        }
    }
}
