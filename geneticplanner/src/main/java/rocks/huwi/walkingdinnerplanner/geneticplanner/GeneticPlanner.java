package rocks.huwi.walkingdinnerplanner.geneticplanner;

import io.jenetics.EnumGene;
import io.jenetics.Optimize;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.SwapMutator;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.util.ISeq;
import rocks.huwi.walkingdinnerplanner.geneticplanner.imports.Database;
import rocks.huwi.walkingdinnerplanner.geneticplanner.team.Team;

import java.net.URL;
import java.util.function.Consumer;

public class GeneticPlanner {
    final private Consumer<EvolutionResult<EnumGene<Team>, Double>> evolutionResultConsumer;
    final private URL csvUrl;
    private Database database;

    public GeneticPlanner(URL csvUrl, Consumer<EvolutionResult<EnumGene<Team>, Double>> evolutionResultConsumer) {
        this.csvUrl = csvUrl;

        if (evolutionResultConsumer == null) {
            this.evolutionResultConsumer = g -> {
            };
        } else {
            this.evolutionResultConsumer = evolutionResultConsumer;
        }
    }

    private void initialize() {
        System.out.println("Initializing GeneticPlanner...");

        this.database = new Database(this.csvUrl);
        this.database.initialize();
//        this.database.print();
    }

    public EvolutionResult<EnumGene<Team>, Double> run() {
        System.out.println("Running GeneticPlanner...");
        this.initialize();
        return this.compute();
    }

    private EvolutionResult<EnumGene<Team>, Double> compute() {
        System.out.println("Computing Plan...");

        CoursesProblem problem = new CoursesProblem(ISeq.of(this.database.getTeams()));

        // use single thread when optimizing performance
//        final ExecutorService executor = Executors.newSingleThreadExecutor();

        Engine<EnumGene<Team>, Double> engine = Engine
                .builder(problem)
                .populationSize(200)
                .optimize(Optimize.MINIMUM)
                .alterers(new SwapMutator<>(0.15),
                        new PartiallyMatchedCrossover<>(0.15))
//                .executor(executor)
                .build();

        final EvolutionResult<EnumGene<Team>, Double> result = engine.stream()
                .limit(Limits.byFitnessThreshold(0.001d).or(
                        Limits.bySteadyFitness(40_000)))
                .peek(evolutionResultConsumer)
                .collect(EvolutionResult.toBestEvolutionResult());

        System.out.println("Computing Plan finished...");

        return result;
    }
}