package rocks.huwi.walkingdinner.geneticplanner.team

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException

data class Name(val name: String) {
    class ConvertName<T> : AbstractBeanField<T>() {
        @Throws(CsvDataTypeMismatchException::class)
        override fun convert(value: String): Any? {
            return Name(value)
        }
    }

    override fun toString(): String {
        return name
    }
}