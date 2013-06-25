package unstructured.example;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import unstructured.Unstructured;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrderTest {
    @Test
    public void shouldNotAllowChangesToId() {
        Order order = new Order(new Unstructured());

        Order newOrder = order.change(new Unstructured(ImmutableMap.<Object, Object>of("id", 9)));

        assertThat(newOrder, equalTo(order));
    }
}
