package com.mobiquityinc.packer.test;

import com.mobiquityinc.packer.util.CombinationGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CombinationGenerationTest {

    @Test
    void canGenerateAllCombinations() {

        List<String> result = combinations("abcd");

        assertThat(result).containsExactly("a", "ab", "abc", "abcd", "abd", "ac", "acd", "ad",  "b", "bc", "bcd", "bd", "c", "cd", "d");

    }


    @Test
    void canOnlyGenerateCombinationsOncePerGenerator() {

        assertThrows(IllegalStateException.class, () -> {

            List<Integer> inputs = Arrays.asList(1,2,3);
            CombinationGenerator<Integer> generator = new CombinationGenerator<>(inputs);
            generator.combinations();
            generator.combinations();

        });


    }

    private List<String> combinations(String input) {
        List<Character> inputChars = charList(input);
        CombinationGenerator<Character> generator = new CombinationGenerator<>(inputChars);
        List<Set<Character>> combinations = generator.combinations();
        return combinations.stream().map(CombinationGenerationTest::setToString).collect(Collectors.toList());
    }

    private static List<Character> charList(String input) {
        List<Character> list = new LinkedList<>();
        for (char c : input.toCharArray()) {
            list.add(c);
        }
        return list;

    }

    private static String setToString(Set<Character> charSet) {
        return charSet.stream().reduce("", (str, chr) -> str + chr, (str1, str2) -> str1 + str2);
    }

}
