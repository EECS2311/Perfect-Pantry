package domain.logic.recipe;

import domain.logic.recipe.Recipe;
import domain.logic.recipe.Ingredient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeApiClient {
    private String apiKey;

    public RecipeApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Recipe> findRecipesByIngredients(String ingredients, int numberOfRecipes) {
        String urlString = String.format(
                "https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&number=%d&apiKey=%s",
                ingredients, numberOfRecipes, apiKey
        );

        List<Recipe> recipes = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Extract each recipe's JSON string
            String recipesStr = response.toString().replaceAll("\\},\\{", "},splitHere{");
            String[] recipeStrings = recipesStr.split("splitHere");
            for (String recipeStr : recipeStrings) {
                int id = Integer.parseInt(extractValue(recipeStr, "\"id\":(\\d+),"));
                String title = extractValue(recipeStr, "\"title\":\"([^\"]+)\"");
                String image = extractValue(recipeStr, "\"image\":\"([^\"]+)\"");

                Recipe recipe = new Recipe(id, title, image);

                // Extract used ingredients
                extractIngredients(recipeStr, "\"usedIngredients\":\\[(.*?)\\]", true, recipe);

                // Extract missed ingredients
                extractIngredients(recipeStr, "\"missedIngredients\":\\[(.*?)\\]", false, recipe);

                recipes.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }

    private void extractIngredients(String recipeStr, String patternStr, boolean isUsed, Recipe recipe) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(recipeStr);
        if (matcher.find()) {
            String ingredientsStr = matcher.group(1);
            Pattern ingredientPattern = Pattern.compile("\\{(.*?)\\}");
            Matcher ingredientMatcher = ingredientPattern.matcher(ingredientsStr);
            while (ingredientMatcher.find()) {
                String ingredientStr = ingredientMatcher.group(1);

                int id = Integer.parseInt(extractValue(ingredientStr, "\"id\":(\\d+),"));
                String name = extractValue(ingredientStr, "\"name\":\"([^\"]+)\"");
                double amount = Double.parseDouble(extractValue(ingredientStr, "\"amount\":(\\d+(\\.\\d+)?),"));
                String unit = extractValue(ingredientStr, "\"unit\":\"([^\"]+)\"");
                String image = extractValue(ingredientStr, "\"image\":\"([^\"]+)\"");
                String original = extractValue(ingredientStr, "\"original\":\"([^\"]+)\"");

                Ingredient ingredient = new Ingredient(id, name, amount, unit, image, original);

                if (isUsed) {
                    recipe.addUsedIngredient(ingredient);
                } else {
                    recipe.addMissedIngredient(ingredient);
                }
            }
        }
    }

    private String extractValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1).trim(); // Use trim() to remove any leading or trailing whitespace
        }
        return ""; // Return an empty string if no match is found
    }
}