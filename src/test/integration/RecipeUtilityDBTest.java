package test.integration;

import domain.logic.recipe.*;
import database.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeUtilityDBTest {
    private static DB database;

    @BeforeAll
    static void setupClass() {
        database = new DB();
        database.init();
    }

    @BeforeEach
    void setUp() {
         database.clearRecipesTable();
    }

    @AfterEach
    void tearDown() {
         database.clearRecipesTable();
    }

    @Test
    void testFindRecipesLazyLoadAndSave() throws IOException, RateLimitPerMinuteExceededException, DailyLimitExceededException {
        Set<String> ingredients = new HashSet<>();
        ingredients.add("chicken");
        List<Recipe> recipes = new ArrayList<>();

        RecipeUtility.findRecipesLazyLoad(ingredients, recipes);

        assertNotNull(recipes, "The recipes list should not be null");
        assertFalse(recipes.isEmpty(), "Recipes list should not be empty after fetching");

        boolean saved = RecipeUtility.verifySaveRecipeToDatabase(recipes.get(0), database);
        assertTrue(saved, "The first recipe should be saved to the database");

        saved = RecipeUtility.verifySaveRecipeToDatabase(recipes.get(0), database);
        assertFalse(saved, "The recipe should not be saved again to the database");
    }

    @Test
    void testIsNearExpiryItemsChanged() {
        Set<String> ingredients = new HashSet<>();
        // Assuming 'database.getNearExpiryOrFreshItemNames()' returns a set of ingredients including "milk"
        ingredients.add("milk");

        boolean isChanged = RecipeUtility.isNearExpiryItemsChanged(ingredients);
        assertTrue(isChanged, "Ingredients set should be updated with new items");
    }
}
