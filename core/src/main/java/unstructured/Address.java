package unstructured;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;

public class Address implements Iterable<Object>{
    private final List<Object> parts;

    public static Address parse(String addressString) {
        return new Address(addressString.split("\\."));
    }

    public Address(Iterable<Object> parts) {
        this.parts = ImmutableList.copyOf(parts);
    }

    public Address(Object... parts) {
        this(ImmutableList.copyOf(parts));
    }

    @Override public Iterator<Object> iterator() {
        return parts.iterator();
    }

    public Address parent() {
        return new Address(parts.subList(0, parts.size() - 1));
    }

    public Address push(Object key) {
        return new Address(ImmutableList.builder().addAll(parts).add(key).build());
    }

    public boolean isAncestorOf(Address input) {
        if(input.parts.size() <= parts.size()) { return false; }
        return this.equals(new Address(input.parts.subList(0, Math.min(input.parts.size(), parts.size()))));
    }

    public Address relativeTo(Address parent) {
        if(parent.equals(this)) { return new Address(); }
        Preconditions.checkArgument(parent.isAncestorOf(this));
        return new Address(this.parts.subList(parent.parts.size(), this.parts.size()));
    }

    public String path() {
        return Joiner.on(".").join(parts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address objects = (Address) o;

        if (parts != null ? !parts.equals(objects.parts) : objects.parts != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return parts != null ? parts.hashCode() : 0;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("parts=").append(parts);
        sb.append('}');
        return sb.toString();
    }
}
