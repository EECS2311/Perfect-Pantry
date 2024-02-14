/**
 * Represents the freshness levels of food items.
 */
public enum FoodFreshness implements Tag {
    /**
     * Indicates that the food item has expired.
     */
    EXPIRED("expired"),

    /**
     * Indicates that the food item is fresh.
     */
    FRESH("fresh"),

    /**
     * Indicates that the food item is nearing its expiry date.
     */
    NEAR_EXPIRY("near expiry");

    private final String displayName;

    /**
     * Constructs a new FoodFreshness enum constant with the specified display name.
     *
     * @param displayName the display name for the freshness level.
     */
    FoodFreshness(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the freshness level.
     *
     * @return the display name of the freshness level.
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }
}