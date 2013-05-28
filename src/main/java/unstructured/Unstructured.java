package unstructured;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class Unstructured {
    private final ImmutableMap<String, Object> data;

    public Unstructured() {
        this(Maps.<String, Object>newHashMap());
    }

    public Unstructured(Map<String, Object> data) {
        this.data = ImmutableMap.copyOf(data);
    }

    public <T> Unstructured map(String replaceKey, Function<T, T> function) {
        checkArgument(
                data.containsKey(replaceKey), "Unstructured has no value for key: '%s'", replaceKey);

        ImmutableMap.Builder<String, Object> builder = ImmutableMap.<String, Object>builder()
                .put(replaceKey, function.apply((T) data.get(replaceKey)));

        for(String key : data.keySet()){
            if(key.equals(replaceKey)){
                continue;
            }
            builder.put(key, data.get(key));
        }

        return new Unstructured(builder.build());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unstructured that = (Unstructured) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
