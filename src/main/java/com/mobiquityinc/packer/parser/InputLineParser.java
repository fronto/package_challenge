package com.mobiquityinc.packer.parser;

import com.mobiquityinc.packer.domain.Item;
import com.mobiquityinc.packer.domain.PackagingSpecification;

import java.util.List;
import java.util.stream.Collectors;

public class InputLineParser {

    private final ParenthesesGrouper parenthesesGrouper;

    public InputLineParser(ParenthesesGrouper parenthesesGrouper) {
        this.parenthesesGrouper = parenthesesGrouper;
    }

    public PackagingSpecification parseLine(String line) {

        String[] mapping = line.split(":");

        if (mapping.length != 2) {
            throw new RuntimeException("Invalid input line, should only contain one semi-colon: " + line);
        }

        Double weighLimit = Double.valueOf(mapping[0].trim());
        List<Item> items = parseItems(mapping[1]);

        return new PackagingSpecification(weighLimit, items);
    }

    private List<Item> parseItems(String itemLine) {
        List<String> itemStrings = parenthesesGrouper.parseItems(itemLine);
        return itemStrings.stream()
                .map(Item::parseSingleItem)
                .collect(Collectors.toList());
    }


}
