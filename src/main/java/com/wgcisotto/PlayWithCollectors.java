package com.wgcisotto;

import com.wgcisotto.model.Person;

import java.util.*;
import java.util.stream.Collectors;

public class PlayWithCollectors {

    public static void main(String[] args) {

        Person p1 = new Person("William", 28, "Itap da Serra");
        Person p2 = new Person("Ian", 2, "Sao Paulo");
        Person p3 = new Person("Carol", 38, "Minas");
        Person p4 = new Person("Carol", 38, "Minas");

        List<Person> people = Arrays.asList(p1, p2, p3, p4);

        Optional<Person> collected = people.stream()
                .collect(Collectors.maxBy(Comparator.comparing(p -> p.getAge())));

        System.out.println(collected.get());

        Double average = people.stream()
                .collect(Collectors.averagingDouble(p -> p.getAge()));

        System.out.println(average);

        String names = people.stream()
                .map(p->p.getName())
                .collect(Collectors.joining(", "));

        System.out.println(names);

        Set<String> namesUnique = people.stream()
                .map(p->p.getName())
                .collect(Collectors.toSet());

        System.out.println(namesUnique);

        TreeSet<String> namesTree =
        people.stream().map(p->p.getName())
                .collect(Collectors.toCollection(() -> new TreeSet<>()));

        System.out.println(namesTree);

        Map<Boolean, List<Person>> groupedByAge =
                people.stream()
                .collect(Collectors.partitioningBy(p->p.getAge()>21));

        System.out.println(groupedByAge);

        Map<Integer, List<Person>> peopleByAge =
                people.stream().collect(Collectors.groupingBy(p->p.getAge()));

        System.out.println(peopleByAge);

//        Map<Integer, Long> peopleByAgeCounting = people.stream()
//                .collect(
//                        Collectors.groupingBy(p -> p.getAge()),
//                        Collectors.counting()
//                );


    }
}
