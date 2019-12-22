package com.mobiquityinc.packer.test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndTest {

    @Test
    void loadInPutFileAndRunPacker() throws APIException {

        String result = Packer.pack("sample_input.txt");

        String[] lines = result.split("\n");
        String[] expected = {"4", "-", "2,7", "8,9"};

        for(int i = 0; i < lines.length; i++) {
            assertEquals(expected[i], lines[i]);
        }
    }

}
