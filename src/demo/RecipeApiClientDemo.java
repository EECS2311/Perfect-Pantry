package demo;

import domain.logic.recipe.Ingredient;
import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeApiClient;
import domain.logic.recipe.SpoonacularApi;

import java.util.List;

public class RecipeApiClientDemo {
    public static void main(String[] args) {
        // Example: Searching for recipes with apples,sugar,flour
        String ingredients = "apples,sugar,flour";
        int numberOfRecipes = 5; // Number of recipes you want to retrieve

        List<Recipe> recipes = RecipeApiClient.findRecipesByIngredients(ingredients, numberOfRecipes);

        // Displaying the recipes and their ingredients
        if(recipes != null){
            for (Recipe recipe : recipes) {
                System.out.println("Recipe ID: " + recipe.getId() + ", Title: " + recipe.getTitle() + ", Image: " + recipe.getImage());
                System.out.println("Used Ingredients:");
                for (Ingredient ingredient : recipe.getUsedIngredients()) {
                    System.out.println(" - " + ingredient.getName() + ": " + ingredient.getAmount() + " " + ingredient.getUnit());
                }
                System.out.println("Missed Ingredients:");
                for (Ingredient ingredient : recipe.getMissedIngredients()) {
                    System.out.println(" - " + ingredient.getName() + ": " + ingredient.getAmount() + " " + ingredient.getUnit());
                }
                System.out.println("--------------------------------");
            }

            System.out.println(recipes.get(0).getDetailedInstructions());
        }
    }
}