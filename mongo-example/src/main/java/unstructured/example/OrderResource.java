package unstructured.example;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;
import unstructured.Unstructured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private final OrderRepository repository;

    public OrderResource(OrderRepository repository){
        this.repository = repository;
    }

    @GET
    @Path("/{id}")
    @Timed
    public Optional<Order> findOrder(@PathParam("id") String id) {
        return repository.with(id, Functions.<Order>identity());
    }

    @Path("/{id}")
    @POST
    public Optional<Order> updateOrder(@PathParam("id") final String id, final Map<Object, Object> changes){
        return repository.with(id, new Function<Order, Order>() {
            @Override public Order apply(Order input) {
                return input.change(new Unstructured(changes));
            }
        });
    }
}
