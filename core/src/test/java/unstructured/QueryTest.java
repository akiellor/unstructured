package unstructured;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QueryTest {
    @Test
    public void shouldMatchFullPaths() {
        assertTrue(Query.of("foo").apply(new Address("foo")));
        assertFalse(Query.of("foo").apply(new Address("baz")));
    }

    @Test
    public void shouldMatchFullNestedPaths() {
        assertTrue(Query.of("foo.bar").apply(new Address("foo.bar")));
        assertFalse(Query.of("foo.baz").apply(new Address("foo.bar")));
    }

    @Test
    public void shouldMatchFullNestedPathsWithWildCard() {
        assertTrue(Query.of("foo.*").apply(new Address("foo.bar")));
        assertTrue(Query.of("foo.*").apply(new Address("foo.baz")));
        assertTrue(Query.of("*").apply(new Address("foo.baz")));
        assertFalse(Query.of("bar.*").apply(new Address("foo.baz")));
    }
}
