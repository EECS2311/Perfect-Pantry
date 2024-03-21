package gui;

import domain.logic.recipe.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeDetailView extends JPanel implements ActionListener {
    private Recipe recipe;
    private JButton backButton = new JButton("Back");

    JEditorPane detailsArea = new JEditorPane();
    JScrollPane scrollPane;

    private static RecipeDetailView instance;

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
    }

    private void updateDetailsArea() {
        if (recipe != null) {
            StringBuilder htmlContent = new StringBuilder("<html><head><style>body { font-family: Arial, sans-serif; }</style></head><body>");

            // Title
            htmlContent.append("<h1>").append(recipe.getTitle()).append("</h1>");

            // Image
            htmlContent.append("<img src='").append(recipe.getImage()).append("' style='width: 200px; height: auto;'><br>");

            // Ingredients
//            htmlContent.append("<h2>Ingredients:</h2><ul>");
            htmlContent.append("<h3>Available Ingredients:</h3><ul>");
            recipe.getUsedIngredients().forEach(ingredient ->
                    htmlContent.append("<li>").append(ingredient.getOriginal()).append("</li>")
            );
            if (!recipe.getMissedIngredients().isEmpty()) {
                htmlContent.append("<h3>Missing Ingredients:</h3><ul>");
                recipe.getMissedIngredients().forEach(ingredient ->
                        htmlContent.append("<li>").append(ingredient.getOriginal()).append("</li>")
                );
            }

            // Instructions
            htmlContent.append("<h2>Instructions:</h2><ol>");
            recipe.getDetailedInstructions().forEach((step, instruction) ->
                    htmlContent.append("<li>").append(instruction).append("</li>")
            );
            htmlContent.append("</ol></body></html>");

            detailsArea.setText(htmlContent.toString());
        }
    }


    public static RecipeDetailView getInstance(Recipe recipe) {
        if (instance == null) {
            instance = new RecipeDetailView(recipe);
        }
        return instance;
    }

    public static RecipeDetailView getInstance() {
        return instance;
    }

    public void setTextArea(Recipe recipe) {
        System.out.println("Updating RecipeDetailView with new recipe");
        this.recipe = recipe; // Update the recipe instance

        updateDetailsArea();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
//            RecipeListView.showListView();
            HomeView.getHomeView().setHomeViewVisibility(true);
            setRecipeDetailViewVisibility(false);
            HomeView.getFrame().remove(this);
        }
    }


    public void setRecipeDetailViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame(); // Get the main frame
        if (visible) {
            frame.getContentPane().removeAll(); // Clear the frame's content pane
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