package com.mobiquityinc.packer.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class CombinationGenerator<I, S extends SnapshotStack<I, S>> {

    private final List<I> input;
    private final List<S> combinations = new LinkedList<>();
    private final S currentCombination;
    private boolean alreadyRun = false;

    public CombinationGenerator(List<I> input, Supplier<S> constructor) {
        this.input = input;
        this.currentCombination = constructor.get();
    }

    public List<S> combinations() {

        try {

            if (alreadyRun) {
                throw new IllegalStateException("can only generate combinations once");
            }

            combine(0);

            return combinations;

        } finally {
            alreadyRun = true;
        }
    }

    private void combine(int index) {

        for(int i = index; i < input.size(); i++) {
            I current = input.get(i);
            currentCombination.push(current);
            combinations.add(currentCombination.snapshot());
            combine(i+1);
            currentCombination.pop();
        }

    }

}
