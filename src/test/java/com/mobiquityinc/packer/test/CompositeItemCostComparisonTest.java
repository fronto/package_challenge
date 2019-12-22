package com.mobiquityinc.packer.test;

import com.mobiquityinc.packer.domain.CompositeItem;
import com.mobiquityinc.packer.domain.CompositeItemCostComparator;
import com.mobiquityinc.packer.domain.Item;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositeItemCostComparisonTest {

    @Test
    void selectsCorrectMaximumPrice() {

        Item cheap = new Item(1l, 1d, 1l);
        CompositeItem lowCost = new CompositeItem(Collections.singleton(cheap));

        Item expensive = new Item(2l, 1d, 99l);
        Set<Item> items = new HashSet<>();
        items.add(cheap);
        items.add(expensive);
        CompositeItem highCost = new CompositeItem(items);

        CompositeItem max = Collections.max(asList(highCost, lowCost), new CompositeItemCostComparator());

        assertEquals(highCost, max);

    }

    @Test
    void sortsUnsortedCollectionByPrice() {

        Item cheap = new Item(1l, 1d, 1l);
        CompositeItem lowCost = new CompositeItem(Collections.singleton(cheap));

        Item expensive = new Item(2l, 1d, 99l);
        Set<Item> items = new HashSet<>();
        items.add(cheap);
        items.add(expensive);
        CompositeItem highCost = new CompositeItem(items);

        List<CompositeItem> list = asList(highCost, lowCost);
        Collections.sort(list, new CompositeItemCostComparator());

        assertThat(list).containsExactly(lowCost, highCost);

    }

}
