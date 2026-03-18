package com.ncba.countryinfo.util;

/**
 * Utility for string transformations such as sentence case conversion.
 * Sentence case: "kenya" -> "Kenya", "united states" -> "United States"
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static String toSentenceCase(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }
        String trimmed = input.trim();
        return java.util.Arrays.stream(trimmed.split("\\s+"))
                .map(word -> capitalizeWord(word.toLowerCase()))
                .reduce((a, b) -> a + " " + b)
                .orElse(trimmed);
    }

    private static String capitalizeWord(String word) {
        if (word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
