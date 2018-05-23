package rocks.huwi.walkingdinner.geneticplanner.team

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException

data class MailAddress(val mail: String) {
    class ConvertMailAddress<T> : AbstractBeanField<T>() {
        @Throws(CsvDataTypeMismatchException::class)
        override fun convert(value: String): Any? {
            return MailAddress(value)
        }
    }
}