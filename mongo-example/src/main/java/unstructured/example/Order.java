package unstructured.example;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
        return new Order(unstructured.merge(changes));
    }
}
