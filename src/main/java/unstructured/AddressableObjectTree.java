package unstructured;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

class AddressableObjectTree {
    private final ImmutableMap<Object, Object> data;

    public AddressableObjectTree(Map<Object, Object> data) {
        this.data = ImmutableMap.copyOf(data);
    }

    public <T> T get(Object key) {
        return (T)data.get(key);
    }

    public AddressableObjectTree put(Object replaceKey, Object value) {
        return new AddressableObjectTree(ImmutableMaps.merge(data, ImmutableMap.of(replaceKey, value)));
    }

    public boolean hasKey(String key) {
        return data.containsKey(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressableObjectTree that = (AddressableObjectTree) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
