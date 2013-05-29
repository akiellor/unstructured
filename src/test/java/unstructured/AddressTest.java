package unstructured;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AddressTest {
    @Test
    public void shouldGetParentOfAddress() {
        Address child = new Address("foo", "bar");

        assertThat(child.parent(), equalTo(new Address("foo")));
    }
}
