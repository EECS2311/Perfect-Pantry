import java.util.Objects;


public class GenericTag<T extends Tag> {
    private T tag;

    public GenericTag(T tag) {
        this.tag = tag;
    }

    public GenericTag(GenericTag<T>  tag) {
        this(tag.getValue());
    }


    public void setValue(T newValue) {
        this.tag = newValue;
    }

    public void setValue(GenericTag<T> newValue) {
        // Ensure newValue is not null to prevent NullPointerException
        if (newValue != null && newValue.getValue() != null) {
            this.tag = newValue.getValue();
        }
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

}
