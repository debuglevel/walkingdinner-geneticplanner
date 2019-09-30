package de.debuglevel.walkingdinner.rest.participant.importer.csv

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import de.debuglevel.walkingdinner.rest.participant.*
import de.debuglevel.walkingdinner.rest.participant.importer.csv.converter.CapabilitiesConverter
import de.debuglevel.walkingdinner.rest.participant.importer.csv.converter.MailAddressConverter
import de.debuglevel.walkingdinner.rest.participant.importer.csv.converter.NameConverter
import de.debuglevel.walkingdinner.rest.participant.importer.csv.converter.PhoneNumberConverter
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Capability
import de.debuglevel.walkingdinner.rest.plan.dietcompatibility.Diet

class TeamDTO {
    @CsvCustomBindByName(column = "Koch1", converter = NameConverter::class)
    lateinit var name1: Name

    @CsvCustomBindByName(column = "Koch2", converter = NameConverter::class)
    lateinit var name2: Name

    @CsvCustomBindByName(column = "Telefon1", converter = PhoneNumberConverter::class)
    lateinit var phone1: PhoneNumber

    @CsvCustomBindByName(column = "Telefon2", converter = PhoneNumberConverter::class)
    lateinit var phone2: PhoneNumber

    @CsvCustomBindByName(column = "Mail1", converter = MailAddressConverter::class)
    lateinit var mail1: MailAddress

    @CsvCustomBindByName(column = "Mail2", converter = MailAddressConverter::class)
    lateinit var mail2: MailAddress

    @CsvBindByName(column = "Adresse")
    lateinit var address: String

    @CsvBindByName(column = "Stadt")
    lateinit var city: String

    @CsvBindByName(column = "Diet")
    lateinit var dietString: String

    private val diet: Diet
        get() = Diet.valueOf(dietString)

    @CsvCustomBindByName(column = "Capabilities", converter = CapabilitiesConverter::class)
    val capabilities: List<Capability?> = listOf()

    fun toTeam(): Team {
        val cook1 = Cook(name1, mail1, phone1)
        val cook2 = Cook(name2, mail2, phone2)
        val location = null
        val capabilities = this.capabilities.filterNotNull()
        val team =
            Team(cook1, cook2, address, diet, capabilities, location, null, city)

        return team
    }
}