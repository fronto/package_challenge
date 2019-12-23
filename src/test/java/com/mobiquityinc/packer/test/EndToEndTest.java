package com.mobiquityinc.packer.test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EndToEndTest {

    @Test
    void loadInputFileAndRunPacker() throws APIException {

        Path path = toAbsolutePath("sample_input.txt");
        String result = Packer.pack(path.toString());

        String[] lines = result.split("\n");
        String[] expected = {"4", "-", "2,7", "8,9"};

        for(int i = 0; i < lines.length; i++) {
            assertEquals(expected[i], lines[i]);
        }
    }


    @Timeout(value = 2l)
    @Test
    void longRow() throws APIException {

        //verify long row with 15 elements is handled in under two seconds
        Path path = toAbsolutePath("long_row.txt");
        Packer.pack(path.toString());

    }

    @Test
    void pathMustBeAbsolute() {

        assertThrows(APIException.class, () -> {

            Packer.pack("relative/path");

        });

    }

    @Test
    void cannotExceedMaxPackageWeight() {

        assertThrows(APIException.class, () -> {
            Path path = toAbsolutePath("maximum_package_weight_exceeded.txt");
            Packer.pack(path.toString());

        });

    }

    @Test
    void cannotExceedMaxItemWeight() {

        assertThrows(APIException.class, () -> {

            Path path = toAbsolutePath("maximum_item_weight_exceeded.txt");
            Packer.pack(path.toString());

        });

    }

    @Test
    void cannotExceedMaxCost() {

        assertThrows(APIException.class, () -> {

            Path path = toAbsolutePath("maximum_cost_exceeded.txt");
            Packer.pack(path.toString());

        });

    }


    private static Path toAbsolutePath(String filePath) {
        Path fromString = Paths.get(filePath);

        if (fromString.isAbsolute()) {
            return fromString;
        }

        try {
            URI uri = Packer.class.getClassLoader().getResource(filePath).toURI();
            return Paths.get(uri).toAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

}
