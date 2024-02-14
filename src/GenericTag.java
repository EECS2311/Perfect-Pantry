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

    // Static method workaround: Provide a method that works similarly to fromString
    // This method will need to be implemented externally, possibly in a utility class or as a static method in the specific enum class
}
