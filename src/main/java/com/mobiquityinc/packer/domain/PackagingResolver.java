package com.mobiquityinc.packer.domain;

import com.mobiquityinc.packer.util.CombinationGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class PackagingResolver {

    public List<Integer> resolvePackaging(PackagingSpecification specification) {

        Double weightLimit = specification.getWeightLimit();
        List<Item> itemsToPack = specification.getItemsToPack();

        List<Item> preFilteredOnWeight = itemsToPack.stream().filter(item -> item.getWeight() <= weightLimit).collect(Collectors.toList());

        CombinationGenerator<Item, ItemStack> generator = new CombinationGenerator<>(preFilteredOnWeight, () -> new ItemStack());
        List<ItemStack> combinations = generator.combinations();

        List<ItemStack> lightEnough = combinations.stream()
                .filter(x -> x.combinedWeight() <= weightLimit)
                .collect(Collectors.toList());

        if(!lightEnough.isEmpty()) {

            Optional<ItemStack> max = lightEnough.stream()
                .max(Comparator.comparing(ItemStack::combinedCost));

            Integer maximumCost = max.get().combinedCost();

            List<ItemStack> mostExpensive = lightEnough.stream()
                    .filter(x -> maximumCost.equals(x.combinedCost())).collect(Collectors.toList());

            Optional<ItemStack> minWeight = mostExpensive.stream().min(Comparator.comparing(ItemStack::combinedWeight));

            return minWeight
                    .map(ItemStack::indexNumbers)
                    .get();

        }

        return Collections.emptyList();

    }



}
