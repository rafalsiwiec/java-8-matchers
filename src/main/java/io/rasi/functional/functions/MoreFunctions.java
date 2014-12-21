package io.rasi.functional.functions;

public final class MoreFunctions {

    public static <T> IndexingFunction<T> withIndex() {
        return new IndexingFunction<>();
    }

    private MoreFunctions() {
    }
}
