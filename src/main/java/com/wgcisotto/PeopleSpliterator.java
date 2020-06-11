package com.wgcisotto;

import com.wgcisotto.model.Person;

import java.util.Spliterator;
import java.util.function.Consumer;

public class PeopleSpliterator implements Spliterator<Person> {

    private final Spliterator<String> lineSpliterator;
    private String name;
    private int age;
    private String city;

    public PeopleSpliterator(Spliterator<String> lineSpliterator) {
        this.lineSpliterator = lineSpliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Person> action) {
        if (this.lineSpliterator.tryAdvance(line -> this.name = line) &&
            this.lineSpliterator.tryAdvance(line -> this.age = Integer.parseInt(line)) &&
            this.lineSpliterator.tryAdvance(line -> this.city = line)){

            Person p = new Person(name, age, city);
            action.accept(p);

            return true;
        }else {
            return false;
        }

    }

    @Override
    public Spliterator<Person> trySplit() {
        return null; // do not want to go in parallel
    }

    @Override
    public long estimateSize() {
        return this.lineSpliterator.estimateSize() / 3;
    }

    @Override
    public int characteristics() {
       return lineSpliterator.characteristics();
    }
}
