package com.mobiquityinc.packer.domain;

import java.util.Objects;

public class Item {

    private final Long indexNumber;
    private final Double weight;
    private final Long cost;

    public Item(Long indexNumber, Double weight, Long cost) {
        this.indexNumber = Objects.requireNonNull(indexNumber);
        this.weight = Objects.requireNonNull(weight);
        this.cost = Objects.requireNonNull(cost);
    }

    public static Item parseSingleItem(String item) {

        String[] fields = item.split(",");

        Long indexNumber = Long.valueOf(fields[0]);
        Double weight = Double.valueOf(fields[1]);
        Long amount = Long.valueOf(fields[2].substring(1));

        return new Item(indexNumber, weight, amount);

    }

    public Long getIndexNumber() {
        return indexNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public Long getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return indexNumber.equals(item.indexNumber) &&
                weight.equals(item.weight) &&
                cost.equals(item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNumber, weight, cost);
    }

    @Override
    public String toString() {
        return "Item{" +
                "indexNumber=" + indexNumber +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
