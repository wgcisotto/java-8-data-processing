package com.wgcisotto;

import com.wgcisotto.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class ParallelControlNumbersOfThreads {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Person p1 = new Person("William", 28, "Itap da Serra");
        Person p2 = new Person("Ian", 2, "Sao Paulo");
        Person p3 = new Person("Carol", 38, "Minas");

        List<Person> people = Arrays.asList(p1, p2, p3);

        // this way I can set to use only 2 threads when parallel not all available CPU
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

        // tuning Parallelism
        ForkJoinPool fjp = new ForkJoinPool(2);
        OptionalDouble optionalDouble = fjp.submit(() ->
                people.stream().parallel()     //
                        .mapToInt(Person::getAge) // this is an implementation
                        .filter(age -> age > 20)   // of Callable<Integer>
                        .average()                  //
        ).get();// from Future Object

        System.out.println(optionalDouble.getAsDouble());

    }

}
