package domain.logic.recipe;

public class Ingredient {
    int id;
    String name;
    double amount;
    String unit;
    String image;
    String fullName;

    public Ingredient(int id, String name, double amount, String unit, String image, String original) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.image = image;
        this.fullName = original;
    }
}