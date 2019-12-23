package com.mobiquityinc.packer.test;

import com.mobiquityinc.packer.domain.Item;
import com.mobiquityinc.packer.domain.PackagingResolver;
import com.mobiquityinc.packer.domain.PackagingSpecification;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PackageResolutionTest {

    @Test
    void resolvePackagingSeeksMaximumMoney() {

        Item itemExpensive =  new Item(1, 10d, 1);
        Item itemCheap =  new Item(2, 10d, 100);

        PackagingSpecification packagingSpecification = new PackagingSpecification(10d, asList(itemExpensive, itemCheap));

        PackagingResolver resolver = new PackagingResolver();

        List<Integer> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).hasSize(1).containsExactly(2);

    }

    @Test
    void resolvePackagingNoChoiceWhenMaxWeightExceeded() {

        Item itemHeavy =  new Item(1, 100d, 1);

        PackagingSpecification packagingSpecification = new PackagingSpecification(10d, asList(itemHeavy));

        PackagingResolver resolver = new PackagingResolver();

        List<Integer> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).isEmpty();

    }

    @Test
    void choosesLightestCombinationWhenCostsAreTheSame() {

        Item item1 =  new Item(1, 3d, 1);
        Item item2 =  new Item(2, 2d, 1);
        Item item3 =  new Item(3, 1d, 1);

        PackagingSpecification packagingSpecification = new PackagingSpecification(5d, asList(item1, item2, item3));

        PackagingResolver resolver = new PackagingResolver();

        List<Integer> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).contains(2, 3);

    }


    @Test
    void choosesBestPriceCombinationWithinWeightConstraints() {

        Item item1 =  new Item(1, 1d, 1);
        Item item2 =  new Item(2, 2d, 2);
        Item item3 =  new Item(3, 3d, 3);

        PackagingSpecification packagingSpecification = new PackagingSpecification(5d, asList(item1, item2, item3));

        PackagingResolver resolver = new PackagingResolver();

        List<Integer> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).contains(2, 2);

    }

    @Test
    void canHandleEmptyInput() {

        PackagingSpecification packagingSpecification = new PackagingSpecification(5d, Collections.emptyList());

        PackagingResolver resolver = new PackagingResolver();

        List<Integer> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).isEmpty();

    }

}
