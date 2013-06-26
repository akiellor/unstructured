package unstructured.example;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import unstructured.Address;
import unstructured.Unstructured;

import java.util.ArrayList;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

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
        return state().change(changes);
    }

    private Status state(){
        Object status = unstructured.get("status");
        if(Status.Settled.isSettled(status)){
            return new Status.Settled();
        }else{
            return new Status.Default(unstructured);
        }
    }

    private interface Status {
        Order change(Unstructured changes);

        class Default implements Status {
            public static final ArrayList<Address> IMMUTABLE_FIELDS = newArrayList(
                    new Address("id"),
                    new Address("buyer"),
                    new Address("seller"));

            private final Unstructured unstructured;

            public Default(Unstructured unstructured){
                this.unstructured = unstructured;
            }

            @Override public Order change(Unstructured changes) {
                return new Order(unstructured.merge(changes).reject(IMMUTABLE_FIELDS));
            }
        }

        class Settled implements Status {
            private static final String NAME = "SETTLED";

            public static boolean isSettled(Object status) {
                return NAME.equals(status);
            }

            @Override public Order change(Unstructured changes) {
                throw new IllegalStateException();
            }
        }
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
