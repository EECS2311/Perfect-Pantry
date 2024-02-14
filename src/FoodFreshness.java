
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

    public static FoodFreshness fromString(String text) {
        for (FoodFreshness b : FoodFreshness.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
