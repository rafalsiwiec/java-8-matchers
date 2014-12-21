package io.rasi.matchers;

import io.rasi.functional.functions.MoreFunctions;
import io.rasi.functional.tuples.Tuple2;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class MatchingSomeItems<T> extends TypeSafeMatcher<Iterable<T>> {

    private final List<Matcher<? extends T>> matchers;

    public MatchingSomeItems(List<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(Iterable<T> iterable) {
        return matchLoop(iterable, new HashSet<>(), matchers.iterator());
    }

    private boolean matchLoop(Iterable<T> iterable, Set<Integer> alreadyMatched, Iterator<Matcher<? extends T>> matcherIt) {
        if (!matcherIt.hasNext()) return true;

        Matcher<? extends T> matcher = matcherIt.next();

        return nonMatchedStream(iterable, alreadyMatched)
                .filter(indexedItem -> matcher.matches(indexedItem.second))
                .findFirst()
                .map(matchingIndexedItem -> {
                    Set<Integer> updated = new HashSet<>(alreadyMatched);
                    updated.add(matchingIndexedItem.first);
                    return matchLoop(iterable, updated, matcherIt);
                })
                .orElse(false);
    }

    private Stream<Tuple2<Integer, T>> nonMatchedStream(Iterable<T> iterable, Set<Integer> matched) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(MoreFunctions.withIndex())
                .filter(i -> !matched.contains(i.first));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Should contain items matching ").appendList("[\n", ",\n", "]", matchers);
    }
}
