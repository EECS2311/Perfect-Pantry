package domain.logic.recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RecipeApiClient {
    private String apiKey;
    private Gson gson;

    public RecipeApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.gson = new Gson(); // Instantiate Gson
    }

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
        return null; // Return null or an empty list in case of failure
    }
}
