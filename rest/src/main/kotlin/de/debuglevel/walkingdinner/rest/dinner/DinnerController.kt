package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.repository.ElementNotFoundException
import de.debuglevel.walkingdinner.utils.toUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging

@Controller("/dinners")
class DinnerController(private val dinnerService: DinnerService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{dinnerId}")
    fun getOne(dinnerId: String): HttpResponse<DinnerResponse> {
        logger.debug("Called getOne($dinnerId)")

        return try {
            val dinner = dinnerService.get(dinnerId.toUUID())
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
            val dinner = Dinner(dinnerRequest.name, dinnerRequest.begin)
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