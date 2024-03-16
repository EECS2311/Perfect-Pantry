package domain.logic.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a recipe with an ID, title, image URL, and lists of used and missed ingredients.
 */
public class Recipe {

    private int id;

    private String title;

    private String image;
    private List<Ingredient> usedIngredients;
    private List<Ingredient> missedIngredients;

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
    }

    public void addUsedIngredient(Ingredient ingredient) {
        this.usedIngredients.add(ingredient);
    }

    public void addMissedIngredient(Ingredient ingredient) {
        this.missedIngredients.add(ingredient);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public List<Ingredient> getMissedIngredients() {
        return missedIngredients;
    }

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
