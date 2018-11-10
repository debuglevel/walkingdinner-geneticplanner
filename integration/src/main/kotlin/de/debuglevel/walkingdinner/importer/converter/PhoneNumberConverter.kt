package de.debuglevel.walkingdinner.importer.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.model.team.PhoneNumber

class PhoneNumberConverter<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return PhoneNumber(value)
    }
}
