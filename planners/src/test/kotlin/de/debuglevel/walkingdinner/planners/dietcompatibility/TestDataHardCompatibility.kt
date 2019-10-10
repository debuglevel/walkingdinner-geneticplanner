package de.debuglevel.walkingdinner.planners.dietcompatibility

import de.debuglevel.walkingdinner.planners.Location
import de.debuglevel.walkingdinner.planners.Meeting
import de.debuglevel.walkingdinner.planners.Team
import java.util.*
import java.util.stream.Stream

object TestDataHardCompatibility {
    fun compatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                "course",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        ),
        MeetingData(
            Meeting(
                "course",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        ),
        MeetingData(
            Meeting(
                "course",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarian,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarian,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarian,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        )
    )

    fun incompatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                "course",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarian,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        ),
        MeetingData(
            Meeting(
                "course",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarian,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        ),
        MeetingData(
            Meeting(
                "course",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        )
    )

    data class MeetingData(
        val meeting: Meeting
    )
}