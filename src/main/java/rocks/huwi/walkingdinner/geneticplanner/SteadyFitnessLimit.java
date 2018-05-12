package rocks.huwi.walkingdinner.geneticplanner;

import io.jenetics.Optimize;
import io.jenetics.engine.EvolutionResult;

import java.util.function.Predicate;

final class SteadyFitnessLimit<C extends Comparable<? super C>>
        implements Predicate<EvolutionResult<?, C>> {
    private final int _generations;

    private boolean _proceed = true;
    private int _stable = 0;
    private C _fitness;

    SteadyFitnessLimit(final int generations) {
        if (generations < 1) {
            throw new IllegalArgumentException("Generations < 1: " + generations);
        }
        _generations = generations;
    }

    @Override
    public boolean test(final EvolutionResult<?, C> result) {
        if (!_proceed) return false;

        if (_fitness == null) {
            _fitness = result.getBestFitness();
            _stable = 1;
        } else {
            final Optimize opt = result.getOptimize();
            if (opt.compare(_fitness, result.getBestFitness()) >= 0) {
                _proceed = ++_stable <= _generations;
            } else {
                _fitness = result.getBestFitness();
                _stable = 1;
            }
        }

        return _proceed;
    }
}
