package test.unit;

import domain.logic.recipe.Ingredient;
import domain.logic.recipe.Recipe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class RecipeTest {

    private Recipe recipe;
    private Ingredient usedIngredient;
    private Ingredient missedIngredient;

    @BeforeEach
    void setUp() {
        recipe = new Recipe(1, "Tomato Soup", "http://example.com/tomato_soup.png");
        usedIngredient = new Ingredient(1, "Tomato", 2.5, "kg", "http://example.com/tomato.png", "2.5 kg of Tomatoes");
        missedIngredient = new Ingredient(2, "Potato", 3.0, "pcs", "http://example.com/potato.png", "3 pcs of Potatoes");

        recipe.addUsedIngredient(usedIngredient);
        recipe.addMissedIngredient(missedIngredient);
    }

    @Test
    void testRecipeCreationAndGetters() {
        assertEquals(1, recipe.getId());
        assertEquals("Tomato Soup", recipe.getTitle());
        assertEquals("http://example.com/tomato_soup.png", recipe.getImage());
        assertTrue(recipe.getUsedIngredients().contains(usedIngredient));
        assertTrue(recipe.getMissedIngredients().contains(missedIngredient));
    }

    @Test
    void testSetters() {
        recipe.setId(2);
        recipe.setTitle("Potato Soup");
        recipe.setImage("http://example.com/potato_soup.png");

        assertEquals(2, recipe.getId());
        assertEquals("Potato Soup", recipe.getTitle());
        assertEquals("http://example.com/potato_soup.png", recipe.getImage());
    }

    @Test
    void testEquals() {
        Recipe anotherRecipe = new Recipe(1, "Chicken Soup", "http://example.com/chicken_soup.png");

        assertTrue(recipe.equals(anotherRecipe));
    }
    void testAddAndSetIngredients() {
        // Test adding ingredients
        Ingredient newUsedIngredient = new Ingredient(3, "Carrot", 1.0, "kg", "http://example.com/carrot.png", "1 kg of Carrots");
        Ingredient newMissedIngredient = new Ingredient(4, "Onion", 2, "pcs", "http://example.com/onion.png", "2 pcs of Onions");

        recipe.addUsedIngredient(newUsedIngredient);
        recipe.addMissedIngredient(newMissedIngredient);

        assertTrue(recipe.getUsedIngredients().contains(newUsedIngredient));
        assertTrue(recipe.getMissedIngredients().contains(newMissedIngredient));

        // Test setting ingredients
        List<Ingredient> newUsedIngredients = List.of(
                new Ingredient(5, "Pepper", 0.5, "kg", "http://example.com/pepper.png", "0.5 kg of Pepper")
        );
        List<Ingredient> newMissedIngredients = List.of(
                new Ingredient(6, "Salt", 0.1, "kg", "http://example.com/salt.png", "0.1 kg of Salt")
        );

        recipe.setUsedIngredients(newUsedIngredients);
        recipe.setMissedIngredients(newMissedIngredients);

        assertEquals(newUsedIngredients, recipe.getUsedIngredients());
        assertEquals(newMissedIngredients, recipe.getMissedIngredients());
    }

    @Test
    void testToString() {
        String expectedString = "Recipe{id=1, title='Tomato Soup', image='http://example.com/tomato_soup.png', usedIngredients=[Ingredient{id=1, name='Tomato', amount=2.5, unit='kg', image='http://example.com/tomato.png', original='2.5 kg of Tomatoes'}], missedIngredients=[Ingredient{id=2, name='Potato', amount=3.0, unit='pcs', image='http://example.com/potato.png', original='3 pcs of Potatoes'}]}";
        assertEquals(expectedString, recipe.toString());
    }
}