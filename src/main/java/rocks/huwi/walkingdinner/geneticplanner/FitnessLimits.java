package rocks.huwi.walkingdinner.geneticplanner;

import io.jenetics.engine.EvolutionResult;

import java.util.function.Predicate;

// TODO: remove as soon as https://github.com/jenetics/jenetics/issues/343 is fixed and released
public class FitnessLimits {
    public static <C extends Comparable<? super C>>
    Predicate<EvolutionResult<?, C>> byFitnessThreshold(final C threshold) {
        return new FitnessThresholdLimit<>(threshold);
    }

//    public static <C extends Comparable<? super C>>
//    Predicate<EvolutionResult<?, C>> bySteadyFitness(final int generations) {
//        return new SteadyFitnessLimit<>(generations);
//    }

    public static <C extends Comparable<? super C>>
    Predicate<EvolutionResult<?, C>> byFitnessThresholdAndSteadyFitness(final C threshold, final int generations) {
        return new FitnessThresholdAndSteadyFitnessLimit<>(threshold, generations);
    }
}
