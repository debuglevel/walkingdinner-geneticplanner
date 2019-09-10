package de.debuglevel.walkingdinner.rest.dinner

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging

@Controller("/dinners")
class DinnerController(private val dinnerService: DinnerService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{dinnerId}")
    fun getOne(dinnerId: String): DinnerDTO {
        logger.debug("Called getOne($dinnerId)")
        return dinnerService.get(dinnerId)
    }

    @Get("/")
    fun getList(): Set<DinnerDTO> {
        logger.debug("Called getList()")
        return dinnerService.getAll()
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