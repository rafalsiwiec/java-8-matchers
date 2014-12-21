package io.rasi.functional.tuples;

public final class Tuples {

    public static <X, Y> Tuple2<X, Y> pair(X first, Y second) {
        return new Tuple2<>(first, second);
    }

    private Tuples() {
    }
}
