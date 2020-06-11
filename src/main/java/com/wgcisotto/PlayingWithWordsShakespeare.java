package com.wgcisotto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayingWithWordsShakespeare {

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

        ToIntFunction<String> intScore =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        System.out.println("Score of hello: " + intScore.applyAsInt("hello"));

        String bestWord = shakespeare.stream()
                .filter(scrabbleWords::contains)
                .max(Comparator.comparing(score)).get();

        System.out.println("Best work of shakespeare : " + bestWord);

        IntSummaryStatistics intSummaryStatistics =
                shakespeare.stream().parallel()
                .filter(scrabbleWords::contains)
                .mapToInt(intScore)
                .summaryStatistics();

    }

}
