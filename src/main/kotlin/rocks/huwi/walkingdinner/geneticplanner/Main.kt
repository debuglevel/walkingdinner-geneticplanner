package rocks.huwi.walkingdinner.geneticplanner

fun main(args: Array<String>) {
    println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")
    GeneticPlanner().run()
}