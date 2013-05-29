package unstructured;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ImmutableMapsTest {
    @Test
    public void shouldMergeTwoImmutableMaps() {
        ImmutableMap<String, Integer> one = ImmutableMap.of("foo", 1);
        ImmutableMap<String, Integer> two = ImmutableMap.of("bar", 2);

        assertThat(ImmutableMaps.merge(one, two), equalTo(ImmutableMap.<Object, Object>of("foo", 1, "bar", 2)));
    }

    @Test
    public void shouldOverwriteKeysFromFirst() {
        ImmutableMap<String, Integer> one = ImmutableMap.of("foo", 1);
        ImmutableMap<String, Integer> two = ImmutableMap.of("foo", 3);

        assertThat(ImmutableMaps.merge(one, two), equalTo(ImmutableMap.<Object, Object>of("foo", 3)));
    }

    @Test
    public void shouldOverwriteNestedKeysFromFirst() {
        ImmutableMap<Object, Object> one = ImmutableMap.<Object, Object>of(
                "foo", ImmutableMap.of("bar", 1, "baz", 7));

        ImmutableMap<Object, Object> two = ImmutableMap.<Object, Object>of(
                "foo", ImmutableMap.of("bar", 3));

        assertThat(ImmutableMaps.merge(one, two), equalTo(ImmutableMap.<Object, Object>of(
                "foo", ImmutableMap.of("bar", 3, "baz", 7))));
    }

    @Test
    public void shouldAddAdditionalNestedValuesFromSecond() {
        ImmutableMap<Object, Object> one = ImmutableMap.<Object, Object>of(
                "foo", ImmutableMap.of("bar", 1, "baz", 7));

        ImmutableMap<Object, Object> two = ImmutableMap.<Object, Object>of(
                "foo", ImmutableMap.of("bar", 3, "qux", 6));

        assertThat(ImmutableMaps.merge(one, two), equalTo(ImmutableMap.<Object, Object>of(
                "foo", ImmutableMap.of("bar", 3, "baz", 7, "qux", 6))));
    }
}
