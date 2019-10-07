package de.debuglevel.walkingdinner.planners.dietcompatibility

import de.debuglevel.walkingdinner.planners.Location
import de.debuglevel.walkingdinner.planners.Meeting
import de.debuglevel.walkingdinner.planners.Team
import java.util.*
import java.util.stream.Stream

object TestDataCourseCompatibility {
    fun compatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                "Vorspeise",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(CookingCapability.OmnivorVorspeise),
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
                "Hauptspeise",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegan,
                        listOf(CookingCapability.VeganHauptgericht),
                        Location(0.0, 0.0)
                    ),
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarisch,
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
        ), MeetingData(
            Meeting(
                "Dessert",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarisch,
                        listOf(CookingCapability.VeganDessert),
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
                        Diet.Vegan,
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
                "Vorspeise",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Vegetarisch,
                        listOf(CookingCapability.VegetarischVorspeise),
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
                        Diet.Omnivore,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        ),
        MeetingData(
            Meeting(
                "Hauptspeise",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(CookingCapability.OmnivorHauptgericht, CookingCapability.VegetarischHauptgericht),
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
                        Diet.Vegetarisch,
                        listOf(),
                        Location(0.0, 0.0)
                    )
                )
            )
        ), MeetingData(
            Meeting(
                "Dessert",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(CookingCapability.VegetarischDessert),
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