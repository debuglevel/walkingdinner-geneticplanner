package rocks.huwi.walkingdinnerplanner.model.team

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

data class PhoneNumber(private val number: String) {
    private val formattedNumber: String = try {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val numberProto = phoneUtil.parse(number, "DE")
        phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    } catch (e: NumberParseException) {
        number
    }

    override fun toString(): String {
        return formattedNumber
    }
}