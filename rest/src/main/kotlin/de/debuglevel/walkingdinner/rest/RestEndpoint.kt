package de.debuglevel.walkingdinner.rest

import de.debuglevel.microservices.utils.apiversion.apiVersion
import de.debuglevel.microservices.utils.logging.buildRequestLog
import de.debuglevel.microservices.utils.logging.buildResponseLog
import de.debuglevel.microservices.utils.spark.configuredPort
import de.debuglevel.microservices.utils.status.status
import de.debuglevel.walkingdinner.rest.dinner.DinnerController
import de.debuglevel.walkingdinner.rest.organisation.OrganisationController
import de.debuglevel.walkingdinner.rest.participant.ParticipantController
import de.debuglevel.walkingdinner.rest.plan.PlanController
import mu.KotlinLogging
import spark.Spark
import spark.Spark.path
import spark.kotlin.after
import spark.kotlin.before
import spark.kotlin.get
import spark.kotlin.post

/**
 * REST endpoint
 */
class RestEndpoint {
    private val logger = KotlinLogging.logger {}

    /**
     * Starts the REST endpoint to enter a listening state
     */
    fun start() {
        logger.info("Starting...")
        configuredPort()

        Spark.staticFiles.externalLocation("upload")

        status(this::class.java)

        apiVersion("1", true)
        {
            path("/organisations") {
                get("/", "text/html", OrganisationController.getListHtml())
                get("/", "application/json", OrganisationController.getListJson())

                path("/:organisationId") {
                    get("/", "text/html", OrganisationController.getOneHtml())
                    get("/", "application/json", OrganisationController.getOneJson())

                    path("/dinners") {
                        //                        get("/", "text/html", DinnerController.getListHtml())
//                        get("/", "application/json", DinnerController.getListJson())

                        path("/:dinnerId") {
                            get("/", "text/html", DinnerController.getOneHtml())
                            get("/", "application/json", DinnerController.getOneJson())

                            path("/plans") {
                                get("/", "text/html", PlanController.getAddFormHtml())
                                get("/:planId", "application/json", function = PlanController.getOne())
                                post("/", function = PlanController.postOne())
                            }

                            path("/participants") {
                                get("/", "text/html", ParticipantController.getAddFormHtml())
                                post("/", function = ParticipantController.postOne())
                            }
                        }
                    }
                }
            }
        }

        // add loggers
        before { logger.debug(buildRequestLog(request)) }
        after { logger.debug(buildResponseLog(request, response)) }
    }
}
