package unstructured;

import com.google.common.base.Predicate;

public class Query implements Predicate<Address>{
    private final String query;

    private Query(String query) {
        this.query = String.format("^%s$", query.replaceAll("\\.", "\\.").replaceAll("\\*", ".*?"));
    }

    public static Query of(String queryString) {
        return new Query(queryString);
    }

    @Override public boolean apply(Address input) {
        return input.path().matches(query);
    }
}
