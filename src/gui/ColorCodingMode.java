package gui;

/**
 * Enum representing the different modes of color coding in the GUI. Color coding can be turned off,
 * applied based on the freshness of the food, or based on the food group category.
 */
public enum ColorCodingMode {
    OFF,           // No color coding is applied.
    BY_FRESHNESS,  // Color coding is applied based on the freshness of the food.
    BY_FOOD_GROUP  // Color coding is applied based on the food group category.
}