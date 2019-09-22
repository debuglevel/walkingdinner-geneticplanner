package de.debuglevel.walkingdinner.rest.plan.report.teams.mail

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Singleton
class MailService {
    private val logger = KotlinLogging.logger {}

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    @Throws(MessagingException::class)
    fun buildMimeMessage(
        to: Set<String>,
        from: String,
        subject: String,
        bodyText: String
    ): MimeMessage {
        logger.trace { "Creating mime message..." }

        val properties = Properties()
        val session = Session.getDefaultInstance(properties, null)

        val mimeMessage = MimeMessage(session)

        // set from header
        mimeMessage.setFrom(InternetAddress(from))

        // set to headers
        to.forEach {
            mimeMessage.addRecipient(
                javax.mail.Message.RecipientType.TO,
                InternetAddress(it)
            )
        }

        // set subject
        mimeMessage.subject = subject

        // set text
        mimeMessage.setText(bodyText)

        logger.trace { "Created mime message" }
        return mimeMessage
    }
}