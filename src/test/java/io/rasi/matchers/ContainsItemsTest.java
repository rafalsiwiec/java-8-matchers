package io.rasi.matchers;

import org.testng.annotations.Test;

import static io.rasi.matchers.MoreMatchers.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class ContainsItemsTest {

    @Test
    public void shouldNotMatchIfSingleItemSatisfiesTwoMatchers() throws Exception {
       assertThat(asList(1, 2, 3), not(containsItems(
               satisfyPredicate(i -> i == 2),
               satisfyPredicate(i -> i == 2)
       )));
    }

    @Test
    public void shouldNotMatchIfPositive() throws Exception {
        assertThat(asList(1, 2, 3, 4), not(containsItems(
                satisfyPredicate("must be equal to 1",
                        i -> i == 1),
                satisfyPredicate("must be odd",
                        i -> i % 2 == 1),
                satisfyPredicate("must be even",
                        i -> i % 2 == 0),
                satisfyPredicate("must be equal to 2",
                        i -> i == 2)
        )));
    }

    @Test
    public void shouldMatchManyItems() throws Exception {
        assertThat(asList(1, 2, 3, 4, 5), containsItems(
                satisfyPredicate("must be equal to 2",
                        i -> i == 2),
                satisfyPredicate("must be equal to 4",
                        i -> i == 4),
                satisfyPredicate("must be equal to 5",
                        i -> i == 5)
        ));
    }
}
