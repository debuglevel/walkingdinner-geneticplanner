package de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner

import de.debuglevel.walkingdinner.rest.Courses
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.Location
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.CourseCompatibility
import de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner.CoursesProblemLegacyJavaCode.calculateMultipleCookingTeams
import de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner.CoursesProblemLegacyJavaCode.calculateOverallDistance
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

        val decoder: Function<Genotype<EnumGene<Team>>, Courses> = Function { genotype: Genotype<EnumGene<Team>> ->
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

    companion object {
        fun calculateLocationsDistance(locations: List<Location>): Double {
            return locations[0].calculateDistance(locations[1]) + locations[1].calculateDistance(locations[2])
        }

        fun getTeamLocations(courseMeetings: Map<String, Set<Meeting>>): HashMap<Team, MutableList<Location?>> {
            val teamsLocations = HashMap<Team, MutableList<Location?>>()

            addLocations(teamsLocations, courseMeetings[Courses.course1name])
            addLocations(teamsLocations, courseMeetings[Courses.course2name])
            addLocations(teamsLocations, courseMeetings[Courses.course3name])

            return teamsLocations
        }

        fun calculateIncompatibleTeams(meetings: Set<Meeting>): Double {
            return meetings.stream()
                //                .filter(m -> !HardCompatibility.INSTANCE.areCompatibleTeams(m))
                .filter { m -> !CourseCompatibility.areCompatibleTeams(m) }
                .count()
                .toDouble()
        }

        private fun addLocations(teamsLocations: HashMap<Team, MutableList<Location?>>, meetings: Set<Meeting>?) {
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
}
