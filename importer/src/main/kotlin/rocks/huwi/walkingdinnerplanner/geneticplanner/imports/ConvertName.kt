package rocks.huwi.walkingdinnerplanner.geneticplanner.imports

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import rocks.huwi.walkingdinnerplanner.model.team.Name

class ConvertName<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return Name(value)
    }
}
