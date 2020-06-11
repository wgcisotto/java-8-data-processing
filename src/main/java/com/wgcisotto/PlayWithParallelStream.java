package com.wgcisotto;

import com.wgcisotto.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayWithParallelStream {

    public static void main(String[] args) {
        // fast computations

        Person p1 = new Person("William", 28, "Itap da Serra");
        Person p2 = new Person("Ian", 2, "Sao Paulo");
        Person p3 = new Person("Carol", 38, "Minas");

        List<Person> people = Arrays.asList(p1, p2, p3);

        //Call parallel Stateless
        people.parallelStream()
                .filter(p -> p.getAge() > 20)
                .forEach(System.out::println);

        //Call parallel Stateless
        people.stream().parallel()
                .filter(p -> p.getAge() > 20)
//                .sorted() I have to pass an Comparator since my class person in not Comparable [In order to guarantee the order in parallel]
                .forEach(System.out::println);

        //Stateful Operation (AVOID PARALLEL) it will kill your performances
        people.stream().parallel()
                .skip(2) // this counter in used in multi thread
                .limit(5)
                .forEach(System.out::println);


        long start;
        long end;

        //Performance
        start = System.currentTimeMillis();
        List<Long> list = new ArrayList<>(10_000_100);
        for(int i = 0; i < 10_000_000; i++){
            list.add(ThreadLocalRandom.current().nextLong());
        }
        end = System.currentTimeMillis();
        printTime(start, end);

        start = System.currentTimeMillis();
        Stream<Long> stream = Stream.generate(()->ThreadLocalRandom.current().nextLong());
        List<Long> listLong = stream.limit(10_000_000).collect(Collectors.toList());
        end = System.currentTimeMillis();
        printTime(start, end);

        start = System.currentTimeMillis();
        Stream<Long> streamOfLongs = ThreadLocalRandom.current().longs(10_000_000).mapToObj(Long::new);
        List<Long> listLong2 = streamOfLongs.collect(Collectors.toList());
        end = System.currentTimeMillis();
        printTime(start, end);



    }

    private static void printTime(long start, long end) {
        float sec = (end - start) / 1000F;
        System.out.println(sec + " seconds");
    }

}
