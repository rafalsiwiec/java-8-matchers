package io.rasi.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

public final class MoreMatchers {

    public static <T> Matcher<Collection<T>> isACollectionWhich(Matcher<Iterable<T>> iterableMatcher) {
        return new TypeSafeMatcher<Collection<T>>() {

            @Override
            public boolean matchesSafely(Collection<T> item) {
                return iterableMatcher.matches(item);
            }

            @Override
            public void describeTo(Description description) {
                iterableMatcher.describeTo(description);
            }
        };
    }

    public static <T> Matcher<Set<T>> isASetWhich(Matcher<Collection<T>> iterableMatcher) {
        return new TypeSafeMatcher<Set<T>>() {

            @Override
            public boolean matchesSafely(Set<T> item) {
                return iterableMatcher.matches(item);
            }

            @Override
            public void describeTo(Description description) {
                iterableMatcher.describeTo(description);
            }
        };
    }

    public static <T> Matcher<List<T>> isAListWhich(Matcher<Iterable<T>> iterableMatcher) {
        return new TypeSafeMatcher<List<T>>() {

            @Override
            public boolean matchesSafely(List<T> item) {
                return iterableMatcher.matches(item);
            }

            @Override
            public void describeTo(Description description) {
                iterableMatcher.describeTo(description);
            }
        };
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<T>> allItemsAreMatchingRespectively(Matcher<? extends T>... matchers) {
        return new MatchingAllItemsRespectively<>(asList(matchers));
    }

    public static <T> Matcher<Iterable<T>> containsItem(Matcher<? extends T> matcher) {
        return new MatchingSomeItems<>(asList(matcher));
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<T>> containsItems(Matcher<? extends T>... matchers) {
        return new MatchingSomeItems<>(asList(matchers));
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<T>> containsOnlyItems(Matcher<? extends T>... matchers) {
        return new MatchingAllItemsInAnyOrder<>(asList(matchers));
    }

    public static <X> PredicateMatcherBuilder<X> isInstanceOf(Class<X> aClass) {
        return new PredicateMatcherBuilder<>(aClass);
    }

    public static class PredicateMatcherBuilder<X> {

        private final Class<X> aClass;

        public PredicateMatcherBuilder(Class<X> aClass) {
            this.aClass = aClass;
        }

        public PredicateMatcher<X> satisfyingPredicate(Predicate<X> predicate) {
            return satisfyPredicate(x -> aClass.isAssignableFrom(x.getClass()) && predicate.test(x));
        }

        public PredicateMatcher<X> satisfyingPredicate(String description, Predicate<X> predicate) {
            return satisfyPredicate(description, x -> aClass.isAssignableFrom(x.getClass()) && predicate.test(x));
        }
    }

    public static <T> PredicateMatcher<T> satisfyPredicate(Predicate<T> predicate) {
        return new PredicateMatcher<>(predicate);
    }

    public static <T> PredicateMatcher<T> satisfyPredicate(String description, Predicate<T> predicate) {
        return new PredicateMatcher<>(predicate, description);
    }

    private MoreMatchers() {
    }
}
