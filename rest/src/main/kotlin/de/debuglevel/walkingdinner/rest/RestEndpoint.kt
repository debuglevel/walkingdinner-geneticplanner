package de.debuglevel.walkingdinner.rest

import de.debuglevel.microservices.utils.apiversion.apiVersion
import de.debuglevel.microservices.utils.spark.configuredPort
import de.debuglevel.microservices.utils.status.status
import de.debuglevel.walkingdinner.rest.participant.ParticipantController
import de.debuglevel.walkingdinner.rest.plan.PlanController
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
            path("/dinners") {
                //get("/", "text/html", DinnerController.getFormHtml())
                //get("/", "application/json", DinnerController.getList())
                //post("/", function = DinnerController.postOne())

                path("/:dinnerId") {
                    //get("/", "application/json", DinnerController.getOne())

                    path("/plans") {
                        get("/", "text/html", PlanController.getAddFormHtml())
                        //get("", "application/json", PlanController.getList())
                        get("/:planId", function = PlanController.getOne())
                        post("/", function = PlanController.postOne())
                    }

                    path("/participants") {
                        get("/", "text/html", ParticipantController.getAddFormHtml())
                        //get("/", "application/json", ParticipantController.getList())
                        //get("/:participantId", function = ParticipantController.getOne())
                        post("/", function = ParticipantController.postOne())
                    }
                }
            }
        }

        logger.info("Starting done...")
    }
}
