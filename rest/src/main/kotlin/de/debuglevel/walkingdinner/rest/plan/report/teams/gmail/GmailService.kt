package de.debuglevel.walkingdinner.rest.plan.report.teams.gmail

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.Draft
import com.google.api.services.gmail.model.Message
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailService
import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Path
import javax.inject.Singleton
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

// see: https://developers.google.com/gmail/api/SendEmail.java
@Singleton
class GmailService(
    @Property(name = "app.walkingdinner.data.base-path") private val dataBasepath: Path,
    @Property(name = "app.walkingdinner.reporters.gmail.credentials-folder") private val credentialsFolder: Path,
    @Property(name = "app.walkingdinner.reporters.gmail.client-secret-file") private val clientSecretFile: String,
    private val mailService: MailService
) {
    private val logger = KotlinLogging.logger {}

    private val APPLICATION_NAME = "Walking Dinner"
    private val jacksonFactory = JacksonFactory.getDefaultInstance()

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private val authorizationScopes = listOf(GmailScopes.GMAIL_COMPOSE)

    private val gmail: Gmail

    init {
        logger.debug { "Initializing Gmail service..." }

        // Build a new authorized API client service.
        val netHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credentials = getCredentials(netHttpTransport)

        gmail = Gmail
            .Builder(netHttpTransport, jacksonFactory, credentials)
            .setApplicationName(APPLICATION_NAME)
            .build()

        logger.debug { "Initialized Gmail service..." }
    }

    /**
     * Creates an authorized Credential object.
     * @param netHttpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    @Throws(IOException::class)
    private fun getCredentials(netHttpTransport: NetHttpTransport): Credential {
        logger.debug { "Getting credentials..." }

        val clientSecrets = getClientSecrets()

        // Build flow and trigger user authorization request.
        logger.trace { "Building authorization code flow..." }
        val authorizationCodeFlow = GoogleAuthorizationCodeFlow
            .Builder(netHttpTransport, jacksonFactory, clientSecrets, authorizationScopes)
            .setDataStoreFactory(FileDataStoreFactory(dataBasepath.resolve(credentialsFolder).toFile()))
            .setAccessType("offline")
            .build()

        logger.trace { "Authorizing google user..." }
        val credential = AuthorizationCodeInstalledApp(authorizationCodeFlow, LocalServerReceiver())
            .authorize("user")

        logger.debug { "Got credentials" }
        return credential
    }

    private fun getClientSecrets(): GoogleClientSecrets {
        logger.debug { "Getting client secrets..." }

        // Load client secrets.
        val reader = try {
            logger.trace { "Creating input stream reader for '$clientSecretFile'..." }
            InputStreamReader(Gmail::class.java.getResourceAsStream(clientSecretFile))
        } catch (e: Exception) {
            logger.error(e) { "Could not read Google Gmail client secrets" }
            throw e
        }

        logger.trace { "Loading client secrets..." }
        val clientSecrets = GoogleClientSecrets.load(jacksonFactory, reader)

        logger.debug { "Got client secrets" }
        return clientSecrets
    }

    fun saveDraft(mailaddresses: Set<String>, subject: String, emailContent: String): Draft {
        logger.debug { "Saving draft for '$mailaddresses'..." }

        // "me" indicates that the authorized user should be used
        val authorizedUserValue = "me"
        val mimeMessage = mailService.buildMimeMessage(mailaddresses, authorizedUserValue, subject, emailContent)
        val draft = createDraft(authorizedUserValue, mimeMessage)

        logger.debug { "Saved draft '${draft.id}'" }
        return draft
    }

    /**
     * Create draft email.
     *
     * @param service an authorized Gmail API instance
     * @param userId user's email address. The special value "me"
     * can be used to indicate the authenticated user
     * @param mimeMessage the MimeMessage used as email within the draft
     * @return the created draft
     * @throws MessagingException
     * @throws IOException
     */
    @Throws(MessagingException::class, IOException::class)
    private fun createDraft(
        userId: String,
        mimeMessage: MimeMessage
    ): Draft {
        logger.debug { "Creating draft on Gmail..." }

        val message = buildMessage(mimeMessage)
        var draft = Draft()
        draft.message = message
        // TODO: this could better be a BatchRequest
        logger.trace { "Submitting request to Gmail..." }
        draft = gmail.users().drafts().create(userId, draft).execute()
        logger.trace { "Submitted request to Gmail" }

        logger.debug { "Created draft on Gmail '${draft.id}'" }
        logger.trace { "Created draft on Gmail '${draft.id}': ${draft.toPrettyString()}" }
        return draft
    }

    /**
     * Create a message from an email.
     *
     * @param mimeMessage Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    @Throws(MessagingException::class, IOException::class)
    private fun buildMessage(mimeMessage: MimeMessage): Message {
        logger.trace { "Building message from mime message..." }

        val byteArrayOutputStream = ByteArrayOutputStream()
        mimeMessage.writeTo(byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        val encodedEmail = Base64.encodeBase64URLSafeString(bytes)

        val message = Message()
        message.raw = encodedEmail

        logger.trace { "Builded message from mime message" }
        return message
    }
}