package gui;

import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeUtility;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A GUI component that displays detailed view of a recipe, including ingredients, instructions, and an image.
 */
public class RecipeDetailView extends JPanel implements ActionListener {
    private Recipe recipe;
    private JButton backButton = new JButton("Back");

    private JEditorPane detailsArea = new JEditorPane();
    private JScrollPane scrollPane;

    private static RecipeDetailView instance;

    private JButton starRecipeButton = new JButton("Star Recipe");


    /**
     * Private constructor to initialize the detail view with a recipe.
     * @param recipe The recipe to display.
     */
    private RecipeDetailView(Recipe recipe) {
        this.recipe = recipe;
        setLayout(new BorderLayout());
        backButton.addActionListener(this);
        add(backButton, BorderLayout.NORTH);
        detailsArea.setEditable(false);
        detailsArea.setBackground(new Color(253, 241, 203));
        detailsArea.setContentType("text/html"); // Set content type to HTML

        scrollPane = new JScrollPane(detailsArea);
        add(scrollPane, BorderLayout.CENTER);
        starRecipeButton.addActionListener(this);
        add(starRecipeButton, BorderLayout.SOUTH);
    }

    /**
     * Returns an instance of RecipeDetailView, creating it if necessary.
     * @param recipe The recipe to display in the detail view.
     * @return The singleton instance of RecipeDetailView.
     */
    public static RecipeDetailView getInstance(Recipe recipe) {
        if (instance == null) {
            instance = new RecipeDetailView(recipe);
        }
        return instance;
    }

    /**
     * Returns the instance of RecipeDetailView.
     * @return The instance of RecipeDetailView.
     */
    public static RecipeDetailView getInstance() {
        return instance;
    }

    /**
     * Updates the detail area with the recipe information.
     */
    private void updateDetailsArea() {
        if (recipe != null) {
            StringBuilder htmlContent = new StringBuilder("<html><head><style>body { font-family: Arial, sans-serif; }</style></head><body>");

            // Title
            htmlContent.append("<h1>").append(recipe.getTitle()).append("</h1>");

            // Image
            htmlContent.append("<img src='").append(recipe.getImage()).append("' style='width: 200px; height: auto;'><br>");

            // Ingredients
            htmlContent.append("<h3>Available Ingredients:</h3><ul>");
            recipe.getUsedIngredients().forEach(ingredient -> htmlContent.append("<li>").append(ingredient.getOriginal()).append("</li>"));
            if (!recipe.getMissedIngredients().isEmpty()) {
                htmlContent.append("<h3>Missing Ingredients:</h3><ul>");
                recipe.getMissedIngredients().forEach(ingredient -> htmlContent.append("<li>").append(ingredient.getOriginal()).append("</li>"));
            }

            // Instructions
            htmlContent.append("<h2>Instructions:</h2><ol>");
            recipe.getDetailedInstructions().forEach((step, instruction) -> htmlContent.append("<li>").append(instruction).append("</li>"));
            htmlContent.append("</ol></body></html>");

            detailsArea.setText(htmlContent.toString());
        }
    }

    /**
     * Sets the recipe to be displayed in the text area and updates the view.
     * @param recipe The recipe to display.
     */
    public void setTextArea(Recipe recipe) {
        this.recipe = recipe; // Update the recipe instance
        updateDetailsArea();
        this.revalidate();
        this.repaint();
    }

    /**
     * Handles action events for the component, such as button clicks.
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
//          RecipeListView.showListView();
            HomeView.getHomeView().setHomeViewVisibility(true);
            setRecipeDetailViewVisibility(false);
            HomeView.getFrame().remove(this);
        }
        else if (e.getSource() == starRecipeButton) {
            boolean isRecipeExists = RecipeUtility.isRecipeInDatabase(recipe.getId());
            if (!isRecipeExists) {
                saveRecipeToDatabase(recipe);
            } else {
                JOptionPane.showMessageDialog(this, "This recipe is already saved.");
            }
        }
    }

    /**
     * Saves the provided recipe to the database.
     * @param recipe The recipe to be saved.
     */
    private void saveRecipeToDatabase(Recipe recipe) {
        RecipeUtility.saveRecipeToDatabase(recipe);
    }

    /**
     * Sets the visibility of the RecipeDetailView.
     * @param visible True to make the view visible, false to hide it.
     */
    public void setRecipeDetailViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame();
        if (visible) {
            frame.getContentPane().removeAll();
            RecipeDetailView recipeDetailView = RecipeDetailView.getInstance();

            HomeView.getFrame().add(recipeDetailView);
        } else {
            frame.getContentPane().removeAll();
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();

    }

}