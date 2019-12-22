package com.mobiquityinc.packer.domain;

import java.util.Comparator;

public class CompositeItemCostComparator implements Comparator<CompositeItem> {
    @Override
    public int compare(CompositeItem o1, CompositeItem o2) {
        if(o1.combinedCost() < o2.combinedCost()) {
            return -1;
        }
        if(o1.combinedCost().equals(o2.combinedCost())) {
            return 0;
        }
        return 1;
    }
}
