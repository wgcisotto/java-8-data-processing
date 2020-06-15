package com.wgcisotto;

import com.wgcisotto.model.Person;
import com.wgcisotto.spliterator.PeopleSpliterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

public class StreamOfNumbers {

    public static void main(String[] args) throws IOException {

        Spliterator<String> lines = Files.lines(Paths.get("files/people.txt")).spliterator();

        Stream<Person> people = StreamSupport.stream(new PeopleSpliterator(lines), false);

        people.mapToInt(Person::getAge)
                .filter(ages -> ages > 20)
                .average();

        LongStream streamsOfLongs = LongStream.of(1L, 2L, 3L, 4L);
        Stream<Long> streamsOfLongsBoxed = LongStream.of(1L, 2L, 3L, 4L).boxed();
        Stream<Long> streamsOfLongsBoxed2 = LongStream.of(1L, 2L, 3L, 4L).mapToObj(l -> l);

//        List<City> cities = people.map(p -> new City(p.getCity()))
//                .collect(Collectors.toList());
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);

//        int sum = intStream.sum();
//        OptionalInt max = intStream.max();
//        OptionalInt min = intStream.min();
//        OptionalDouble average = intStream.average();
        IntSummaryStatistics intSummaryStatistics = intStream.summaryStatistics();

        System.out.println(intSummaryStatistics);

    }

}
