package com.mobiquityinc.packer.domain;

import java.util.Objects;

public class Item {

    private final Integer indexNumber;
    private final Double weight;
    private final Integer cost;

    public Item(Integer indexNumber, Double weight, Integer cost) {
        this.indexNumber = Objects.requireNonNull(indexNumber);
        this.weight = Objects.requireNonNull(weight);
        this.cost = Objects.requireNonNull(cost);
    }

    public static Item parseSingleItem(String item) {

        String[] fields = item.split(",");

        Integer indexNumber = Integer.valueOf(fields[0]);
        Double weight = Double.valueOf(fields[1]);
        Integer amount = Integer.valueOf(fields[2].substring(1));

        return new Item(indexNumber, weight, amount);

    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getCost() {
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
