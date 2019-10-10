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
                        listOf(CookingCapability.OmnivoreAppetizer),
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
                        listOf(CookingCapability.VeganMaindish),
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
                        Diet.Vegetarian,
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
                        Diet.Vegetarian,
                        listOf(CookingCapability.VegetarianAppetizer),
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
                        listOf(CookingCapability.OmnivoreMaindish, CookingCapability.VegetarianMaindish),
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
        ), MeetingData(
            Meeting(
                "Dessert",
                listOf(
                    Team(
                        UUID.randomUUID(),
                        Diet.Omnivore,
                        listOf(CookingCapability.VegetarianDessert),
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