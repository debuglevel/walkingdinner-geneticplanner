package de.debuglevel.walkingdinner.rest.plan

//import spark.kotlin.RouteHandler
import de.debuglevel.walkingdinner.rest.common.Base64String
import de.debuglevel.walkingdinner.rest.common.toUUID
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging

@Controller("/plans")
class PlanController(private val planService: PlanService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    fun getOne(planId: String): PlanDTO {
        logger.debug("Called getOne($planId)")
        return planService.get(planId.toUUID())
    }

    @Get("/")
    fun getList(): Set<PlanDTO> {
        logger.debug("Called getList()")
        return planService.getAll()
    }

    @Post("/")
    fun postOne(planRequest: PlanRequest): PlanResponse {
        logger.debug("Called postOne($planRequest)")

        // TODO: as this is just a CSV, we could just transfer it as a String
        val surveyCsv = Base64String(planRequest.surveyfile).asString
        val planId = planService.startPlannerAsync(
            surveyCsv,
            planRequest.populationsSize,
            planRequest.fitnessThreshold,
            planRequest.steadyFitness,
            planRequest.location
        )

        return PlanResponse(planId)
    }

//    fun getAddFormHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "plan/add.html.mustache"))
//        }
//    }
}