package unstructured;

import com.google.common.base.Function;

public interface Converter {
    <T> Function<Object, Object> apply(Function<T, T> operation, Class<T> clazz);
}
