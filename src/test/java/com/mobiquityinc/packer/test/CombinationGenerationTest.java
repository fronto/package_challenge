package com.mobiquityinc.packer.test;

import com.mobiquityinc.packer.util.CombinationGenerator;
import com.mobiquityinc.packer.test.util.DelegatingSnapshotStack;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
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
            CombinationGenerator<Integer, DelegatingSnapshotStack<Integer>> generator = new CombinationGenerator<>(inputs, newStack());
            generator.combinations();
            generator.combinations();

        });


    }

    private Supplier<DelegatingSnapshotStack<Integer>> newStack() {
        return () -> new DelegatingSnapshotStack(new Stack());
    }

    private List<String> combinations(String input) {
        List<Character> inputChars = charList(input);
        CombinationGenerator<Character, DelegatingSnapshotStack<Character>> generator = new CombinationGenerator(inputChars, newStack());
        List<DelegatingSnapshotStack<Character>> combinations = generator.combinations();
        return combinations.stream().map(CombinationGenerationTest::setToString).collect(Collectors.toList());
    }

    private static List<Character> charList(String input) {
        List<Character> list = new LinkedList<>();
        for (char c : input.toCharArray()) {
            list.add(c);
        }
        return list;

    }

    private static String setToString(DelegatingSnapshotStack<Character> charSet) {
        return charSet.delegate().stream().reduce("", (str, chr) -> str + chr, (str1, str2) -> str1 + str2);
    }

}
