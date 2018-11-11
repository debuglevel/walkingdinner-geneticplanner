package de.debuglevel.walkingdinner.importer

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import de.debuglevel.walkingdinner.importer.converter.CapabilitiesConverter
import de.debuglevel.walkingdinner.importer.converter.MailAddressConverter
import de.debuglevel.walkingdinner.importer.converter.NameConverter
import de.debuglevel.walkingdinner.importer.converter.PhoneNumberConverter
import de.debuglevel.walkingdinner.model.dietcompatibility.Capability
import de.debuglevel.walkingdinner.model.dietcompatibility.Diet
import de.debuglevel.walkingdinner.model.team.*


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
        val team = Team(cook1, cook2, address, diet, capabilities, location)

        return team
    }
}