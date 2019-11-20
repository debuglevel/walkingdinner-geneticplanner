package de.debuglevel.walkingdinner.rest.dietcompatibility

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.PhoneNumber
import de.debuglevel.walkingdinner.rest.participant.*
import java.util.stream.Stream

object TestDataCourseCompatibility {
    fun compatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(CookingCapability.OmnivoreAppetizer),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city"
                    )
                ),
                "Vorspeise"
            )
        ),
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegan,
                        listOf(CookingCapability.VeganMaindish),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegetarian,
                        listOf(),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city"
                    )
                ),
                "Hauptspeise"
            )
        ), MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegetarian,
                        listOf(CookingCapability.VeganDessert),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city"
                    )
                ),
                "Dessert"
            )
        )
    )

    fun incompatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegetarian,
                        listOf(CookingCapability.VegetarianAppetizer),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city"
                    )
                ),
                "Vorspeise"
            )
        ),
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(CookingCapability.OmnivoreMaindish, CookingCapability.VegetarianMaindish),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegetarian,
                        listOf(),
                        null,
                        "city"
                    )
                ),
                "Hauptspeise"
            )
        ), MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(CookingCapability.VegetarianDessert),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city"
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city"
                    )
                ),
                "Dessert"
            )
        )
    )

    data class MeetingData(
        val meeting: Meeting
    )
}