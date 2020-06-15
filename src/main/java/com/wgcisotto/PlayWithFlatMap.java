package com.wgcisotto;

import com.wgcisotto.model.Person;

import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class PlayWithFlatMap {

    public static void main(String[] args) {

        Stream<String> a = Stream.of("a", "b", "c");
        Stream<String> b = Stream.of("d", "e", "f");
        Stream<String> c = Stream.of("g", "h", "i");

        Stream<Stream<String>> stringStream1 = Stream.of(a, b, c);

//        Stream<String> stringStream2 = Stream.of(a, b, c).flatMap(stream -> stream);
        Stream<String> stringStream3 = Stream.of(a, b, c)
                .flatMap(Function.identity());

//        stringStream2.forEach(System.out::println);
        stringStream3.forEach(System.out::println);

    }

}
