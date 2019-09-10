package de.debuglevel.walkingdinner.rest.participant

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

data class PhoneNumber(val number: String) {
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
        return formattedNumber
    }
}