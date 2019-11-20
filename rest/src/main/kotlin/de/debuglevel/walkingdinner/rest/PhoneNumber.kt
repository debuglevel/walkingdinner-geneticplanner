package de.debuglevel.walkingdinner.rest

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Transient

@Entity
data class PhoneNumber(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val number: String
) {

    // TODO: would probably not hurt anyone if we just format the number once and save it formatted instead of formatting it every time again
    @delegate:Transient
    @get:Transient
    private val formattedNumber: String by lazy {
        try {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val numberProto = phoneUtil.parse(number, "DE")
            phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: NumberParseException) {
            number
        }
    }

    override fun toString(): String {
        return try {
            formattedNumber
        } catch (e: Exception) {
            number
        }
    }
}