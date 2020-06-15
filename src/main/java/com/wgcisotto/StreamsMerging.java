package com.wgcisotto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamsMerging {

    public static void main(String[] args) throws IOException {
        Stream<String> stream1 = Files.lines(Paths.get("files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("files/TomSawyer_04.txt"));

//        System.out.println("Stream 1 : " + stream1.count());
//        System.out.println("Stream 2 : " + stream2.count());
//        System.out.println("Stream 3 : " + stream3.count());
//        System.out.println("Stream 4 : " + stream4.count());

//        Stream<Stream<String>> streamOfStreamsMerged =
//                Stream.of(stream1, stream2, stream3, stream4);

//        System.out.println("# Stream Of Streams Merged : " + streamOfStreamsMerged.count());

//        Stream<String> streamOfLinesMerged =
//                Stream.of(stream1, stream2, stream3, stream4)
//                        .flatMap(Function.identity());
//                        .flatMap(stream -> stream);

//        System.out.println("# Stream Of Lines Merged : " + streamOfLinesMerged.count());

//        Function<String, Stream<String>> lineSplitter = line -> Pattern.compile("").splitAsStream(line);
        Stream<String> streamOfWordsMerged =
                Stream.of(stream1, stream2, stream3, stream4)
                        .flatMap(Function.identity())
                        .flatMap(line -> Pattern.compile(" ").splitAsStream(line)
                        .peek(System.out::println)
                        .map(word -> word.toUpperCase()))
                        .peek(System.out::println)
                        .filter(word -> word.length() == 4)
                        .peek(System.out::println)
                        .distinct();

        System.out.println("# words : " + streamOfWordsMerged.count());



    }

    
}
