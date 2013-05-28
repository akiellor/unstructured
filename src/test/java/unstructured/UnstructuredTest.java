package unstructured;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UnstructuredTest {
    @Test
    public void shouldReplaceExistingKeyValueWithNewValue() {
        Unstructured unstructured = new Unstructured(ImmutableMap.<String, Object>builder()
                .put("foo", 1)
                .build());

        Unstructured actual = unstructured.map("foo", new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        });

        Unstructured expected = new Unstructured(ImmutableMap.<String, Object>builder()
            .put("foo", 2)
            .build());

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldRetainOtherValuesWhenMapping() {
        Unstructured unstructured = new Unstructured(ImmutableMap.<String, Object>builder()
                .put("foo", 1)
                .put("bar", 7)
                .build());

        Unstructured actual = unstructured.map("foo", new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        });

        Unstructured expected = new Unstructured(ImmutableMap.<String, Object>builder()
                .put("foo", 2)
                .put("bar", 7)
                .build());

        assertThat(actual, equalTo(expected));
    }
}
