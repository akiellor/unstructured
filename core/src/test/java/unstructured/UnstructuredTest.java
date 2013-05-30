package unstructured;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
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

    @Test
    public void shouldPerformTypeConversionAndOperation() {
        BasicConverter converter = new BasicConverter();
        Unstructured unstructured = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", ImmutableMap.of("bar", "7"))
                .build());

        Unstructured actual = unstructured.map("foo.bar", converter.apply(new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        }, Integer.class));

        Unstructured expected = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", ImmutableMap.of("bar", "8"))
                .build());

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldGetValue() {
        Unstructured unstructured = new Unstructured(ImmutableMap.<Object, Object>builder()
                .put("foo", ImmutableMap.of("bar", "7"))
                .build());

        assertThat(unstructured.<ImmutableMap>get("foo"), equalTo(ImmutableMap.of("bar", "7")));
    }

    @Test
    public void shouldMergeTwoUnstructured() {
        Unstructured one = new Unstructured(ImmutableMap.<Object, Object>of("foo", ImmutableMap.of("bar", 1), "baz", 9));
        Unstructured two = new Unstructured(ImmutableMap.<Object, Object>of("foo", ImmutableMap.of("bar", 7)));

        assertThat(one.merge(two), equalTo(
                new Unstructured(ImmutableMap.<Object, Object>of("foo", ImmutableMap.of("bar", 7), "baz", 9))));
    }
}
