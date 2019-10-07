package de.debuglevel.walkingdinner.planners.planner.geneticplanner

import de.debuglevel.walkingdinner.planners.Courses
import de.debuglevel.walkingdinner.planners.Team
import de.debuglevel.walkingdinner.planners.plan.Plan
import de.debuglevel.walkingdinner.planners.planner.Planner
import io.jenetics.EnumGene
import io.jenetics.Optimize
import io.jenetics.PartiallyMatchedCrossover
import io.jenetics.SwapMutator
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.Limits
import io.jenetics.util.ISeq
import mu.KotlinLogging
import java.util.*
import java.util.function.Consumer

class GeneticPlanner(private val options: GeneticPlannerOptions) :
    Planner {
    private val logger = KotlinLogging.logger {}

    private val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?

    init {
        logger.debug { "Initializing GeneticPlanner with options: $options..." }
        if (options.evolutionResultConsumer == null) {
            this.evolutionResultConsumer = Consumer { }
        } else {
            this.evolutionResultConsumer = options.evolutionResultConsumer
        }

        logger.debug { "Initialized GeneticPlanner" }
    }

    override fun plan(): Plan {
        logger.debug("Planning...")

        val evolutionResult = compute()

        val courses = CoursesProblem(evolutionResult.bestPhenotype.genotype.gene.validAlleles)
            .codec()
            .decode(evolutionResult.bestPhenotype.genotype)
        val meetings = courses.toMeetings()

        val additionalInformation = """
            best generation: ${evolutionResult.generation}
            total generations: ${evolutionResult.totalGenerations}
            best fitness: ${evolutionResult.bestFitness}
            meetings: $meetings
        """.trimIndent()

        val plan = Plan(UUID.randomUUID(), meetings, additionalInformation)
        logger.debug("Planned: $plan")
        return plan
    }

    private fun compute(): EvolutionResult<EnumGene<Team>, Double> {
        logger.debug("Computing plan...")

        val problem = CoursesProblem(ISeq.of(options.teams))

        // use single thread when optimizing performance
        //        final ExecutorService executor = Executors.newSingleThreadExecutor();

        val engine = Engine
            .builder<Courses, EnumGene<Team>, Double>(problem)
            .populationSize(options.populationsSize)
            .optimize(Optimize.MINIMUM)
            .alterers(
                SwapMutator(0.15),
                PartiallyMatchedCrossover(0.15)
            )
            //                .executor(executor)
            .build()

        val evolutionResult: EvolutionResult<EnumGene<Team>, Double> = engine.stream()
            .limit(
                Limits.byFitnessThreshold(options.fitnessThreshold).or(
                    Limits.bySteadyFitness(options.steadyFitness)
                )
            )
            .peek(evolutionResultConsumer)
            .collect(EvolutionResult.toBestEvolutionResult<EnumGene<Team>, Double>())

        logger.debug("Computed plan: $evolutionResult")
        return evolutionResult
    }
}