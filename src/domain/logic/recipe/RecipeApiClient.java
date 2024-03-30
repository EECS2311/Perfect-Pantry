package domain.logic.recipe;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The client for accessing the recipe API using Spoonacular's API.
 */
public class RecipeApiClient {
    private static String apiKey = SpoonacularApi.SpoonacularApiKey;
    private static Gson gson = new Gson();

    /**
     * Sets the API key for all Spoonacular API requests.
     *
     * @param newApiKey the API key to set
     */
    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
    }

    /**
     * Finds recipes by ingredients using Spoonacular API.
     *
     * @param ingredients      a comma-separated list of ingredients
     * @param numberOfRecipes  the number of recipes to return
     * @return a list of Recipe objects
     */
    public static List<Recipe> findRecipesByIngredients(String ingredients, int numberOfRecipes) throws IOException, RateLimitPerMinuteExceededException, DailyLimitExceededException {
        String urlString = String.format(
                "https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&number=%d&apiKey=%s",
                ingredients, numberOfRecipes, apiKey
        );

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            checkResponseCode(connection);
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

        return null;
    }

    /**
     * Retrieves the analyzed recipe instructions from Spoonacular API.
     *
     * @param recipeId the unique identifier of the recipe
     * @return a map of steps for the recipe
     */
    public static Map<Integer, String> getRecipeInstructions(int recipeId) throws IOException {
        String urlString = String.format(
                "https://api.spoonacular.com/recipes/%d/analyzedInstructions?stepBreakdown=true&apiKey=%s",
                recipeId, apiKey
        );

        Map<Integer, String> stepsMap = new HashMap<>();

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

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(response.toString());
            if (element.isJsonArray()) {
                JsonArray instructionsArray = element.getAsJsonArray();
                for (JsonElement instructionElement : instructionsArray) {
                    JsonObject instructionObject = instructionElement.getAsJsonObject();
                    if (instructionObject.has("steps") && instructionObject.get("steps").isJsonArray()) {
                        JsonArray stepsArray = instructionObject.get("steps").getAsJsonArray();
                        for (JsonElement stepElement : stepsArray) {
                            JsonObject stepObject = stepElement.getAsJsonObject();
                            int number = stepObject.get("number").getAsInt();
                            String step = stepObject.get("step").getAsString();
                            stepsMap.put(number, step);
                        }
                    }
                }
            }


        return stepsMap;
    }

    private static void checkResponseCode(HttpURLConnection connection) throws RateLimitPerMinuteExceededException, DailyLimitExceededException, IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == 429) { // 429 is the typical rate limit error code
            throw new RateLimitPerMinuteExceededException();
        } else if (responseCode == 402) { // Assuming 403 for daily limit exceeded
            throw new DailyLimitExceededException();
        }
    }

}
