package com.mobiquityinc.packer.parser;

import java.util.LinkedList;
import java.util.List;

public class ParenthesesGrouper {

    public List<String> parseItems(String items) {

        boolean inside = false;
        List<String> itemStrings = new LinkedList<>();

        String current = null;

        for(Character c : items.toCharArray()) {

            if(c =='(') {
                inside = true;
                current = "";
                continue;
            }

            if(c == ')') {
                inside = false;
                itemStrings.add(current);
                current = null;
                continue;
            }

            if(inside) {
                current += c;
            }


        }

        return itemStrings;
    }

}
