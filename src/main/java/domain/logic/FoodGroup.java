package domain.logic;

/**
 * Represents the food group categories.
 */
public enum FoodGroup implements Tag {
	/**
	 * Represents grains food group.
	 */
	GRAIN("Grain"),

	/**
	 * Represents protein food group.
	 */
	PROTEIN("Protein"),

	/**
	 * Represents vegetable food group.
	 */
	VEGETABLE("Vegetable"),

	/**
	 * Represents fruit food group.
	 */
	FRUIT("Fruit"),

	/**
	 * Represents dairy food group.
	 */
	DAIRY("Dairy");

	private final String displayName;

	/**
	 * Constructs a new FoodGroup enum constant with the specified display name.
	 *
	 * @param displayName the display name for the food group.
	 */
	FoodGroup(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the display name of the food group.
	 *
	 * @return the display name of the food group.
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}
}