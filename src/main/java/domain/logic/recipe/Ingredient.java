package main.java.domain.logic.recipe;

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

    /**
     * Gets the unique identifier of the ingredient.
     *
     * @return the ingredient ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the ingredient.
     *
     * @param id the ingredient ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the ingredient.
     *
     * @return the ingredient name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ingredient.
     *
     * @param name the ingredient name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the amount of the ingredient.
     *
     * @return the ingredient amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the ingredient.
     *
     * @param amount the ingredient amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the unit of measurement for the ingredient's amount.
     *
     * @return the unit of measurement
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of measurement for the ingredient's amount.
     *
     * @param unit the unit of measurement to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the URL to the image of the ingredient.
     *
     * @return the URL of the ingredient's image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL to the image of the ingredient.
     *
     * @param image the URL of the ingredient's image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the original string of the ingredient details.
     *
     * @return the original string
     */
    public String getOriginal() {
        return original;
    }

    /**
     * Sets the original string of the ingredient details.
     *
     * @param original the original string to set
     */
    public void setOriginal(String original) {
        this.original = original;
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