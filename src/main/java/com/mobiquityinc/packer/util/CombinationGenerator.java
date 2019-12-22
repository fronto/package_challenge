package com.mobiquityinc.packer.util;

import java.util.*;

public class CombinationGenerator<T> {

    private final List<T> input;
    private final List<Set<T>> combinations = new LinkedList<>();
    private final List<T> currentCombination = new LinkedList<>();
    private boolean alreadyRun = false;

    public CombinationGenerator(List<T> input) {
        this.input = input;
    }

    public List<Set<T>> combinations() {

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
            T current = input.get(i);
            currentCombination.add(current);
            Set<T> snapshot = new HashSet<>();
            snapshot.addAll(currentCombination);
            combinations.add(snapshot);
            combine(i+1);
            currentCombination.remove(current);
        }

    }

}
