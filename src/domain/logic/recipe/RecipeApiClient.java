package domain.logic.recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * The client for accessing the recipe API using Spoonacular's API.
 */
public class RecipeApiClient {
    private String apiKey;
    private Gson gson;

    /**
     * Constructs a RecipeApiClient instance with the provided API key.
     *
     * @param apiKey the API key for Spoonacular's API
     */
    public RecipeApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.gson = new Gson();
    }

    /**
     * Finds recipes by ingredients using Spoonacular API.
     *
     * @param ingredients      a comma-separated list of ingredients
     * @param numberOfRecipes  the number of recipes to return
     * @return a list of Recipe objects
     */
    public List<Recipe> findRecipesByIngredients(String ingredients, int numberOfRecipes) {
        String urlString = String.format(
                "https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&number=%d&apiKey=%s",
                ingredients, numberOfRecipes, apiKey
        );

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Type listType = new TypeToken<List<Recipe>>(){}.getType();
            return gson.fromJson(response.toString(), listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null in case of failure
    }
}
