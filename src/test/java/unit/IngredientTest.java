
package test.java.unit;

import org.junit.jupiter.api.Test;

import main.java.domain.logic.recipe.Ingredient;
import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    @Test
    void testIngredientCreationAndGetters() {
        Ingredient ingredient = new Ingredient(1, "Tomato", 2.5, "kg", "http://example.com/tomato.png", "2.5 kg of Tomatoes");

        assertEquals(1, ingredient.getId());
        assertEquals("Tomato", ingredient.getName());
        assertEquals(2.5, ingredient.getAmount());
        assertEquals("kg", ingredient.getUnit());
        assertEquals("http://example.com/tomato.png", ingredient.getImage());
        assertEquals("2.5 kg of Tomatoes", ingredient.getOriginal());
    }

    @Test
    void testSetters() {
        Ingredient ingredient = new Ingredient(1, "Tomato", 2.5, "kg", "http://example.com/tomato.png", "2.5 kg of Tomatoes");

        ingredient.setId(2);
        ingredient.setName("Potato");
        ingredient.setAmount(3.0);
        ingredient.setUnit("pcs");
        ingredient.setImage("http://example.com/potato.png");
        ingredient.setOriginal("3 pcs of Potatoes");

        assertEquals(2, ingredient.getId());
        assertEquals("Potato", ingredient.getName());
        assertEquals(3.0, ingredient.getAmount());
        assertEquals("pcs", ingredient.getUnit());
        assertEquals("http://example.com/potato.png", ingredient.getImage());
        assertEquals("3 pcs of Potatoes", ingredient.getOriginal());
    }

    @Test
    void testEquals() {
        Ingredient ingredient1 = new Ingredient(1, "Tomato", 2.5, "kg", "http://example.com/tomato.png", "2.5 kg of Tomatoes");
        Ingredient ingredient2 = new Ingredient(1, "Potato", 3.0, "pcs", "http://example.com/potato.png", "3 pcs of Potatoes");

        assertTrue(ingredient1.equals(ingredient2));
    }

}