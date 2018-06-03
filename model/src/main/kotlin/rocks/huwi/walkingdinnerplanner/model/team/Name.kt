package rocks.huwi.walkingdinnerplanner.model.team

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException

data class Name(val name: String) {
    val firstname: String = extractFirstname()
    val lastname: String = extractLastname()
    val abbreviatedName: String = buildAbbreviatedName()

    class ConvertName<T> : AbstractBeanField<T>() {
        @Throws(CsvDataTypeMismatchException::class)
        override fun convert(value: String): Any? {
            return Name(value)
        }
    }

    private fun extractFirstname(): String {
        return this.name.split(" ").first()
    }

    private fun extractLastname(): String {
        return this.name.split(" ").last()
    }

    fun buildAbbreviatedName(): String {
        val firstletter = firstname.toCharArray().first()
        return "$firstletter. $lastname"
    }

    override fun toString(): String {
        return name
    }
}