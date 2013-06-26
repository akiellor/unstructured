package unstructured.example;

import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import unstructured.Unstructured;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrderTest {
    @Rule public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotAllowChangesToId() {
        Order order = new Order(new Unstructured());

        Order newOrder = order.change(new Unstructured(ImmutableMap.<Object, Object>of("id", 9)));

        assertThat(newOrder, equalTo(order));
    }

    @Test
    public void shouldNotAllowChangesToBuyer() {
        Order order = new Order(new Unstructured());

        Order newOrder = order.change(new Unstructured(ImmutableMap.<Object, Object>of("buyer", 9)));

        assertThat(newOrder, equalTo(order));
    }

    @Test
    public void shouldNotAllowChangesToSeller() {
        Order order = new Order(new Unstructured());

        Order newOrder = order.change(new Unstructured(ImmutableMap.<Object, Object>of("seller", 9)));

        assertThat(newOrder, equalTo(order));
    }

    @Test
    public void shouldNotAllowChangesWhenSettled() {
        exception.expect(IllegalStateException.class);

        Order order = new Order(new Unstructured(ImmutableMap.<Object, Object>of("status", "SETTLED")));

        order.change(new Unstructured(ImmutableMap.<Object, Object>of()));
    }
}
