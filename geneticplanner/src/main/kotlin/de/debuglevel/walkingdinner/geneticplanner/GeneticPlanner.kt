package de.debuglevel.walkingdinner.geneticplanner

import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.Courses
import de.debuglevel.walkingdinner.model.team.Team
import io.jenetics.EnumGene
import io.jenetics.Optimize
import io.jenetics.PartiallyMatchedCrossover
import io.jenetics.SwapMutator
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.Limits
import io.jenetics.util.ISeq

import java.util.function.Consumer

class GeneticPlanner(options: GeneticPlannerOptions) {
    private val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?
    //    final private URL csvUrl;
    private val database: Database

    private val populationsSize: Int
    private val fitnessThreshold: Double
    private val steadyFitness: Int

    init {
        this.fitnessThreshold = options.fitnessThreshold
        this.populationsSize = options.populationsSize
        this.steadyFitness = options.steadyFitness
        this.database = options.database

        if (options.evolutionResultConsumer == null) {
            this.evolutionResultConsumer = Consumer { }
        } else {
            this.evolutionResultConsumer = options.evolutionResultConsumer
        }

        println("Created WalkingPlanner with options: $options")
    }

    //    private void initialize() {
    //        System.out.println("Initializing GeneticPlanner...");
    //
    //        this.database = new Database(this.csvUrl);
    //        this.database.initialize();
    ////        this.database.print();
    //    }

    fun run(): EvolutionResult<EnumGene<Team>, Double> {
        println("Running GeneticPlanner...")
        //        this.initialize();
        return this.compute()
    }

    private fun compute(): EvolutionResult<EnumGene<Team>, Double> {
        println("Computing Plan...")

        val problem = CoursesProblem(ISeq.of(this.database.teams))

        // use single thread when optimizing performance
        //        final ExecutorService executor = Executors.newSingleThreadExecutor();

        val engine = Engine
                .builder<Courses, EnumGene<Team>, Double>(problem)
                .populationSize(this.populationsSize)
                .optimize(Optimize.MINIMUM)
                .alterers(SwapMutator(0.15),
                        PartiallyMatchedCrossover(0.15))
                //                .executor(executor)
                .build()

        val result: EvolutionResult<EnumGene<Team>, Double> = engine.stream()
                .limit(Limits.byFitnessThreshold(this.fitnessThreshold).or(
                        Limits.bySteadyFitness(this.steadyFitness)))
                .peek(evolutionResultConsumer)
                .collect(EvolutionResult.toBestEvolutionResult<EnumGene<Team>, Double>())

        println("Computing Plan finished...")

        return result
    }
}