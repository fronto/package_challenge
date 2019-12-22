package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
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

    private Packer() {

    }

    public static String pack(String filePath) throws APIException {

        try {

            Path pathToFile = Paths.get(filePath);

            if (!pathToFile.isAbsolute()) {
                throw new APIException("path to input file must be absolute: " + filePath);
            }
            if (!Files.exists(pathToFile)) {
                throw new APIException("Input file does not exist: " + filePath);
            }

            List<String> lines = readLines(pathToFile);

            ParenthesesGrouper parenthesesGrouper = new ParenthesesGrouper();
            InputLineParser inputLineParser = new InputLineParser(parenthesesGrouper);

            List<PackagingSpecification> specifications = lines.stream().map(inputLineParser::parseLine).collect(Collectors.toList());

            PackagingResolver packagingResolver = new PackagingResolver();

            StringBuilder resultBuilder = new StringBuilder();
            specifications.forEach(spec -> {

                List<Long> indexNumbers = packagingResolver.resolvePackaging(spec);

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
            throw new APIException("error", r);
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
