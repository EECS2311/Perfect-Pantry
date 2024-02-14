import java.util.Objects;

public class GenericTag<T extends Tag> {
    private T tag;

    public GenericTag(T tag) {
        this.tag = tag;
    }

    public void setValue(T newValue) {
        this.tag = newValue;
    }

    public T getValue() {
        return tag;
    }

    @Override
    public String toString() {
        return tag.getDisplayName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericTag<?> that = (GenericTag<?>) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    // Static method workaround: Provide a method that works similarly to fromString
    // This method will need to be implemented externally, possibly in a utility class or as a static method in the specific enum class
}
