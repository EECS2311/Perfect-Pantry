package domain.logic.recipe;

import gui.HomeView;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Utility class for managing recipes, including checking for ingredient updates and finding recipes lazily.
 */
public class RecipeUtility {

    /**
     * Checks if the set of near expiry ingredients has changed compared to the current ingredients
     * and updates the ingredient set if there are changes.
     *
     * @param ingredients The set of ingredients to check and update.
     * @return true if the ingredients set was updated with new items, false if no changes were made.
     */
    public static boolean isNearExpiryItemsChanged(Set<String> ingredients) {
        Set<String> currentIngredients = HomeView.data.getNearExpiryOrFreshItemNames();
        if (!currentIngredients.equals(ingredients)) {
            ingredients.clear();
            ingredients.addAll(currentIngredients);
            return true;
        }
        return false;
    }

    /**
     * Finds recipes lazily based on a set of ingredients. If the non-expired items have changed,
     * it queries the external Recipe API for new recipes based on these ingredients and updates
     * the provided `recipes` list; otherwise, it retains the cached recipes in the `recipes` list.
     *
     * @param ingredients A set of ingredient names (non-null) to be used for finding recipes.
     * @param recipes The current list of recipes (cached). This list will be cleared and updated
     *                with new recipes if the non-expired items have changed since the last query.
     *                The list should not be null, but it can be empty.
     * @throws RateLimitPerMinuteExceededException If the rate limit for querying the Recipe API
     *                                             per minute is exceeded.
     * @throws DailyLimitExceededException If the daily limit for querying the Recipe API is exceeded.
     * @throws IOException If an I/O error occurs while communicating with the Recipe API.
     */
    public static void findRecipesLazyLoad(Set<String> ingredients, List<Recipe> recipes) throws RateLimitPerMinuteExceededException, IOException, DailyLimitExceededException {
        if(isNearExpiryItemsChanged(ingredients)){
            String ingredientsString = String.join(", ", ingredients);
            List<Recipe> newRecipes = RecipeApiClient.findRecipesByIngredients(ingredientsString, 5);

            recipes.clear();
            recipes.addAll(newRecipes);
        }
    }

    /**
     * Attempts to save a recipe to the database if it does not already exist.
     *
     * This method checks whether a recipe with the same ID already exists in the database.
     * If the recipe is new, it saves the recipe to the database and returns true.
     * If the recipe already exists, it does not perform any operation and returns false.
     *
     * @param recipe The Recipe object to save to the database.
     * @return true if the recipe was successfully saved (i.e., it did not exist in the database),
     *         false if the recipe already exists in the database and was not saved.
     */
    public static boolean verifySaveRecipeToDatabase(Recipe recipe){
        boolean isRecipeExists = RecipeUtility.isRecipeInDatabase(recipe.getId());
        if (!isRecipeExists) {
            HomeView.data.saveRecipeToDatabase(recipe);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a recipe with a specific ID exists in the database.
     *
     * This method queries the database to determine whether a recipe with the given ID
     * is already stored. It is used internally to avoid duplicating recipes in the database.
     *
     * @param recipeId The unique identifier of the recipe to check in the database.
     * @return true if the recipe exists in the database, false otherwise.
     */
    private static boolean isRecipeInDatabase(int recipeId) {
        return HomeView.data.isRecipeInDatabase(recipeId);
    }

}
