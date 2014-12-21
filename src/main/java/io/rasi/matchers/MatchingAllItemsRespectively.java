package io.rasi.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Iterator;
import java.util.List;

class MatchingAllItemsRespectively<T> extends TypeSafeMatcher<Iterable<T>> {

    private final List<Matcher<? extends T>> matchers;

    public MatchingAllItemsRespectively(List<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(Iterable<T> item) {
        return matchLoop(matchers.iterator(), item.iterator());
    }

    private boolean matchLoop(Iterator<Matcher<? extends T>> matcherIt, Iterator<T> itemIt) {
        boolean noMoreMatchers = !matcherIt.hasNext();
        boolean noMoreItems = !itemIt.hasNext();

        if (noMoreMatchers && noMoreItems) return true;
        if (noMoreMatchers || noMoreItems) return false;
        if (!matcherIt.next().matches(itemIt.next())) return false;
        return matchLoop(matcherIt, itemIt);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("All items should match respectively ").appendList("[\n", ",\n", "]", matchers);
    }
}
