package de.debuglevel.walkingdinner.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.rest.participant.PhoneNumber

class PhoneNumberConverter<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return PhoneNumber(value)
    }
}
