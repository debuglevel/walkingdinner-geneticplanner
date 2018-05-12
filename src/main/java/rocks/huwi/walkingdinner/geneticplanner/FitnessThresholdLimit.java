package rocks.huwi.walkingdinner.geneticplanner;

import io.jenetics.engine.EvolutionResult;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

// TODO: remove as soon as https://github.com/jenetics/jenetics/issues/343 is fixed and released
// TODO: cannot be removed, as the original is package-private but this one is used in FitnessThresholdAndSteadyFitnessLimit.
// TODO: the logic of this class should probably be moved into FitnessThresholdAndSteadyFitnessLimit if the bug above does not get fixed.
final class FitnessThresholdLimit<C extends Comparable<? super C>>
        implements Predicate<EvolutionResult<?, C>> {

    private final C _threshold;
    boolean hasBeenFalse = false;

    FitnessThresholdLimit(final C threshold) {
        _threshold = requireNonNull(threshold);
    }

    @Override
    public boolean test(final EvolutionResult<?, C> result) {
        boolean res = result.getOptimize()
                .compare(_threshold, result.getBestFitness()) >= 0;

        if (res == false && hasBeenFalse == false) {
//            System.out.println("First result which brakes the limit. Holding it back for now due to bug.");
            res = true;
            hasBeenFalse = true;
        } else if (res == false && hasBeenFalse == true) {
//            System.out.println("Second result which brakes the limit; accepting it this time.");
        }

        return res;
    }

}
