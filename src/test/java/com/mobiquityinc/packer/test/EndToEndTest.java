package com.mobiquityinc.packer.test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EndToEndTest {

    @Test
    void loadInPutFileAndRunPacker() throws APIException {

        Path path = toAbsolutePath("sample_input.txt");
        String result = Packer.pack(path.toString());

        String[] lines = result.split("\n");
        String[] expected = {"4", "-", "2,7", "8,9"};

        for(int i = 0; i < lines.length; i++) {
            assertEquals(expected[i], lines[i]);
        }
    }

    @Test
    void pathMustBeAbsolute() {

        assertThrows(APIException.class, () -> {

            Packer.pack("relative/path");

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
