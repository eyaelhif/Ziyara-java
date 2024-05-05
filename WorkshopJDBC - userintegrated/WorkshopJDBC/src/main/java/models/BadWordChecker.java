package models;

import java.util.HashSet;

public class BadWordChecker {
    private HashSet<String> badWords;

    public BadWordChecker() {
        // Initialize the set of bad words
        badWords = new HashSet<>();
        badWords.add("flower");
        badWords.add("star");

    }

    public boolean containsBadWord(String text) {
        // Split the text into words
        String[] words = text.split("\\s+");

        // Check each word against the set of bad words
        for (String word : words) {
            if (badWords.contains(word.toLowerCase())) {
                return true; // Found a bad word
            }
        }
        return false; // No bad words found
    }

}