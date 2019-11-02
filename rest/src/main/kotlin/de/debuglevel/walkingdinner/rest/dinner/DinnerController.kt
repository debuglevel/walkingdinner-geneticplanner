package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.common.ElementNotFoundException
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/dinners")
class DinnerController(private val dinnerService: DinnerService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{dinnerId}")
    fun getOne(dinnerId: UUID): HttpResponse<DinnerResponse> {
        logger.debug("Called getOne($dinnerId)")

        return try {
            val dinner = dinnerService.get(dinnerId)
            HttpResponse.ok(DinnerResponse(dinner.id, dinner.name, dinner.begin))
        } catch (e: ElementNotFoundException) {
            HttpResponse.notFound<DinnerResponse>()
        }
    }

    @Get("/")
    fun getList(): Set<DinnerResponse> {
        logger.debug("Called getList()")
        return dinnerService.getAll().map {
            DinnerResponse(it.id, it.name, it.begin)
        }.toSet()
    }

    @Post("/")
    fun postOne(dinnerRequest: DinnerRequest): HttpResponse<DinnerResponse> {
        logger.debug("Called postOne()")

        return try {
            val dinner = Dinner(name = dinnerRequest.name, begin = dinnerRequest.beginDateTime)
            val savedDinner = dinnerService.save(dinner)
            HttpResponse.created(DinnerResponse(savedDinner.id, savedDinner.name, savedDinner.begin))
        } catch (e: ElementNotFoundException) {
            HttpResponse.badRequest<DinnerResponse>()
        }
    }

    //    fun getOneHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "dinner/show.html.mustache"))
//        }
//    }

//    fun getListHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "dinner/list.html.mustache"))
//        }
//    }

    /*
    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }*/
}