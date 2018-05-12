package rocks.huwi.walkingdinner.geneticplanner;

import io.jenetics.engine.EvolutionResult;

import java.util.function.Predicate;

/**
 * Limit which first ensures that a result is below a Threshold and then allows a further optimization for a given count of generations.
 *
 * @param <C>
 */
final class FitnessThresholdAndSteadyFitnessLimit<C extends Comparable<? super C>>
        implements Predicate<EvolutionResult<?, C>> {
    private final FitnessThresholdLimit<C> fitnessThresholdLimit;
    private final SteadyFitnessLimit<C> steadyFitnessLimit;

    FitnessThresholdAndSteadyFitnessLimit(final C threshold, final int generations) {
        fitnessThresholdLimit = new FitnessThresholdLimit<>(threshold);
        steadyFitnessLimit = new SteadyFitnessLimit<>(generations);
    }

    @Override
    public boolean test(final EvolutionResult<?, C> result) {
        boolean fitnessThresholdLimitReached = !fitnessThresholdLimit.test(result);
        if (fitnessThresholdLimitReached) {
            boolean steadyFitnessLimitReached = !steadyFitnessLimit.test(result);
            return !steadyFitnessLimitReached;
        }

        return true;
    }
}
