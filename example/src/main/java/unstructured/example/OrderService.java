package unstructured.example;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class OrderService extends Service<OrderConfiguration> {
    public static void main(String[] args) throws Exception {
        new OrderService().run(args);
    }

    @Override public void initialize(Bootstrap<OrderConfiguration> bootstrap) {
        bootstrap.setName("order-service");
    }

    @Override public void run(OrderConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new OrderResource(new OrderRepository()));
    }
}
