package com.wgcisotto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainUsingCollectorsPatterns {

    public static void main(String[] args) throws IOException {

        Set<String> shakespeare =
                Files.lines(Paths.get("files/words.shakespeare.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        Set<String> scrabbleWords =
                Files.lines(Paths.get("files/ospd.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        System.out.println("# words of Shakespeare : " + shakespeare.size());
        System.out.println("# words of Scrabble : " + scrabbleWords.size());

        final int[] scrabbleENScore = {1, 3, 3, 2, 1, 4,
                2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10,
                1, 1, 1, 1, 4, 4, 8, 4, 10};

        Function<String, Integer> score =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        /*Collectors*/

        Map<Integer, List<String>> histoWordsByScore =
                shakespeare.stream()
                        .filter(scrabbleWords::contains)
                .collect(
                        Collectors.groupingBy(score)
                );

        System.out.println("# histoWordsByScore = " + histoWordsByScore.size() );

        // to Stream a Map
        // Set<Map.Entry<Integer, List<String>>>
        histoWordsByScore.entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(entry -> -entry.getKey())
                )
                .limit(3)
        .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));

        int[] scrabbleENDistribution = {
                9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1,
        };

        Function<String, Map<Integer, Long>> histoWord =
                word -> word.chars().boxed()
                        .collect(
                                Collectors.groupingBy(
                                        letter -> letter,
                                        Collectors.counting()
                                )
                        );

        Function<String, Long> nBlanks =
                word -> histoWord.apply(word) //Map<Integer, Long> Map<Letter, # of letters>
                            .entrySet()
                            .stream() //Map.entry<Integer, Long>
                            .mapToLong(
                                    entry ->
                                            Long.max(
                                            entry.getValue() -
                                                    (long) scrabbleENDistribution[entry.getKey() - 'a'],
                                                    0L)
                            )
                            .sum();

        System.out.println(" # of blanks for whizzing " + nBlanks.apply("whizzing"));

        Function<String, Integer> score2 =
                word -> histoWord.apply(word)
                        .entrySet()
                        .stream() //Map.entry<Integer, Long>
                        .mapToInt(
                                entry ->
                                        scrabbleENScore[entry.getKey() - 'a'] *
                                                Integer.min(entry.getValue().intValue(),
                                                        scrabbleENDistribution[entry.getKey() - 'a'])
                        )
                        .sum();

        System.out.println(" # score for whizzing " + score.apply("whizzing"));
        System.out.println(" # score2 for whizzing " + score2.apply("whizzing"));

       // Map<Integer, List<String>> histoWordsByScore2 =
        shakespeare.stream()
                        .filter(scrabbleWords::contains)
                        .filter(word -> nBlanks.apply(word) <= 2)
                        .collect(
                                Collectors.groupingBy(score2)
                        )
                        .entrySet()
                        .stream()
                        .sorted(
                                Comparator.comparing(entry -> -entry.getKey())
                        )
                        .limit(3)
                        .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));


    }

}
