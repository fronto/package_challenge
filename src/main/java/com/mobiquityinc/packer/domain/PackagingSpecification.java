package com.mobiquityinc.packer.domain;

import java.util.List;
import java.util.Objects;

public class PackagingSpecification {

    private final Double weightLimit;
    private final List<Item> itemsToPack;

    public PackagingSpecification(Double weightLimit, List<Item> itemsToPack) {
        this.weightLimit = Objects.requireNonNull(weightLimit);
        this.itemsToPack = Objects.requireNonNull(itemsToPack);
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public List<Item> getItemsToPack() {
        return itemsToPack;
    }
}
