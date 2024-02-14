
public enum FoodGroup implements Tag {
    GRAINS("grains"),
    PROTEIN("protein"),
    VEGETABLES("vegetables"),
    FRUITS("fruits"),
    DAIRY("dairy");

    private final String displayName;

    FoodGroup(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static FoodGroup fromString(String text) {
        for (FoodGroup b : FoodGroup.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
