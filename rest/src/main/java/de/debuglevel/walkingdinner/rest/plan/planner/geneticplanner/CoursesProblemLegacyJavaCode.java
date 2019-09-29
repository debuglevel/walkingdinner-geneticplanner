package de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner;

import de.debuglevel.walkingdinner.rest.Courses;
import de.debuglevel.walkingdinner.rest.Meeting;
import de.debuglevel.walkingdinner.rest.participant.Team;
import de.debuglevel.walkingdinner.rest.participant.location.Location;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CoursesProblemLegacyJavaCode {
    public static double calculateOverallDistance(Courses courses) {
        Set<Meeting> meetings = courses.toMeetings();

        Map<String, Set<Meeting>> courseMeetings = meetings.stream()
                .collect(
                        Collectors.groupingBy(
                                Meeting::getCourse,
                                Collectors.toSet()));

        Map<Team, List<Location>> teamsLocations = CoursesProblem.Companion.getTeamLocations(courseMeetings);

        return teamsLocations.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(CoursesProblem.Companion::calculateLocationsDistance)
                .mapToDouble(d -> d)
                .sum();
    }

    public static double calculateMultipleCookingTeams(Set<Meeting> meetings) {
        Map<Team, Long> teamCookings = meetings.stream()
                .map(Meeting::getCookingTeam)
                .collect(
                        Collectors.groupingBy(
                                t -> t,
                                Collectors.counting()));

        Long countMultipleCookingTeams = teamCookings.entrySet().stream()
                .filter(kv -> kv.getValue() > 1)
                .map(Map.Entry::getValue)
                .mapToLong(value -> value)
                .sum();

        return countMultipleCookingTeams.doubleValue();
    }
}
