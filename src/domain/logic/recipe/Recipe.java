package domain.logic.recipe;

import java.util.ArrayList;
import java.util.List;

class Recipe {
    int id;
    String title;
    String image;
    List<Ingredient> usedIngredients;
    List<Ingredient> missedIngredients;

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
}
