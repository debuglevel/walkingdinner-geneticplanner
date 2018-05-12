package rocks.huwi.walkingdinner.geneticplanner;

import io.jenetics.EnumGene;
import io.jenetics.Genotype;
import io.jenetics.PermutationChromosome;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

class CoursesProblem implements Problem<Courses, EnumGene<Team>, Double> {
    private final ISeq<Team> teams;

    CoursesProblem(final ISeq<Team> teams) {
        this.teams = teams;
    }

    @Override
    public Codec<Courses, EnumGene<Team>> codec() {
        return Codec.of(
                Genotype.of(
                        PermutationChromosome.of(teams),
                        PermutationChromosome.of(teams),
                        PermutationChromosome.of(teams)),
                genotype -> new Courses(
                        genotype.getChromosome(0).toSeq().map(EnumGene::getAllele),
                        genotype.getChromosome(1).toSeq().map(EnumGene::getAllele),
                        genotype.getChromosome(2).toSeq().map(EnumGene::getAllele)
                )
        );
    }

    private double calculateOverallDistance(Team team, Map<String, List<Meeting>> courseMeetings) {
        Location location1 = getCookingLocation(team, courseMeetings, Courses.course1name);
        Location location2 = getCookingLocation(team, courseMeetings, Courses.course2name);
        Location location3 = getCookingLocation(team, courseMeetings, Courses.course3name);

        return location1.calculateDistance(location2) + location2.calculateDistance(location3);
    }

    private Location getCookingLocation(Team team, Map<String, List<Meeting>> courseMeetings, String courseName) {
        return courseMeetings.get(courseName).stream()
                .filter(m -> Arrays.asList(m.getTeams()).contains(team))
                .findFirst()
                .get()
                .getCookingTeam()
                .getLocation();
    }

    private double calculateOverallDistance(Courses courses) {
        Set<Meeting> meetings = courses.toMeetings();
        Map<String, List<Meeting>> courseMeetings = meetings.stream()
                .collect(Collectors.groupingBy(Meeting::getCourse));
        List<Double> distances = this.teams.stream()
                .map(team -> calculateOverallDistance(team, courseMeetings))
                .collect(Collectors.toList());

        return distances.stream()
                .mapToDouble(d -> d)
                .sum();
    }

    private double calculateMultipleCookingTeams(Courses courses) {
        Set<Meeting> meetings = courses.toMeetings();

        Map<Team, Long> teamCookings = meetings.stream()
                .map(Meeting::getCookingTeam)
                .collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        Long countMultipleCookingTeams = teamCookings.entrySet().stream()
                .filter(kv -> kv.getValue() > 1)
                .map(Map.Entry::getValue)
                .mapToLong(value -> value)
                .sum();

        return countMultipleCookingTeams.doubleValue();
    }

    private double calculateIncompatibleTeams(ISeq<Team> teams, String courseName) {
        Set<Meeting> meetings = Team.Companion.toMeetings(teams, courseName);

        return ((Long) meetings.stream()
                .filter(m -> !m.areCompatibleTeams())
                .count())
                .doubleValue();
    }

    @Override
    public Function<Courses, Double> fitness() {
        return courses ->
                (1 * calculateMultipleCookingTeams(courses))
                        + (1 * calculateIncompatibleTeams(courses.getCourse1teams(), Courses.course1name))
                        + (1 * calculateIncompatibleTeams(courses.getCourse2teams(), Courses.course2name))
                        + (1 * calculateIncompatibleTeams(courses.getCourse3teams(), Courses.course3name))
                        + (0.00001 * calculateOverallDistance(courses));
    }
}
