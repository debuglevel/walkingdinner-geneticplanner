package de.debuglevel.walkingdinner.rest.plan

import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanServiceTests {

    @Inject
    lateinit var planService: PlanService

//    @Test
//    fun addX() {
//        val plan = Plan(null, setOf<Meeting>(), "test")
//        planService.addX(plan)
//    }
}