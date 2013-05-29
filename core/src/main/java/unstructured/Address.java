package unstructured;

import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;

class Address implements Iterable<Object>{
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
