package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.model.dietcompatibility.Capability
import de.debuglevel.walkingdinner.model.team.*
import de.debuglevel.walkingdinner.rest.MultipartUtils.getMultipartCheckbox
import de.debuglevel.walkingdinner.rest.MultipartUtils.getMultipartField
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine

object ParticipantController {
    private val logger = KotlinLogging.logger {}

    fun postOne(): RouteHandler.() -> Any {
        return {
            logger.debug("Got POST request on '/participants' with content-type '${request.contentType()}'")

            if (!request.contentType().startsWith("multipart/form-data")) {
                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
                throw Exception("Content-Type ${request.contentType()} not supported.")
            }

            MultipartUtils.setup(request)
            val address = getMultipartField(request, "address")
            val chef1 = getMultipartField(request, "chef1")
            val chef2 = getMultipartField(request, "chef2")
            val mail1 = getMultipartField(request, "mail1")
            val mail2 = getMultipartField(request, "mail2")
            val phone1 = getMultipartField(request, "phone1")
            val phone2 = getMultipartField(request, "phone2")
            val veganVorspeise = getMultipartCheckbox(request, "VeganVorspeise")
            val veganHauptgericht = getMultipartCheckbox(request, "VeganHauptgericht")
            val veganDessert = getMultipartCheckbox(request, "VeganDessert")
            val vegetarischVorspeise = getMultipartCheckbox(request, "VegetarischVorspeise")
            val vegetarischHauptgericht = getMultipartCheckbox(request, "VegetarischHauptgericht")
            val vegetarischDessert = getMultipartCheckbox(request, "VegetarischDessert")
            val omnivorVorspeise = getMultipartCheckbox(request, "OmnivorVorspeise")
            val omnivorHauptgericht = getMultipartCheckbox(request, "OmnivorHauptgericht")
            val omnivorDessert = getMultipartCheckbox(request, "OmnivorDessert")
            val anmerkungen = getMultipartField(request, "anmerkungen")

            val capabilities = hashMapOf<Capability, Boolean>()
            capabilities[Capability.VeganVorspeise] = veganVorspeise
            capabilities[Capability.VeganHauptgericht] = veganHauptgericht
            capabilities[Capability.VeganDessert] = veganDessert
            capabilities[Capability.VegetarischVorspeise] = vegetarischVorspeise
            capabilities[Capability.VegetarischHauptgericht] = vegetarischHauptgericht
            capabilities[Capability.VegetarischDessert] = vegetarischDessert
            capabilities[Capability.OmnivorVorspeise] = omnivorVorspeise
            capabilities[Capability.OmnivorHauptgericht] = omnivorHauptgericht
            capabilities[Capability.OmnivorDessert] = omnivorDessert

            Team(
                    Cook(
                            Name(chef1),
                            MailAddress(mail1),
                            PhoneNumber(phone1)
                    ),
                    Cook(
                            Name(chef2),
                            MailAddress(mail2),
                            PhoneNumber(phone2)
                    ),
                    address,
                    "UNKNOWN",
                    capabilities.filter { it.value }.map { it.key },
                    null
            )

            ""
        }
    }

    /*
    fun getOne(): RouteHandler.() -> String {
        return {
            val id = request.params(":id").toInt()
            logger.debug("Got GET request on '/participants/$id'")

        }
    }*/

    fun getFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }
}