package demo;

import java.io.IOException;
import java.util.List;

import domain.logic.recipe.*;

public class RecipeApiClientDemo {
    public static void main(String[] args) {
        // Example: Searching for recipes with apples,sugar,flour
        String ingredients = "apples,sugar,flour";
        int numberOfRecipes = 5; // Number of recipes you want to retrieve

        List<Recipe> recipes = null;
        try {
            recipes = RecipeApiClient.findRecipesByIngredients(ingredients, numberOfRecipes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RateLimitPerMinuteExceededException e) {
            throw new RuntimeException(e);
        } catch (DailyLimitExceededException e) {
            throw new RuntimeException(e);
        }

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

            try {
                System.out.println(recipes.get(0).getDetailedInstructions());
            } catch (RateLimitPerMinuteExceededException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (DailyLimitExceededException e) {
                throw new RuntimeException(e);
            }
        }
    }
}