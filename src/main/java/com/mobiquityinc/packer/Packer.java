package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.domain.PackagingResolver;
import com.mobiquityinc.packer.domain.PackagingSpecification;
import com.mobiquityinc.packer.parser.InputLineParser;
import com.mobiquityinc.packer.parser.ParenthesesGrouper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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


            Path pathToFile = asPath(filePath);

            if (!Files.exists(pathToFile)) {
                throw new APIException("Input file does not exist: " + filePath);
            }

            List<String> lines = readLines(pathToFile);

            ParenthesesGrouper parenthesesGrouper = new ParenthesesGrouper();
            InputLineParser inputLineParser = new InputLineParser(parenthesesGrouper);


            List<String> inputLines = lines.stream()
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());

            List<PackagingSpecification> specifications = inputLines.stream().map(inputLineParser::parseLine).collect(Collectors.toList());

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

    private static Path asPath(String filePath) {
        Path fromString = Paths.get(filePath);

        if (fromString.isAbsolute()) {
            return fromString;
        }

        try {
            URI uri = Packer.class.getClassLoader().getResource(filePath).toURI();
            return Paths.get(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
