
public enum FoodGroup implements Tag {
    GRAIN("grain"),
    PROTEIN("protein"),
    VEGETABLE("vegetable"),
    FRUIT("fruit"),
    DAIRY("dairy");

    private final String displayName;

    FoodGroup(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
