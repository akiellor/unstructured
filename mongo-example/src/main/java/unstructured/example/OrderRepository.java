package unstructured.example;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import unstructured.Unstructured;

import java.util.HashMap;

public class OrderRepository {

    private final HashMap<String, Order> orders;

    public OrderRepository() {
        orders = new HashMap<String, Order>();
        orders.put("1", new Order(new Unstructured(ImmutableMap.<Object, Object>of(
                "id", 1,
                "buyer", 1234,
                "lineItems", ImmutableMap.of(
                "1", ImmutableMap.of("product", 5678),
                "2", ImmutableMap.of("product", 9876))))));
    }

    public Optional<Order> with(String id, Function<Order, Order> function) {
        return Optional.fromNullable(orders.get(id)).transform(function);
    }
}
