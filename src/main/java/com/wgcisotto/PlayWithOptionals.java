package com.wgcisotto;

import com.wgcisotto.utils.NewMath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class PlayWithOptionals {

    public static void main(String[] args) {

        // Creating Optinal
        Optional.empty();
        Optional.of("String"); // return nullPointer with parameter null
        Optional.ofNullable("String"); // can receive null


        List<Double> result = new ArrayList<>();

        // Example of bad pattern
        ThreadLocalRandom
                .current()
                .doubles(10_000)
                .boxed()
//                .parallel() // ERROR
                .forEach( d -> NewMath.inv(d)
                        .ifPresent( inv -> NewMath.sqrt(inv)
                                    .ifPresent(
                                            sqrt -> result.add(sqrt)
                                    ))

                );

        System.out.println("# result = " + result.size());

        // Better way allow parallelism
        Function<Double, Stream<Double>> flatMapper =
                d -> NewMath.inv(d)
                        .flatMap(NewMath::sqrt)
                        .map(Stream::of)
                        .orElseGet(Stream::empty);

        List<Double> collected = ThreadLocalRandom
                .current()
                .doubles(10_000)
                .parallel()
                .map(d -> d*20 - 10)
                .boxed()
                .flatMap(flatMapper)
                .collect(Collectors.toList());

        System.out.println("# result = " + collected.size());



    }

}
