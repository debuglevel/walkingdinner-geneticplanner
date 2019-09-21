package de.debuglevel.walkingdinner.rest.plan

//import spark.kotlin.RouteHandler
import de.debuglevel.walkingdinner.rest.common.toUUID
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
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

//    fun getAddFormHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "plan/add.html.mustache"))
//        }
//    }
}