package io.rasi.functional.functions;

import io.rasi.functional.tuples.Tuple2;
import io.rasi.functional.tuples.Tuples;

import java.util.function.Function;

public class IndexingFunction<T> implements Function<T, Tuple2<Integer, T>> {

    private int index = 0;

    @Override
    public Tuple2<Integer, T> apply(T t) {
        return Tuples.pair(index++, t);
    }

}
