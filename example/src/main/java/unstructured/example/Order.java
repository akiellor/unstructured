package unstructured.example;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import unstructured.Address;
import unstructured.Unstructured;

import java.util.Map;

@JsonSerialize(using = OrderSerializer.class)
public class Order{
    private final Unstructured unstructured;

    public Order(Unstructured unstructured) {
        this.unstructured = unstructured;
    }

    public Map<Object, Object> asMap() {
        return unstructured.asMap();
    }

    public Order change(Unstructured changes) {
        return new Order(unstructured.merge(changes.reject(Lists.<Address>newArrayList(new Address("id")))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (unstructured != null ? !unstructured.equals(order.unstructured) : order.unstructured != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return unstructured != null ? unstructured.hashCode() : 0;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("unstructured=").append(unstructured);
        sb.append('}');
        return sb.toString();
    }
}
