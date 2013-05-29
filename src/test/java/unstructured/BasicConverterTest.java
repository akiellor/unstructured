package unstructured;

import com.google.common.base.Function;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class BasicConverterTest {
    @Rule public ExpectedException exception = ExpectedException.none();

    BasicConverter converter = new BasicConverter();

    @Test
    public void shouldConvertStringToInteger() {
        Function<Object, Object> function = converter.apply(new Function<Integer, Integer>(){
            @Override public Integer apply(Integer integer) {
                return integer + 1;
            }
        }, Integer.class);

        assertThat(function.apply("7"), equalTo((Object)"8"));
    }

    @Test
    public void shouldThrowExceptionWhenNoConverterExistsForTypes() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No suitable converter for classes: [class java.lang.String, class java.lang.Number]");

        converter.apply(mock(Function.class), Number.class).apply("7");
    }
}
