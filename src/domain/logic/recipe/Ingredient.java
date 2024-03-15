package domain.logic.recipe;

public class Ingredient {
    private int id;
    private String name;
    private double amount;

    private String unit;
    private String image;
    private String original;

    public Ingredient(int id, String name, double amount, String unit, String image, String original) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.image = image;
        this.original = original;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String fullName) {
        this.original = fullName;
    }
}