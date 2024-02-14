
public enum FoodFreshness implements Tag {
    EXPIRED("expired"),
    FRESH("fresh"),
    NEAR_EXPIRY("near expiry");

    private final String displayName;

    FoodFreshness(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
