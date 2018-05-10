import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.ISeq;

public class GeneticDinnerPlanner {
    private Database database;

    public static void main(String[] args) {
        new GeneticDinnerPlanner().run();
    }

    private void initialize() {
        this.database = new Database();
        this.database.initializeTeams();
        this.database.print();
    }

    public void run() {
        this.initialize();
        this.compute();
    }

    private void compute() {
        CoursesProblem problem = new CoursesProblem(ISeq.of(this.database.getTeams()));

        Engine<EnumGene<Team>, Double> engine = Engine
                .builder(problem)
                .optimize(Optimize.MINIMUM)
                .alterers(new SwapMutator<>(0.15),
                        new PartiallyMatchedCrossover<>(0.15))
                .build();

        // Create evolution statistics consumer.
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        final EvolutionResult<EnumGene<Team>, Double> result = engine.stream()
                .limit(Limits.bySteadyFitness(40_000))
//                .limit(Limits.byFitnessThreshold(0.5d))
                .peek(g -> {
                    if (g.getGeneration() % 500 == 0) {
                        System.out.println("Generation: " + g.getGeneration() + "\t| Best Fitness: " + g.getBestFitness());
                    }
                })
                .peek(statistics)
                .collect(EvolutionResult.toBestEvolutionResult());

        ChromosomeHelper.Companion.print(result.getBestPhenotype().getGenotype());

        /*
         * print the results
         */
        System.out.println();
        System.out.println("-----------------");
        System.out.println("Best in Generation: " + result.getGeneration());
        System.out.println("Best with Fitness: " + result.getBestFitness());

        System.out.println(statistics);
    }
}