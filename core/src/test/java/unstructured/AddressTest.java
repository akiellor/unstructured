package unstructured;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class AddressTest {
    @Rule public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldGetParentOfAddress() {
        Address child = new Address("foo", "bar");

        assertThat(child.parent(), equalTo(new Address("foo")));
    }

    @Test
    public void shouldParseDotSeparatedString() {
        assertThat(Address.parse("foo.bar"), equalTo(new Address("foo", "bar")));
    }

    @Test
    public void shouldPushNewPartToAddress() {
        assertThat(new Address().push("foo"), equalTo(new Address("foo")));
    }

    @Test
    public void shouldCheckAncestry() {
        assertTrue(new Address().isAncestorOf(new Address("foo")));
        assertTrue(new Address().isAncestorOf(new Address("foo", "bar")));
        assertTrue(new Address("foo").isAncestorOf(new Address("foo", "bar", "baz")));
        assertFalse(new Address().isAncestorOf(new Address()));
        assertFalse(new Address("foo").isAncestorOf(new Address()));
    }

    @Test
    public void shouldBuildRelativePath() {
        assertThat(new Address().relativeTo(new Address()), equalTo(new Address()));
        assertThat(new Address("foo").relativeTo(new Address("foo")), equalTo(new Address()));
        assertThat(new Address("foo").relativeTo(new Address()), equalTo(new Address("foo")));
        assertThat(new Address("foo", "bar").relativeTo(new Address("foo")), equalTo(new Address("bar")));
    }

    @Test
    public void shouldNotBuildRelativePath() {
        exception.expect(IllegalArgumentException.class);

        new Address("foo").relativeTo(new Address("bar"));
    }
}
