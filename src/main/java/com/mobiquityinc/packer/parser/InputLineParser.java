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
                .map(InputLineParser::parseSingleItem)
                .collect(Collectors.toList());
    }

    private static Item parseSingleItem(String item) {

        String[] fields = item.split(",");

        Integer indexNumber = Integer.valueOf(fields[0]);
        Double weight = Double.valueOf(fields[1]);
        Integer amount = Integer.valueOf(fields[2].substring(1));

        return new Item(indexNumber, weight, amount);

    }


}
