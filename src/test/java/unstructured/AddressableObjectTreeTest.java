package unstructured;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AddressableObjectTreeTest {
    @Test
    public void shouldGetValue() {
        AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 1));

        Integer value = tree.get(new Address("foo"));

        assertThat(value, equalTo(1));
    }

    @Test
    public void shouldPutValue() {
        AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 1));

        AddressableObjectTree actual = tree.put(new Address("foo"), 2);

        AddressableObjectTree expected = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 2));

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldRetainOtherValuesWhenPutValue() {
        AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 1, "bar", 7));

        AddressableObjectTree actual = tree.put(new Address("foo"), 2);

        AddressableObjectTree expected = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 2, "bar", 7));

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldHaveKeyWhenKeyPresent() {
        AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 1));

        assertTrue(tree.hasKey("foo"));
    }

    @Test
    public void shouldNotHaveKeyWhenKeyMissing() {
        AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.<Object, Object>of("foo", 1));

        assertFalse(tree.hasKey("bar"));
    }
}
