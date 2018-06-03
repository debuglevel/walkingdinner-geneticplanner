package rocks.huwi.walkingdinnerplanner.importer.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import rocks.huwi.walkingdinnerplanner.model.team.MailAddress

class MailAddressConverter<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return MailAddress(value)
    }
}
