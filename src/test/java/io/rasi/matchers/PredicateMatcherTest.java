package io.rasi.matchers;

import org.testng.annotations.Test;

import java.util.function.Predicate;

import static io.rasi.matchers.MoreMatchers.*;
import static java.util.Objects.nonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class PredicateMatcherTest {

    @Test
    public void shouldIncludePassedDescription() throws Exception {
        String predicateDescription = "is empty string";
        Throwable t = null;
        try {
            assertThat("this string",
                    satisfyPredicate(predicateDescription, String::isEmpty));
        } catch (Throwable thrown) {
            t = thrown;
        }
        assertThat(t.getMessage(), containsString(predicateDescription));

        Runnable x = () -> System.out.println("");
    }

    @Test
    public void shouldWork() throws Exception {
        assertThatStatement(() -> System.out.println("xxx")).throwsExceptionMatching(Throwable.class, t -> t != null);
    }

    public static <T extends Throwable> ExceptionMatcherBuilder<T> assertThatStatement(Runnable runnable) {
        return new ExceptionMatcherBuilder<>(runnable);
    }

    public static class ExceptionMatcherBuilder<T extends Throwable> {

        private final Runnable runnable;

        public ExceptionMatcherBuilder(Runnable runnable) {
            this.runnable = runnable;
        }

        @SuppressWarnings("unchecked")
        public void throwsExceptionMatching(Class<T> exceptionClass, Predicate<T> exceptionPredicate) {

            nonNull(exceptionClass);

            Throwable thrown = null;
            try {
                runnable.run();
            } catch (Throwable throwable) {
                thrown = throwable;
            }

            assertThat("No exception was thrown. Expected instance of: " + exceptionClass.getName(), thrown, notNullValue());
            assertThat(thrown, org.hamcrest.Matchers.instanceOf(exceptionClass));
            assertThat((T) thrown, MoreMatchers.satisfyPredicate(exceptionPredicate));
        }

    }

}
