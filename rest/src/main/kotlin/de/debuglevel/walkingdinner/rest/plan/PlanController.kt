package de.debuglevel.walkingdinner.rest.plan

//import spark.kotlin.RouteHandler
import de.debuglevel.walkingdinner.utils.toUUID
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.nio.file.Path

@Controller("/plans")
class PlanController(private val planService: PlanService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    fun getOne(planId: String): PlanDTO? {
        logger.debug("Called getOne($planId)")
        return planService.getX(planId.toUUID())
    }

    @Get("/")
    fun getList(): Set<PlanDTO> {
        logger.debug("Called getList()")
        return planService.getAll()
    }

    @Post("/")
    fun postOne(planRequest: PlanRequest): PlanResponse {
        logger.debug("Called postOne($planRequest)")
        return postOneX(planRequest)
    }

    private fun postOneX(planRequest: PlanRequest): PlanResponse {
        val surveyfile = takeBase64encodedStringAndCreateAFile(planRequest.surveyfile)

        val planId = planService.startPlannerX(
            surveyfile,
            planRequest.populationsSize,
            planRequest.fitnessThreshold,
            planRequest.steadyFitness,
            planRequest.location
        )

        return PlanResponse(planId)
    }

    private fun takeBase64encodedStringAndCreateAFile(surveyfile: String): Path {
        // TODO: even better: refactor Service so that no intermediate file is needed
        TODO()
    }

//    fun getAddFormHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "plan/add.html.mustache"))
//        }
//    }
}