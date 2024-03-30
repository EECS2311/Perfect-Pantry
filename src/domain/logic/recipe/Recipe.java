package domain.logic.recipe;

import java.io.IOException;
import java.util.*;

/**
 * Represents a recipe with an ID, title, image URL, and lists of used and missed ingredients.
 */
public class Recipe {
    private int id;
    private String title;
    private String image;
    private List<Ingredient> usedIngredients;
    private List<Ingredient> missedIngredients;
    private Map<Integer, String> detailedInstructions;
    private boolean fetchedStep = false;

    public void setFetchedStep(boolean fetchedStep){
        this.fetchedStep = fetchedStep;
    }

    public boolean getFetchedStep(){
        return this.fetchedStep;
    }
    /**
     * Constructs a Recipe instance with specified details.
     *
     * @param id    the unique identifier of the recipe
     * @param title the title of the recipe
     * @param image the URL to the image of the recipe
     */
    public Recipe(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.usedIngredients = new ArrayList<>();
        this.missedIngredients = new ArrayList<>();
        this.detailedInstructions = new HashMap<>();
    }

    /**
     * Lazily retrieves the analyzed recipe instructions when requested by the user.
     * The method does not fetch the instructions until this method is called, to respect API rate limits.
     *
     * @return a map of step numbers to instruction descriptions
     */
    public Map<Integer, String> getDetailedInstructions() throws RateLimitPerMinuteExceededException, IOException, DailyLimitExceededException {
        if (!fetchedStep) {
            detailedInstructions = RecipeApiClient.getRecipeInstructions(this.id);
            fetchedStep = true;
        }
        return detailedInstructions;
    }

    public void setDetailedInstructions(Map<Integer, String> detailedInstructions){
        this.detailedInstructions = detailedInstructions;
    }

    /**
     * Adds an ingredient to the list of used ingredients in the recipe.
     *
     * @param ingredient the ingredient to add
     */
    public void addUsedIngredient(Ingredient ingredient) {
        this.usedIngredients.add(ingredient);
    }

    /**
     * Adds an ingredient to the list of missed ingredients in the recipe.
     *
     * @param ingredient the ingredient to add
     */
    public void addMissedIngredient(Ingredient ingredient) {
        this.missedIngredients.add(ingredient);
    }

    /**
     * Gets the unique identifier of the recipe.
     *
     * @return the recipe ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the recipe.
     *
     * @param id the recipe ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the recipe.
     *
     * @return the recipe title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the recipe.
     *
     * @param title the recipe title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the URL to the image of the recipe.
     *
     * @return the URL of the recipe's image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL to the image of the recipe.
     *
     * @param image the URL of the recipe's image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the list of used ingredients in the recipe.
     *
     * @return the list of used ingredients
     */
    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    /**
     * Sets the list of used ingredients in the recipe.
     *
     * @param usedIngredients the list of used ingredients to set
     */
    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    /**
     * Gets the list of missed ingredients in the recipe.
     *
     * @return the list of missed ingredients
     */
    public List<Ingredient> getMissedIngredients() {
        return missedIngredients;
    }

    /**
     * Sets the list of missed ingredients in the recipe.
     *
     * @param missedIngredients the list of missed ingredients to set
     */
    public void setMissedIngredients(List<Ingredient> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", usedIngredients=" + usedIngredients +
                ", missedIngredients=" + missedIngredients +
                '}';
    }
}
