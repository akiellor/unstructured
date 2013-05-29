package unstructured;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

class AddressableObjectTree {
    private final ImmutableMap<Object, Object> data;

    public AddressableObjectTree(Map<Object, Object> data) {
        this.data = ImmutableMap.copyOf(data);
    }

    public <T> T get(Address address) {
        Object result = data;
        for(Object part : address){
            result = ((Map<String, Object>)result).get(part);
        }
        return (T)result;
    }

    public AddressableObjectTree put(Address address, Object value) {
        ImmutableMap<Object, Object> change = ImmutableMap.of((Object)Iterables.getLast(address), value);
        List<Object> keys = Lists.reverse(Lists.newArrayList(address));
        for(Object key : keys.subList(0, keys.size() - 1)){
            change = ImmutableMap.<Object, Object>of(key, change);
        }
        return new AddressableObjectTree(ImmutableMaps.merge(data, change));
    }

    public boolean hasKey(Address address) {
        Object current = data;
        for(Object key : address){
            current = ((ImmutableMap<Object, Object>)current).get(key);
        }
        return current != null;
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

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("AddressableObjectTree{");
        sb.append("data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
