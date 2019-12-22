package com.mobiquityinc.packer.domain;

public class Item {

    private final Long indexNumber;
    private final Double weight;
    private final Long cost;

    private Item(Long indexNumber, Double weight, Long cost) {
        this.indexNumber = indexNumber;
        this.weight = weight;
        this.cost = cost;
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
}
