package com.enron.indexer;

import java.util.Arrays;

public class EnronCliUtility {
    public static String extractInputArgFrom(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String argName = args[i];

            if (argName.equals("-input")) {
                return args[i + 1];
            }
        }

        return null;
    }
}
