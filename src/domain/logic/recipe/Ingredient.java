package domain.logic.recipe;

import java.util.Objects;

/**
 * Represents an ingredient with detailed information such as ID, name, amount, unit, image URL, and the original string.
 */
public class Ingredient {

    private int id;
    private String name;
    private double amount;

    private String unit;
    private String image;
    private String original;

    /**
     * Constructs an Ingredient instance with specified details.
     *
     * @param id       the unique identifier of the ingredient
     * @param name     the name of the ingredient
     * @param amount   the quantity of the ingredient
     * @param unit     the unit of measurement for the quantity
     * @param image    the URL to the image of the ingredient
     * @param original the original string of the ingredient details
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", image='" + image + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}