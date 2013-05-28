package unstructured;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class Unstructured {
    private final AddressableObjectTree tree;

    public Unstructured() {
        this(Maps.<String, Object>newHashMap());
    }

    public Unstructured(Map<String, Object> data) {
        this.tree = new AddressableObjectTree(ImmutableMap.copyOf(data));
    }

    private Unstructured(AddressableObjectTree tree){
        this.tree = tree;
    }

    public <T> Unstructured map(String replaceKey, Function<T, T> function) {
        checkArgument(
                tree.hasKey(replaceKey), "Unstructured has no value for key: '%s'", replaceKey);

        AddressableObjectTree tree = this.tree.put(replaceKey, function.apply((T) this.tree.get(replaceKey)));

        return new Unstructured(tree);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unstructured that = (Unstructured) o;

        if (tree != null ? !tree.equals(that.tree) : that.tree != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tree != null ? tree.hashCode() : 0;
    }
}
