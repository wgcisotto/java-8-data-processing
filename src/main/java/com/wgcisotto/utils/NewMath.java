package com.wgcisotto.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class NewMath {


    public static Optional<Double> sqrt(Double d){
        return d > 0d ? Optional.of(Math.sqrt(d)):
                        Optional.empty();
    }

    public static Optional<Double> inv(Double d){
        return d != 0d ? Optional.of(1d/d):
                         Optional.empty();
    }

    public static void main(String[] args) {
        List<Double> list = Arrays.asList(1d, 2d, 3d, 4d);

        list.forEach(d -> NewMath.inv(d)
                    .flatMap(NewMath::sqrt)
                    .map(Stream::of)
                    .orElseGet(Stream::empty)
                );


    }

}
