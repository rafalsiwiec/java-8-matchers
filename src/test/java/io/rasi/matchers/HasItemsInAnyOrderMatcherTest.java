package io.rasi.matchers;

import org.testng.annotations.Test;

import java.util.Collection;

import static io.rasi.matchers.MoreMatchers.isInstanceOf;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HasItemsInAnyOrderMatcherTest {

    @Test
    public void testName() throws Exception {
        Interface mock = mock(Interface.class);
        mock.doNothing(asList(
                new Child(2, 2),
                new Child(2, 1),
                new Parent(10)
        ));

        verify(mock).doNothing(argThat(MoreMatchers.isACollectionWhich(
                        MoreMatchers.containsItem(
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.x == 2 && child.y == 1)
                        ))
        ));

        verify(mock).doNothing(argThat(MoreMatchers.isACollectionWhich(
                        MoreMatchers.containsItems(
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.y == 2),
                                isInstanceOf(Parent.class).satisfyingPredicate(parent -> parent.x == 10)
                        ))
        ));

        verify(mock).doNothing(argThat(MoreMatchers.isACollectionWhich(
                        MoreMatchers.containsItems(
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.y == 2),
                                isInstanceOf(AnotherChild.class).satisfyingPredicate(parent -> parent.z == 10)
                        ))
        ));

        verify(mock).doNothing(argThat(MoreMatchers.isACollectionWhich(
                        MoreMatchers.containsOnlyItems(
                                isInstanceOf(Parent.class).satisfyingPredicate(parent -> parent.x == 10),
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.y == 1),
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.y == 2)
                        ))
        ));

        verify(mock).doNothing(argThat(MoreMatchers.isACollectionWhich(
                        MoreMatchers.allItemsAreMatchingRespectively(
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.y == 2),
                                isInstanceOf(Child.class).satisfyingPredicate(child -> child.y == 1),
                                isInstanceOf(Parent.class).satisfyingPredicate(parent -> parent.x == 10)
                        ))
        ));
    }

}

// TESTING

interface Interface {
    void doNothing(Collection<? extends Parent> arg);
}

class Parent {
    public final int x;

    Parent(int x) {
        this.x = x;
    }
}

class Child extends Parent {
    public final int y;

    public Child(int x, int y) {
        super(x);
        this.y = y;
    }
}
class AnotherChild extends Parent {

    public final int z;

    public AnotherChild(int x, int y) {
        super(x);
        this.z = y;
    }
}