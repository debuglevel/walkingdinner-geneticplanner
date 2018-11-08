package de.debuglevel.walkingdinner.rest

import de.debuglevel.microservices.utils.apiversion.apiVersion
import de.debuglevel.microservices.utils.spark.configuredPort
import de.debuglevel.microservices.utils.status.status
import mu.KotlinLogging
import spark.Spark
import spark.Spark.path
import spark.kotlin.get
import spark.kotlin.post

/**
 * REST endpoint
 */
class RestEndpoint {
    private val logger = KotlinLogging.logger {}

    /**
     * Starts the REST endpoint to enter a listening state
     *
     * @param args parameters to be passed from main() command line
     */
    fun start(args: Array<String>) {
        logger.info("Starting...")
        configuredPort()

        Spark.staticFiles.externalLocation("upload")

        status(this::class.java)

        apiVersion("1", true)
        {
            path("/plans") {
                get("/", "text/html", PlanController.getFormHtml())
                get("/:id", function = PlanController.getOne())
                post("/", function = PlanController.postOne())
            }

            path("/participants") {
                get("/", "text/html", ParticipantController.getFormHtml())
                //get("/:id", function = ParticipantController.getOne())
                post("/", function = ParticipantController.postOne())
            }
        }

        logger.info("Starting done...")
    }
}
