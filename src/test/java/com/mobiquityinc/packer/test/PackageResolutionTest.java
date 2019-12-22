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

        Item itemExpensive =  new Item(1l, 10d, 1l);
        Item itemCheap =  new Item(2l, 10d, 100l);

        PackagingSpecification packagingSpecification = new PackagingSpecification(10d, asList(itemExpensive, itemCheap));

        PackagingResolver resolver = new PackagingResolver();

        List<Long> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).hasSize(1).containsExactly(2l);

    }

    @Test
    void resolvePackagingNoChoiceWhenMaxWeightExceeded() {

        Item itemHeavy =  new Item(1l, 100d, 1l);

        PackagingSpecification packagingSpecification = new PackagingSpecification(10d, asList(itemHeavy));

        PackagingResolver resolver = new PackagingResolver();

        List<Long> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).isEmpty();


    }


    @Test
    void choosesBestPriceCombinationWithinWeightConstraints() {

        Item item1 =  new Item(1l, 1d, 1l);
        Item item2 =  new Item(2l, 2d, 2l);
        Item item3 =  new Item(3l, 3d, 3l);

        PackagingSpecification packagingSpecification = new PackagingSpecification(5d, asList(item1, item2, item3));

        PackagingResolver resolver = new PackagingResolver();

        List<Long> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).contains(2l, 2l);

    }

    @Test
    void canHandleEmptyInput() {

        PackagingSpecification packagingSpecification = new PackagingSpecification(5d, Collections.emptyList());

        PackagingResolver resolver = new PackagingResolver();

        List<Long> result = resolver.resolvePackaging(packagingSpecification);

        assertThat(result).isEmpty();

    }

}
