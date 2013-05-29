package unstructured;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class BasicConverter implements Converter {
    private static class Key<T, V>{
        private final Class<T> one;
        private final Class<V> two;

        public Key(Class<T> one, Class<V> two){
            this.one = one;
            this.two = two;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (one != null ? !one.equals(key.one) : key.one != null) return false;
            if (two != null ? !two.equals(key.two) : key.two != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = one != null ? one.hashCode() : 0;
            result = 31 * result + (two != null ? two.hashCode() : 0);
            return result;
        }
    }

    private interface TypeConverter<T, V>{
        V forward(T t);
        T backward(V t);
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableMap<Key, TypeConverter> CONVERTERS = ImmutableMap.of(
        new Key(String.class, Integer.class), (TypeConverter)new TypeConverter<String, Integer>() {
            @Override public Integer forward(String string) {
                return Integer.parseInt(string);
            }

            @Override public String backward(Integer integer) {
                return String.valueOf(integer);
            }
        }
    );

    @Override public <T> Function<Object, Object> apply(final Function<T, T> operation, final Class<T> two) {
        return new Function<Object, Object>() {
            @Override public Object apply(Object object) {
                Class<?> one = object.getClass();

                TypeConverter typeConverter = getTypeConverter(one, two);
                return typeConverter.backward(operation.apply((T)typeConverter.forward(object)));
            }

            private TypeConverter getTypeConverter(Class<?> one, Class<?> two) {
                Key key = new Key(one, two);
                TypeConverter typeConverter = CONVERTERS.get(key);
                if(typeConverter == null){
                    throw new IllegalArgumentException(String.format("No suitable converter for classes: %s", Lists.newArrayList(one, two)));
                }
                return typeConverter;
            }
        };
    }
}
