package domain.logic.recipe;

import gui.HomeView;

import java.util.List;
import java.util.Set;

public class RecipeUtility {
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
     * Finds recipes lazily based on a set of ingredients. If the near expiry items have changed,
     * it queries for new recipes based on these ingredients; otherwise, it retains the cached recipes.
     *
     * @param ingredients A set of ingredient names.
     * @param recipes The current list of recipes (cached).
     */
    public static void findRecipesLazyLoad(Set<String> ingredients, List<Recipe> recipes){
        if(isNearExpiryItemsChanged(ingredients)){
            String ingredientsString = String.join(", ", ingredients);
            System.out.println(ingredientsString);
            // Retrieve new recipes based on the updated ingredients
            List<Recipe> newRecipes = RecipeApiClient.findRecipesByIngredients(ingredientsString, 2);

            // Clear the original list and add all the new recipes to it
            recipes.clear();
            recipes.addAll(newRecipes);
        }
        // If the ingredients have not changed, the original recipes list remains unchanged
    }


}
