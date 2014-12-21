package io.rasi.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class MatchingAllItemsInAnyOrder<T> extends TypeSafeMatcher<Iterable<T>> {

    private final List<Matcher<? extends T>> matchers;

    public MatchingAllItemsInAnyOrder(List<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(Iterable<T> item) {
        List<T> items = StreamSupport.stream(item.spliterator(), false).collect(Collectors.toList());
        return items.size() == matchers.size() && new MatchingSomeItems<>(matchers).matches(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Should contains in any order items matching ").appendList("[\n", ",\n", "\n]", matchers);
    }
}
