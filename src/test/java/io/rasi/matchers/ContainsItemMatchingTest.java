package io.rasi.matchers;

import org.testng.annotations.Test;

import static io.rasi.matchers.MoreMatchers.containsItem;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class ContainsItemMatchingTest {

    @Test
    public void shouldNotMatchEmptyIterable() throws Exception {
        assertThat(emptyList(), not(containsItem(MoreMatchers.satisfyPredicate(i -> true))));
    }

    @Test
    public void shouldMatchElement() throws Exception {
        assertThat(asList(1, 2, 3), containsItem(MoreMatchers.satisfyPredicate(i -> i == 2)));
    }

    @Test
    public void shouldMatchIfMultipleItemsSatisfyMatcher() throws Exception {
        assertThat(asList(2, 2), containsItem(MoreMatchers.satisfyPredicate(i -> i == 2)));
    }
}