package io.rasi.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.function.Predicate;

public class PredicateMatcher<T> extends TypeSafeMatcher<T> {

    private final Predicate<T> predicate;
    private final String description;

    public PredicateMatcher(Predicate<T> predicate) {
        this(predicate, "should satisfy predicate");
    }

    public PredicateMatcher(Predicate<T> predicate, String description) {
        this.predicate = predicate;
        this.description = description;
    }

    @Override
    public boolean matchesSafely(T t) {
        return predicate.test(t);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.description);
    }

}
