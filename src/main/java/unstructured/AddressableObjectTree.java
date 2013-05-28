package unstructured;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

class AddressableObjectTree {
    private final Map<String, Object> data;

    public AddressableObjectTree(Map<String, Object> data) {
        this.data = ImmutableMap.copyOf(data);
    }

    public <T> T get(String key) {
        return (T)data.get(key);
    }

    public AddressableObjectTree put(String replaceKey, Object value) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.<String, Object>builder();
        builder.put(replaceKey, value);
        for(String key : data.keySet()){
            if(key.equals(replaceKey)){
                continue;
            }
            builder.put(key, data.get(key));
        }
        return new AddressableObjectTree(builder.build());
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
