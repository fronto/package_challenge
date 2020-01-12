package com.mobiquityinc.packer.test;

import com.mobiquityinc.packer.domain.Item;
import com.mobiquityinc.packer.domain.PackagingSpecification;
import com.mobiquityinc.packer.parser.InputLineParser;
import com.mobiquityinc.packer.parser.ParenthesesGrouper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputLineParsingTest {

    @Test
    void canParseMaximumWeight() {

        ParenthesesGrouper parenthesesGrouper = new ParenthesesGrouper();
        InputLineParser inputLineParser = new InputLineParser(parenthesesGrouper);
        PackagingSpecification result = inputLineParser.parseLine("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9)");

        assertEquals(81, result.getWeightLimit());

    }


    @Test
    void canParseCorrectNumberOfItems() {

        ParenthesesGrouper parenthesesGrouper = new ParenthesesGrouper();
        InputLineParser inputLineParser = new InputLineParser(parenthesesGrouper);
        PackagingSpecification result = inputLineParser.parseLine("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9)");

        assertEquals(5, result.getItemsToPack().size());

    }

    @Test
    void canParseItemFromItemLine() {

        ParenthesesGrouper parenthesesGrouper = new ParenthesesGrouper();
        InputLineParser inputLineParser = new InputLineParser(parenthesesGrouper);
        PackagingSpecification result = inputLineParser.parseLine("81 : (1,53.38,€45) ");

        Item item = result.getItemsToPack().get(0);

        assertEquals(1, item.getIndexNumber());
        assertEquals(53.38d, item.getWeight());
        assertEquals(45, item.getCost());

    }

    @Test
    void canSeparateGroupsOfParentheses() {

        ParenthesesGrouper grouper = new ParenthesesGrouper();
        List<String> groups = grouper.parseItems("(A) (B) (C)");

        assertThat(groups).contains("A", "B", "C");

    }

}
