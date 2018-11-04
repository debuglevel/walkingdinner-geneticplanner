package de.debuglevel.walkingdinner.planner.geneticplanner;

import de.debuglevel.walkingdinner.model.Courses;
import de.debuglevel.walkingdinner.model.Meeting;
import de.debuglevel.walkingdinner.model.location.Location;
import de.debuglevel.walkingdinner.model.team.Team;

import java.util.*;
import java.util.stream.Collectors;

public class CoursesProblemLegacyJavaCode {
    public static void addLocations(HashMap<Team, List<Location>> teamsLocations, Set<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            for (Team team : meeting.getTeams()) {
                List<Location> teamLocations = teamsLocations.computeIfAbsent(team, x -> new ArrayList<>());
                teamLocations.add(meeting.getCookingTeam().getLocation());
            }
        }
    }

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
