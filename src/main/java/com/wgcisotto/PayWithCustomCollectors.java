package com.wgcisotto;

import com.wgcisotto.model.Actor;
import com.wgcisotto.model.Movie;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PayWithCustomCollectors {

    public static void main(String[] args) throws IOException {

        Set<Movie> movies = new HashSet<>();

        Stream<String> lines = Files.lines(
                Paths.get("files", "movies-mpaa.txt")
        );

        lines.forEach(
                line -> {
                    String[] elements = line.split("/");
                    String title =
                            elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
                    String releaseYear =
                            elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));

                    if (releaseYear.contains(",")) {
                        //with skip movies with a coma in their title
                        return;
                    }

                    Movie movie = new Movie(title, Integer.valueOf(releaseYear));

                    for (int i = 1; i < elements.length; i++) {
                        String[] name = elements[i].split(", ");
                        String lastName = name[0].trim();
                        String firstName = "";
                        if (name.length > 1) {
                            firstName = name[1].trim();
                        }

                        Actor actor = new Actor(lastName, firstName);
                        movie.addActor(actor);
                    }

                    movies.add(movie);

                }
        );

        System.out.println("# movies = " + movies.size());

        // # of actors
        long numberOfActors = movies.stream()
                .flatMap(movie -> movie.getActors().stream()) // Stream<Set<Actors>> >>>> Stream<Stream<Actors>> >>> flatMap >>> Stream<Actors>
//                .collect(Collectors.toSet()) // Too costly build an Set just for count
//                .size(); // Too costly
                .distinct()
                .count();

        System.out.println("# of actors " + numberOfActors);


        // # actors that played in the greatest # movies

        Map.Entry<Actor, Long> mostViewActor = movies.stream()
                .flatMap(movie -> movie.getActors().stream())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet().stream() // Stream<Map.Entry<Actor, Long>>
                .max(
                        Map.Entry.comparingByValue()
                )
                .get();

        System.out.println("Most viewed actor : " + mostViewActor);

        // actor that played in the greatest # of movies during a year
        // Map<Release years, Map<Actor, #number of movies during that year>> // I have to build my own Collector

        // Map<Integer, HashMap<Actor, AtomicLong>> result =
        Map.Entry<Integer, Map.Entry<Actor, AtomicLong>> integerEntryEntry = movies.stream()
                .collect(
                        Collectors.groupingBy(
                                movie -> movie.getReleaseYear(),
                                Collector.of(
                                        () -> new HashMap<Actor, AtomicLong>(), // supplier to build the result
                                        (map, movie) -> {
                                            movie.getActors().forEach(
                                                    actor -> map.computeIfAbsent(actor, a -> new AtomicLong()).incrementAndGet()
                                            );
                                        }, // accumulator
                                        (map1, map2) -> {
                                            map2.entrySet().forEach(
                                                    entry2 -> map1.merge(
                                                            entry2.getKey(), entry2.getValue(),
                                                            (al1, al2) -> {
                                                                al1.addAndGet(al2.get());
                                                                return al1;
                                                            }
                                                    )
                                            );
                                            return map1;
                                        }, // combiner
                                        Collector.Characteristics.IDENTITY_FINISH
                                )
                        )
                ) // Map<Integer, HashMap<Actor, AtomicLong>> result
                .entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue().entrySet().stream().max(
                                        Map.Entry.comparingByValue(Comparator.comparing(l -> l.get()))
                                ).get()

                        )
                )   //// Map<Integer, Map.Entry<Actor, AtomicLong>> result
                .entrySet().stream()
                .max(
                        Map.Entry.comparingByValue(
                                Comparator.comparing(
                                        entry -> entry.getValue().get()
                                )
                        )
                )
                .get();//// Map.Entry<Integer, Map.Entry<Actor, AtomicLong>> result

        System.out.println(integerEntryEntry);

    }
}