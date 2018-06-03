package rocks.huwi.walkingdinnerplanner.importer

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import rocks.huwi.walkingdinnerplanner.model.team.PhoneNumber

class ConvertPhoneNumber<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return PhoneNumber(value)
    }
}
