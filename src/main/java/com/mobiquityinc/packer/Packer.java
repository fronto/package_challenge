package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.domain.Item;
import com.mobiquityinc.packer.domain.PackagingResolver;
import com.mobiquityinc.packer.domain.PackagingSpecification;
import com.mobiquityinc.packer.parser.InputLineParser;
import com.mobiquityinc.packer.parser.ParenthesesGrouper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Packer {

    private final PackagingResolver packagingResolver;
    private final InputLineParser inputLineParser;

    private Packer(PackagingResolver packagingResolver, InputLineParser inputLineParser) {
        this.packagingResolver = packagingResolver;
        this.inputLineParser = inputLineParser;
    }

    public static String pack(String filePath) throws APIException {

        ParenthesesGrouper parenthesesGrouper = new ParenthesesGrouper();
        InputLineParser inputLineParser = new InputLineParser(parenthesesGrouper);
        PackagingResolver packagingResolver = new PackagingResolver();
        Packer packer = new Packer(packagingResolver, inputLineParser);

        List<PackagingSpecification> specifications = packer.parseInput(filePath);
        return packer.resolvePackages(specifications);

    }

    private String resolvePackages(List<PackagingSpecification> specifications) throws APIException {

        try {

            StringBuilder resultBuilder = new StringBuilder();
            specifications.forEach(spec -> {

                List<Integer> indexNumbers = packagingResolver.resolvePackaging(spec);

                if (indexNumbers.isEmpty()) {
                    resultBuilder.append("-\n");
                } else {
                    String outputLine = indexNumbers.stream()
                            .map(l -> l.toString())
                            .collect(Collectors.joining(","));
                    resultBuilder.append(outputLine).append("\n");
                }
            });

            return resultBuilder.toString();

        } catch (RuntimeException r) {
            throw new APIException("error resolving packages", r);
        }
    }

    private List<PackagingSpecification> parseInput(String filePath) throws APIException {

        try {

            Path pathToFile = Paths.get(filePath);

            if (!pathToFile.isAbsolute()) {
                throw new APIException("path to input file must be absolute: " + filePath);
            }
            if (!Files.exists(pathToFile)) {
                throw new APIException("Input file does not exist: " + filePath);
            }

            List<String> lines = readLines(pathToFile);

            List<PackagingSpecification> specifications = lines.stream().map(inputLineParser::parseLine).collect(Collectors.toList());

            for (PackagingSpecification specification : specifications) {
                validateParsedInputLine(specification);
            }
            return specifications;

        } catch (RuntimeException r) {
            throw new APIException("error parsing input", r);
        }
    }

    private static void validateParsedInputLine(PackagingSpecification specification) throws APIException {

        final int maxPackageWeight = 100;
        final int maxItemWeight = 100;
        final int maxItemCost = 100;

        if (specification.getWeightLimit() > maxPackageWeight) {
            throw new APIException(String.format("Package weight cannot exceed %s : %s", maxPackageWeight, specification.getWeightLimit()));
        }

        for (Item item : specification.getItemsToPack()) {

            if (item.getWeight() > maxItemWeight) {
                throw new APIException(String.format("Weight of item cannot exceed %s: %s", maxItemWeight, item));
            }

            if (item.getCost() > maxItemCost) {
                throw new APIException(String.format("Cost of item cannot exceed %s : %s", maxItemCost, item));
            }
        }

    }

    private static List<String> readLines(Path pathToFile) {
        try {
            return Files.readAllLines(pathToFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
