package com.mobiquityinc.packer.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CompositeItem {

    private final Set<Item> items;

    public CompositeItem(Set<Item> items) {
        this.items = items;
    }

    public Double combinedWeight() {
        return items.stream().map(Item::getWeight).reduce(0d, (x, y) -> x + y);
    }

    public Long combinedCost() {
        return items.stream().map(Item::getCost).reduce(0l, (x, y) -> x + y);
    }

    public List<Long> indexNumbers() {
        return items.stream().map(Item::getIndexNumber).collect(Collectors.toList());
    }

}
