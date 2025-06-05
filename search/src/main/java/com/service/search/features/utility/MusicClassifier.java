package com.service.search.features.utility;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

@Component
public class MusicClassifier {
    // Simplified and valid regex patterns
    private static final Pattern ARTIST_PATTERN = Pattern.compile(
            "^(?=.{3,25}$)(?:[A-Z][a-z'-]++(?:\\s++(?:&|and|feat\\.?|x|vs\\.?|with)\\s*+)?)++[A-Z][a-z'*-]++$",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SONG_PATTERN = Pattern.compile(
            "^[\"']?[\\p{L}\\d\\s\\-',.!?&@#$%^*()]+[\"']?(?:\\s*\\(.*\\))?$",
            Pattern.CASE_INSENSITIVE
    );

    private static final Set<String> SONG_INDICATORS = Set.of(
            "remix", "version", "edit", "acoustic", "live", "original", "mix",
            "extended", "radio", "cover", "soundtrack", "reprise"
    );

    private static final Set<String> ARTIST_INDICATORS = Set.of(
            "band", "group", "ensemble", "orchestra", "choir", "trio", "quartet", "featuring"
    );

    private static final Set<String> PREPOSITIONS = Set.of(
            "of", "in", "on", "at", "by", "with", "for", "to", "from", "into",
            "about", "over", "through", "under", "after", "before"
    );

    public enum ContentType { USER, MUSICNAME }

    public ContentType classify(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        String normalized = normalizeInput(input);
        ClassificationScore score = new ClassificationScore();

        // Apply scoring rules
        analyzeCasePattern(normalized, score);
        analyzeWordStructure(normalized, score);
        detectSpecialPatterns(normalized, score);
        detectKeywords(normalized, score);
        analyzeLength(normalized, score);

        // Final determination
        return (score.artistScore >= score.songScore) ? ContentType.USER : ContentType.MUSICNAME;
    }

    private String normalizeInput(String input) {
        return input.trim()
                .replaceAll("\\s{2,}", " ")
                .replaceAll("[“”]", "\"");
    }

    private void analyzeCasePattern(String input, ClassificationScore score) {
        String[] words = input.split("\\s+");
        int titleCaseWords = 0;
        int uppercaseWords = 0;

        for (String word : words) {
            if (!word.isEmpty()) {
                if (Character.isUpperCase(word.charAt(0))) {
                    titleCaseWords++;
                }
                if (word.matches("[A-Z]+")) {
                    uppercaseWords++;
                }
            }
        }

        // Artist name patterns
        if (titleCaseWords >= 2) score.artistScore += 2;
        if (uppercaseWords >= 2) score.artistScore += 1;

        // Song title patterns
        if (titleCaseWords == 1 && words.length > 1) score.songScore += 1;
        if (uppercaseWords == 0) score.songScore += 0.5;
    }

    private void analyzeWordStructure(String input, ClassificationScore score) {
        String[] words = input.split("\\s+");
        int wordCount = words.length;

        // Artist scoring
        if (wordCount == 1) score.artistScore += 2;
        if (wordCount == 2) score.artistScore += 1;

        // Song scoring
        if (wordCount >= 4) score.songScore += 2;
        if (wordCount == 3) score.songScore += 1;

        // Preposition detection
        for (String word : words) {
            String cleanWord = word.replaceAll("^\\W+|\\W+$", "").toLowerCase();
            if (PREPOSITIONS.contains(cleanWord)) {
                score.songScore += 1.5;
                break;
            }
        }
    }

    private void detectSpecialPatterns(String input, ClassificationScore score) {
        // Parentheses/brackets detection
        if ((input.contains("(") && input.contains(")")) ||
                (input.contains("[") && input.contains("]"))) {
            score.songScore += 1.5;
        }

        // Quotes detection
        if ((input.startsWith("\"") && input.endsWith("\"")) ||
                (input.startsWith("'") && input.endsWith("'"))) {
            score.songScore += 1;
        }

        // Pattern matching
        if (ARTIST_PATTERN.matcher(input).matches()) {
            score.artistScore += 3;
        } else if (SONG_PATTERN.matcher(input).matches()) {
            score.songScore += 1;
        }
    }

    private void detectKeywords(String input, ClassificationScore score) {
        String lowerInput = input.toLowerCase();

        // Song indicators
        for (String indicator : SONG_INDICATORS) {
            if (lowerInput.contains(indicator)) {
                score.songScore += 2;
                break;
            }
        }

        // Artist indicators
        for (String indicator : ARTIST_INDICATORS) {
            if (lowerInput.contains(indicator)) {
                score.artistScore += 1.5;
                break;
            }
        }

        // Collaboration terms
        if (lowerInput.contains("feat") || lowerInput.contains("ft") ||
                lowerInput.contains("featuring") || lowerInput.contains("pres") ||
                lowerInput.contains("vs") || lowerInput.contains("x")) {
            score.artistScore += 1.5;
        }
    }

    private void analyzeLength(String input, ClassificationScore score) {
        int len = input.length();
        if (len > 40) {
            score.songScore += 2;
        } else if (len > 25) {
            score.songScore += 1;
        }

        if (len < 15) {
            score.artistScore += 1;
        }
    }

    // Score tracking class
    private static class ClassificationScore {
        double artistScore = 0;
        double songScore = 0;
    }
}
