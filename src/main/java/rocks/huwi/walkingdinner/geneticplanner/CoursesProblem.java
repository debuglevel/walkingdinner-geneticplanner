package rocks.huwi.walkingdinner.geneticplanner;

import io.jenetics.EnumGene;
import io.jenetics.Genotype;
import io.jenetics.PermutationChromosome;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.*;
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

    private void addLocations(HashMap<Team, List<Location>> teamsLocations, Set<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            for (Team team : meeting.getTeams()) {
                List<Location> teamLocations = teamsLocations.computeIfAbsent(team, x -> new ArrayList<>());
                teamLocations.add(meeting.getCookingTeam().getLocation());
            }
        }
    }

    private double calculateLocationsDistance(List<Location> locations) {
        return locations.get(0).calculateDistance(locations.get(1)) +
                locations.get(1).calculateDistance(locations.get(2));
    }

    private double calculateOverallDistance(Courses courses) {
        Set<Meeting> meetings = courses.toMeetings();

        Map<String, Set<Meeting>> courseMeetings = meetings.stream()
                .collect(Collectors.groupingBy(Meeting::getCourse, Collectors.toSet()));

        Map<Team, List<Location>> teamsLocations = getTeamLocations(courseMeetings);

        return teamsLocations.entrySet().stream()
                .map(kv -> kv.getValue())
                .map(l -> calculateLocationsDistance(l))
                .mapToDouble(d -> d)
                .sum();
    }

    private HashMap<Team, List<Location>> getTeamLocations(Map<String, Set<Meeting>> courseMeetings) {
        HashMap<Team, List<Location>> teamsLocations = new HashMap<>();

        addLocations(teamsLocations, courseMeetings.get(Courses.course1name));
        addLocations(teamsLocations, courseMeetings.get(Courses.course2name));
        addLocations(teamsLocations, courseMeetings.get(Courses.course3name));

        return teamsLocations;
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
