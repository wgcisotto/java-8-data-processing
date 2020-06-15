package com.wgcisotto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreams {

    public static void main(String[] args) {

        // Uncomment this code in order to run the Stream in parallel above with only two Threads
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

        // Single Thread
        Stream.iterate("+", s -> s+"+")
                .limit(6)
                .forEach(System.out::println);

        System.out.println();

        // In parallel different result
        Stream.iterate("+", s -> s+"+")
                .parallel()
                .limit(6)
                .peek(s -> System.out.println(s + " processed in the thread " + Thread.currentThread().getName()))
                .forEach(System.out::println);

        System.out.println();

        List<String> strings = new ArrayList<>();
//        = new CopyOnWriteArrayList<>(); // this is thread safe by do not use in production low performance
        Stream.iterate("+", s -> s + "+")
//                .parallel() //java.lang.ArrayIndexOutOfBoundsException or wrong number(ArrayList not thread Safe)
                .limit(1000)
                .forEach(s -> strings.add(s));

        System.out.println("# " + strings.size());

        // Thre better way
        List<String> collected = Stream.iterate("+", s -> s + "+")
                .parallel()
                .limit(1000)
                .collect(Collectors.toList());

        System.out.println("# " + collected.size());

    }

}
