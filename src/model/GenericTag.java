package model;

import java.util.Objects;

/**
 * Generic wrapper class for tagging objects with additional information.
 *
 * @param <T> the type of the tag (either {@code FoodFreshness} or {@code FoodGroup}), extending the Tag interface.
 */
public class GenericTag<T extends Tag> {
    private T tag;

    /**
     * Constructs a new GenericTag with the specified tag.
     *
     * @param tag the tag to wrap.
     */
    public GenericTag(T tag) {
        this.tag = tag;
    }

    /**
     * Constructs a new GenericTag by copying another GenericTag.
     *
     * @param tag another GenericTag to copy.
     */
    public GenericTag(GenericTag<T> tag) {
        this(tag.getValue());
    }

    /**
     * Sets the value of the tag.
     *
     * @param newValue the new value of the tag.
     */
    public void setValue(T newValue) {
        this.tag = newValue;
    }

    /**
     * Sets the tag's value to the value of another GenericTag.
     *
     * @param newValue another GenericTag whose value is to be used.
     */
    public void setValue(GenericTag<T> newValue) {
        if (newValue != null && newValue.getValue() != null) {
            this.tag = newValue.getValue();
        }
    }

    /**
     * Returns the current value of the tag.
     *
     * @return the current value of the tag.
     */
    public T getValue() {
        return tag;
    }

    /**
     * Returns the display name of the tag.
     *
     * @return the display name of the tag.
     */
    @Override
    public String toString() {
        return tag.getDisplayName();
    }

    /**
     * Indicates whether some other object is "equal to" this GenericTag.
     *
     * @param o the reference object with which to compare.
     * @return true if this GenericTag is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericTag<?> that = (GenericTag<?>) o;
        return Objects.equals(tag, that.tag);
    }

    /**
     * Returns a hash code value for this GenericTag.
     *
     * @return a hash code value for this GenericTag.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
