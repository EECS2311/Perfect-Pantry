package domain.logic.recipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String title;

    private String image;
    private List<Ingredient> usedIngredients;
    private List<Ingredient> missedIngredients;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public void setMissedIngredients(List<Ingredient> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    public List<Ingredient> getMissedIngredients() {
        return missedIngredients;
    }
}
