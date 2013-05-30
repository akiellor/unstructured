package unstructured.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    String database;

    public String getDatabase() {
        return database;
    }
}
