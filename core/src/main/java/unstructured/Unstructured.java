package unstructured;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class Unstructured {
    private final ImmutableMap<Address, Object> objects;

    public Unstructured() {
        this(Maps.<Object, Object>newHashMap());
    }

    public Unstructured(Map<Object, Object> data) {
        this(flatten(ImmutableMap.copyOf(data)));
    }

    public Unstructured(ImmutableMap<Address, Object> objects) {
        this.objects = objects;
    }

    private static ImmutableMap<Address, Object> flatten(Object tree) {
        Map<Address, Object> work = Maps.newHashMap(ImmutableMap.of(new Address(), tree));
        ImmutableMap.Builder<Address, Object> leaves = ImmutableMap.builder();
        while(!work.isEmpty()){
            Address next = work.keySet().iterator().next();
            Object maybeLeaf = work.remove(next);
            if(maybeLeaf instanceof Map){
                for(Map.Entry<Object, Object> entry : ((Map<Object, Object>) maybeLeaf).entrySet()){
                    work.put(next.push(entry.getKey()), entry.getValue());
                }
            }else{
                leaves.put(next, maybeLeaf);
            }
        }
        return leaves.build();
    }

    public <T> Unstructured map(String replaceKey, Function<T, T> function) {
        final Address address = Address.parse(replaceKey);
        checkArgument(
                objects.containsKey(address), "Unstructured has no value for key: '%s'", replaceKey);

        HashMap<Address, Object> map = Maps.newHashMap();
        map.putAll(objects);
        map.put(address, function.apply((T) objects.get(address)));

        return new Unstructured(ImmutableMap.copyOf(map));
    }

    public <T> T get(Object key) {
        final Address parent = new Address(key);
        if(objects.containsKey(parent)){
            return (T) objects.get(parent);
        }else{
            Iterable<Address> children = Iterables.filter(objects.keySet(), new Predicate<Address>() {
                @Override public boolean apply(Address input) {
                    return parent.isAncestorOf(input);
                }
            });
            AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.of());
            for(Address address : children){
                tree = tree.put(address.relativeTo(parent), objects.get(address));
            }
            return (T) tree.asMap();
        }
    }

    public Unstructured merge(Unstructured unstructured) {
        HashMap<Address, Object> map = Maps.newHashMap();
        map.putAll(objects);
        map.putAll(unstructured.objects);
        return new Unstructured(ImmutableMap.copyOf(map));
    }

    public Unstructured constrain(Iterable<Address> addresses){
        ImmutableMap.Builder<Address, Object> builder = ImmutableMap.builder();
        for(Address address : addresses){
            builder.put(address, objects.get(address));
        }
        return new Unstructured(builder.build());
    }

    public Map<Object,Object> asMap() {
        AddressableObjectTree tree = new AddressableObjectTree(ImmutableMap.of());
        for(Map.Entry<Address, Object> entry : objects.entrySet()){
            tree = tree.put(entry.getKey(), entry.getValue());
        }
        return tree.asMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unstructured that = (Unstructured) o;

        if (objects != null ? !objects.equals(that.objects) : that.objects != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return objects != null ? objects.hashCode() : 0;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Unstructured{");
        sb.append("objects=").append(objects);
        sb.append('}');
        return sb.toString();
    }

}
