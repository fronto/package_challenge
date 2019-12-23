package com.mobiquityinc.packer.domain;

import com.mobiquityinc.packer.util.CombinationGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class PackagingResolver {

    public List<Integer> resolvePackaging(PackagingSpecification specification) {

        Double weightLimit = specification.getWeightLimit();
        List<Item> itemsToPack = specification.getItemsToPack();

        CombinationGenerator<Item> generator = new CombinationGenerator<>(itemsToPack);
        List<Set<Item>> combinations = generator.combinations();

        List<CompositeItem> lightEnough = combinations.stream()
                .map(CompositeItem::new)
                .filter(x -> x.combinedWeight() <= weightLimit)
                .collect(Collectors.toList());

        Optional<CompositeItem> max = lightEnough.stream()
                .max(Comparator.comparing(CompositeItem::combinedCost));

        if(max.isPresent()) {

            Integer maximumCost = max.get().combinedCost();
            Optional<CompositeItem> minWeight = lightEnough.stream()
                    .filter(x -> maximumCost.equals(x.combinedCost()))
                    .min(Comparator.comparing(CompositeItem::combinedWeight));

            return minWeight
                    .map(CompositeItem::indexNumbers)
                    .get();

        }

        return Collections.emptyList();

    }



}
