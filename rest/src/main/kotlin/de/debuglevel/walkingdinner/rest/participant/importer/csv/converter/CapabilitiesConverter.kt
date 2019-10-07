package de.debuglevel.walkingdinner.rest.participant.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.rest.participant.CookingCapability
import mu.KotlinLogging

class CapabilitiesConverter<T> : AbstractBeanField<T>() {
    private val logger = KotlinLogging.logger {}

    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
//        println("Converting Capability-Answer to Capability-Enum: $value")

        val answers = value.split(';')

        val capabilities = mapOf<String, CookingCapability>(
            "Ich schaffe es, eine vegane Vorspeise zu machen." to CookingCapability.VeganVorspeise,
            "Ich schaffe es, ein veganes Hauptgericht zu machen." to CookingCapability.VeganHauptgericht,
            "Ich schaffe es, ein veganes Dessert zu machen." to CookingCapability.VeganDessert,
            "Ich schaffe es, eine vegetarische Vorspeise zu machen." to CookingCapability.VegetarischVorspeise,
            "Ich schaffe es, ein vegetarisches Hauptgericht zu machen." to CookingCapability.VegetarischHauptgericht,
            "Ich schaffe es, ein vegetarisches Dessert zu machen." to CookingCapability.VegetarischDessert,
            "Ich schaffe es, eine omnivore Vorspeise zu machen." to CookingCapability.OmnivorVorspeise,
            "Ich schaffe es, ein omnivores Hauptgericht zu machen." to CookingCapability.OmnivorHauptgericht,
            "Ich schaffe es, ein omnivores Dessert zu machen." to CookingCapability.OmnivorDessert
        )

        val teamCapabilities = answers
            //.onEach { println("'${capabilities[it]}' derived from '$it'") }
            .onEach {
                if (!capabilities.contains(it)) {
                    logger.error("Could not map answer '$it' to Capability.")
                }
            }
            .map { capabilities[it] }

//        println("Enum-Capabilities:")
//        teamCapabilities.forEach { println(it) }
//        println("")

        return teamCapabilities
    }

    @Throws(CsvDataTypeMismatchException::class)
    override fun convertToWrite(value: Any?): String {
        TODO("Serialization form object to CSV is not implemented")
    }
}