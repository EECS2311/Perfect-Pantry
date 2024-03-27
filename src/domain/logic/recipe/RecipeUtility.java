package domain.logic.recipe;

import gui.HomeView;

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
            // Clear the original set and add all the new ingredients
            ingredients.clear();
            ingredients.addAll(currentIngredients);
            return true; // Indicates the set was updated
        }
        return false; // Indicates no update was needed
    }

    /**
     * Finds recipes lazily based on a set of ingredients. If the non-expired items have changed,
     * it queries for new recipes based on these ingredients; otherwise, it retains the cached recipes.
     *
     * @param ingredients A set of ingredient names.
     * @param recipes The current list of recipes (cached).
     */
    public static void findRecipesLazyLoad(Set<String> ingredients, List<Recipe> recipes){
        if(isNearExpiryItemsChanged(ingredients)){
            String ingredientsString = String.join(", ", ingredients);
            // Retrieve new recipes based on the updated ingredients
            List<Recipe> newRecipes = RecipeApiClient.findRecipesByIngredients(ingredientsString, 5);

            recipes.clear();
            recipes.addAll(newRecipes);
        }
        // If the ingredients have not changed, the original recipes list remains unchanged
    }

    public static void saveRecipeToDatabase(Recipe recipe){
        HomeView.data.saveRecipeToDatabase(recipe);
    }

    public static boolean isRecipeInDatabase(int recipeId) {
        return HomeView.data.isRecipeInDatabase(recipeId);
    }

}
