package rocks.huwi.walkingdinner.geneticplanner.report.gmail

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
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.StringReader
import java.util.*
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


object GmailQuickstart {
    private val APPLICATION_NAME = "Gmail API Java Quickstart"
    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private val CREDENTIALS_FOLDER = "credentials" // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private val SCOPES = listOf(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_COMPOSE)
//    private val CLIENT_SECRET_DIR = "client_secret.json"

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
//        val `in` = GmailQuickstart::class.java.getResourceAsStream(CLIENT_SECRET_DIR)

        val reader = StringReader("""
            {"installed":{"client_id":"462124324501-a9ggr7ide7mt05ho2b2kjgt26gvmcnjf.apps.googleusercontent.com","project_id":"walkingdinner-geneticplanner","auth_uri":"https://accounts.google.com/o/oauth2/auth","token_uri":"https://accounts.google.com/o/oauth2/token","auth_provider_x509_cert_url":"https://www.googleapis.com/oauth2/v1/certs","client_secret":"6yDYxLyUHH3CdBXbbvTJ6bVF","redirect_uris":["urn:ietf:wg:oauth:2.0:oob","http://localhost"]}}
            """)

        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader)

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(FileDataStoreFactory(java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build()
        return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
    }

//    @Throws(IOException::class, GeneralSecurityException::class)
//    @JvmStatic
//    fun main(args: Array<String>) {
//        // Build a new authorized API client service.
//        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
//        val service = Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build()
//
//        // Print the labels in the user's account.
//        val user = "me"
//        val listResponse = service.users().labels().list(user).execute()
//        val labels = listResponse.labels
//        if (labels.isEmpty()) {
//            println("No labels found.")
//        } else {
//            println("Labels:")
//            for (label in labels) {
//                System.out.printf("- %s\n", label.name)
//            }
//        }
//
//
//
//
//
////        GmailDraftReporter().createDraft(service, "marc.kohaupt@gmail.com", "hallo welt dies ist 1 mail")
//    }

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
    private fun createEmail(to: Set<String>,
                            from: String,
                            subject: String,
                            bodyText: String): MimeMessage {
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)

        val email = MimeMessage(session)

        email.setFrom(InternetAddress(from))

        to.forEach {
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    InternetAddress(it))
        }

        email.subject = subject
        email.setText(bodyText)
        return email
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    @Throws(MessagingException::class, IOException::class)
    private fun createMessageWithEmail(emailContent: MimeMessage): Message {
        val buffer = ByteArrayOutputStream()
        emailContent.writeTo(buffer)
        val bytes = buffer.toByteArray()
        val encodedEmail = Base64.encodeBase64URLSafeString(bytes)
        val message = Message()
        message.raw = encodedEmail
        return message
    }

    /**
     * Create draft email.
     *
     * @param service an authorized Gmail API instance
     * @param userId user's email address. The special value "me"
     * can be used to indicate the authenticated user
     * @param emailContent the MimeMessage used as email within the draft
     * @return the created draft
     * @throws MessagingException
     * @throws IOException
     */
    @Throws(MessagingException::class, IOException::class)
    private fun createDraft(service: Gmail,
                            userId: String,
                            emailContent: MimeMessage): Draft {
        val message = createMessageWithEmail(emailContent)
        var draft = Draft()
        draft.message = message
        draft = service.users().drafts().create(userId, draft).execute()

        println("Draft id: " + draft.id)
        println(draft.toPrettyString())
        return draft
    }

    private var service: Gmail

    init {
        // Build a new authorized API client service.
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        service = Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build()
    }

    fun createDraft(mailaddresses: Set<String>, subject: String, emailContent: String) {

        val mimemessage = createEmail(mailaddresses, "me", subject, emailContent)
        createDraft(service, "me", mimemessage)


//        // Print the labels in the user's account.
//        val user = "me"
//        val listResponse = service.users().labels().list(user).execute()
//        val labels = listResponse.labels
//        if (labels.isEmpty()) {
//            println("No labels found.")
//        } else {
//            println("Labels:")
//            for (label in labels) {
//                System.out.printf("- %s\n", label.name)
//            }
//        }

    }
}