package de.debuglevel.walkingdinner.rest.dietcompatibility

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.PhoneNumber
import de.debuglevel.walkingdinner.rest.participant.Cook
import de.debuglevel.walkingdinner.rest.participant.Diet
import de.debuglevel.walkingdinner.rest.participant.Name
import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.stream.Stream

object TestDataHardCompatibility {
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
                "course"
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
                "course"
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
                "course"
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
                "course"
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
                "course"
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
                "course"
            )
        )
    )

    data class MeetingData(
        val meeting: Meeting
    )
}