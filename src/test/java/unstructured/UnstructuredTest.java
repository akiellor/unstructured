package unstructured;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class UnstructuredTest {
    @Rule public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReplaceExistingKeyValueWithNewValue() {
        Unstructured unstructured = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", 1)
                .build());

        Unstructured actual = unstructured.map("foo", new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        });

        Unstructured expected = new Unstructured(ImmutableMap.<Object, Object>builder()
            .put("foo", 2)
            .build());

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldRetainOtherValuesWhenMapping() {
        Unstructured unstructured = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", 1)
                .put("bar", 7)
                .build());

        Unstructured actual = unstructured.map("foo", new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        });

        Unstructured expected = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", 2)
                .put("bar", 7)
                .build());

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForInvalidKey() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Unstructured has no value for key: 'foo'");

        new Unstructured().map("foo", mock(Function.class));
    }

    @Test
    @Ignore
    public void shouldYieldANestedField() {
        Unstructured unstructured = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", ImmutableMap.of("bar", 7))
                .build());

        Unstructured actual = unstructured.map("foo.bar", new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        });

        Unstructured expected = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", ImmutableMap.of("bar", 8))
                .build());

        assertThat(actual, equalTo(expected));
    }
}
