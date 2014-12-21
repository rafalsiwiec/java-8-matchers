package io.rasi.functional.tuples;

public class Tuple2<X, Y> {

    public final X first;
    public final Y second;

    Tuple2(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }
}
