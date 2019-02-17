package de.debuglevel.walkingdinner.repository

import de.debuglevel.walkingdinner.model.Plan
import de.debuglevel.walkingdinner.model.dietcompatibility.Capability
import de.debuglevel.walkingdinner.model.dietcompatibility.Diet
import de.debuglevel.walkingdinner.model.dinner.Dinner
import de.debuglevel.walkingdinner.model.location.Location
import de.debuglevel.walkingdinner.model.organisation.Organisation
import de.debuglevel.walkingdinner.model.team.*
import org.litote.kmongo.getCollection
import java.time.LocalDateTime

fun main() {
    val organisation =
        Organisation(
            "Bamberg WalkingDinner Association",
            setOf<Dinner>(
                Dinner(
                    "The very first dinner",
                    LocalDateTime.of(2019, 1, 30, 19, 0),
                    setOf<Team>(
                        Team(
                            Cook(Name("Adam Ansow"), MailAddress("adam@ansow.tld"), PhoneNumber("111111111")),
                            Cook(Name("Berta Berthold"), MailAddress("berta@berthold.tld"), PhoneNumber("2222222")),
                            "Adamsstraße 42",
                            Diet.Omnivore,
                            listOf(Capability.OmnivorVorspeise),
                            Location("Adamsstraße 42", 1.0, 1.0)
                        ),
                        Team(
                            Cook(Name("Charles Chester"), MailAddress("charles@chester.tld"), PhoneNumber("33333333")),
                            Cook(Name("Dagobert Duck"), MailAddress("dagobert@duck.tld"), PhoneNumber("4444444")),
                            "Chesterstreet 23",
                            Diet.Omnivore,
                            listOf(),
                            Location("Chesterstreet 23", 2.0, 2.0)
                        ),
                        Team(
                            Cook(Name("Edgar Eddams"), MailAddress("edgar@eddams.tld"), PhoneNumber("555555")),
                            Cook(
                                Name("Ferdinand Forster"),
                                MailAddress("ferdinand@forster.tld"),
                                PhoneNumber("666666")
                            ),
                            "Eddamsstreet 4711",
                            Diet.Omnivore,
                            listOf(),
                            Location("Eddamsstreet 4711", 3.0, 3.0)
                        )
                    ),
                    setOf<Plan>()
                ),
                Dinner(
                    "The second chance",
                    LocalDateTime.of(2019, 6, 1, 20, 0),
                    setOf<Team>(
                        Team(
                            Cook(Name("Adam Ansow"), MailAddress("adam@ansow.tld"), PhoneNumber("111111111")),
                            Cook(Name("Berta Berthold"), MailAddress("berta@berthold.tld"), PhoneNumber("2222222")),
                            "Adamsstraße 42",
                            Diet.Omnivore,
                            listOf(Capability.OmnivorVorspeise),
                            Location("Adamsstraße 42", 1.0, 1.0)
                        ),
                        Team(
                            Cook(Name("Charles Chester"), MailAddress("charles@chester.tld"), PhoneNumber("33333333")),
                            Cook(Name("Dagobert Duck"), MailAddress("dagobert@duck.tld"), PhoneNumber("4444444")),
                            "Chesterstreet 23",
                            Diet.Omnivore,
                            listOf(),
                            Location("Chesterstreet 23", 2.0, 2.0)
                        )
                    ),
                    setOf<Plan>()
                )
            )
        )

    MongoDatabase.database.getCollection<Organisation>().drop()
    MongoDatabase.database.getCollection<Organisation>().insertOne(organisation)
}