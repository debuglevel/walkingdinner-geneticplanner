package de.debuglevel.walkingdinner.planner.geneticplanner

import de.debuglevel.walkingdinner.model.Courses
import de.debuglevel.walkingdinner.model.Plan
import de.debuglevel.walkingdinner.model.team.Team
import de.debuglevel.walkingdinner.planner.Planner
import io.jenetics.EnumGene
import io.jenetics.Optimize
import io.jenetics.PartiallyMatchedCrossover
import io.jenetics.SwapMutator
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.Limits
import io.jenetics.util.ISeq
import mu.KotlinLogging

import java.util.function.Consumer

class GeneticPlanner(options: GeneticPlannerOptions) : Planner {
    private val logger = KotlinLogging.logger {}

    override fun plan(): Plan {
        val evolutionResult = compute()

        val courses = CoursesProblem(evolutionResult.bestPhenotype.genotype.gene.validAlleles)
            .codec()
            .decode(evolutionResult.bestPhenotype.genotype)
        val meetings = courses.toMeetings()

        val description = """
            best generation: ${evolutionResult.generation}
            total generations: ${evolutionResult.totalGenerations}
            best fitness: ${evolutionResult.bestFitness}
            toString(): $evolutionResult
            meetings: $meetings
        """.trimIndent()

        return Plan(description, meetings)
    }

    private val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?
    private val teams = options.teams

    private val populationsSize = options.populationsSize
    private val fitnessThreshold = options.fitnessThreshold
    private val steadyFitness = options.steadyFitness

    init {
        if (options.evolutionResultConsumer == null) {
            this.evolutionResultConsumer = Consumer { }
        } else {
            this.evolutionResultConsumer = options.evolutionResultConsumer
        }

        logger.debug("Created GeneticPlanner with options: $options")
    }

    private fun compute(): EvolutionResult<EnumGene<Team>, Double> {
        logger.debug("Computing plan...")

        val problem = CoursesProblem(ISeq.of(this.teams))

        // use single thread when optimizing performance
        //        final ExecutorService executor = Executors.newSingleThreadExecutor();

        val engine = Engine
            .builder<Courses, EnumGene<Team>, Double>(problem)
            .populationSize(this.populationsSize)
            .optimize(Optimize.MINIMUM)
            .alterers(
                SwapMutator(0.15),
                PartiallyMatchedCrossover(0.15)
            )
            //                .executor(executor)
            .build()

        val result: EvolutionResult<EnumGene<Team>, Double> = engine.stream()
            .limit(
                Limits.byFitnessThreshold(this.fitnessThreshold).or(
                    Limits.bySteadyFitness(this.steadyFitness)
                )
            )
            .peek(evolutionResultConsumer)
            .collect(EvolutionResult.toBestEvolutionResult<EnumGene<Team>, Double>())

        logger.debug("Computing plan finished...")

        return result
    }
}