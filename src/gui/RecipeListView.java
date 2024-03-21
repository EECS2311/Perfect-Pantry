package gui;

import domain.logic.recipe.Ingredient;
import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeApiClient;
import domain.logic.recipe.RecipeUtility;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class RecipeListView extends JPanel implements ActionListener {
    private static RecipeListView instance;
    private JButton backButton = new JButton("Back to Home");
    private JPanel recipesPanel = new JPanel();

    private static Recipe wow = new Recipe(640352, "Cranberry Apple Crisp", "https://spoonacular.com/recipeImages/640352-312x231.jpg");
    private static Set<String> ingredients = new HashSet<>();
    private static List<Recipe> recipes = new ArrayList<>();

    JScrollPane scrollPane;

    private static RecipeDetailView recipeDetailView = new RecipeDetailView(wow);


    private RecipeListView() {
        setLayout(new BorderLayout());
        backButton.addActionListener(this);
        add(backButton, BorderLayout.NORTH);
        recipesPanel.setLayout(new GridLayout(0, 1)); // Dynamic grid layout, 1 column
        scrollPane = new JScrollPane(recipesPanel);
        add(scrollPane, BorderLayout.CENTER);
        recipeDetailView.setPreferredSize(new Dimension(600, 400)); // Example size, adjust as needed

        RecipeUtility.findRecipesLazyLoad(ingredients, recipes);
        displayRecipes();
    }

    public static RecipeListView getInstance() {
        if (instance == null) {
            instance = new RecipeListView();
        }
        return instance;
    }

    private void displayRecipes() {
        recipesPanel.removeAll(); // Clear the panel before adding new components

        for (Recipe recipe : recipes) {
            JPanel recipePanel = new JPanel(new BorderLayout(5, 0)); // Add some horizontal spacing between components

            // Placeholder label for the image
            JLabel imageLabel = new JLabel("Loading image...");
            // Load image in the background
            new SwingWorker<ImageIcon, Void>() {
                @Override
                protected ImageIcon doInBackground() throws Exception {
                    URL imageUrl = new URL(recipe.getImage());
                    Image image = ImageIO.read(imageUrl).getScaledInstance(312, 231, Image.SCALE_SMOOTH);
                    return new ImageIcon(image);
                }

                @Override
                protected void done() {
                    try {
                        ImageIcon imageIcon = get();
                        imageLabel.setIcon(imageIcon);
                        imageLabel.setText(""); // Remove the "Loading image..." text
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        imageLabel.setText("Failed to load image");
                    }
                }
            }.execute();

            recipePanel.add(imageLabel, BorderLayout.WEST);
            recipePanel.setBackground(new Color(245, 223, 162));

            // Convert ingredient lists to HTML list format
            String usedIngredientsList = recipe.getUsedIngredients().stream()
                    .map(ingredient -> "<li>" + ingredient.getName() + "</li>")
                    .reduce("", (a, b) -> a + b);
            if (usedIngredientsList.isEmpty()) usedIngredientsList = "<li>No used ingredients</li>";

            String missedIngredientsList = recipe.getMissedIngredients().stream()
                    .map(ingredient -> "<li>" + ingredient.getName() + "</li>")
                    .reduce("", (a, b) -> a + b);
            if (missedIngredientsList.isEmpty()) missedIngredientsList = "<li>No missed ingredients</li>";

            JPanel detailsPanel = new JPanel(new BorderLayout());
            detailsPanel.setBackground(new Color(245, 223, 162));

            // use HTML for list formatting
            JButton recipeButton = new JButton("<html><body style='text-align:left;'>"
                    + "<h3>" + recipe.getTitle()+ "</h3>"
                    + "<br><b>Used Ingredients:</b> <ul>" + usedIngredientsList + "</ul>"
                    + "<br><b>Missed Ingredients:</b> <ul>" + missedIngredientsList + "</ul>"
                    + "</body></html>");
            recipeButton.setHorizontalAlignment(SwingConstants.LEFT);
            recipeButton.setFocusable(false);
            recipeButton.addActionListener(e -> showRecipeDetails(recipe));

            detailsPanel.add(recipeButton, BorderLayout.CENTER);

            recipePanel.add(detailsPanel, BorderLayout.CENTER);

            recipesPanel.add(recipePanel);
        }

        recipesPanel.revalidate(); // Revalidate to ensure layout managers redo the layout
        recipesPanel.repaint(); // Repaint to show the updated UI

        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void showRecipeDetails(Recipe recipe) {
        SwingUtilities.invokeLater(() -> {
            recipeDetailView.setRecipeDetailViewVisibility(true); // Show the RecipeDetailView first
            recipeDetailView.setTextArea(recipe);
        });
    }
    public static void showListView() {
        // Show the list view and hide the RecipeDetailView
        if (instance != null) {
            instance.setRecipeListViewVisibility(true);
        }
        if (recipeDetailView != null) {
            recipeDetailView.setRecipeDetailViewVisibility(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            HomeView.getHomeView().setHomeViewVisibility(true);
            setRecipeListViewVisibility(false);
            HomeView.getFrame().remove(this); // Remove RecipeListView from the frame
        }
    }
    public void setRecipeListViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame(); // Get the main frame
        if (visible) {
            frame.getContentPane().removeAll(); // Clear the frame's content pane
            RecipeListView recipeListView = RecipeListView.getInstance();

            //Add all panels
            HomeView.getFrame().add(recipeListView);

            // Refresh
            RecipeUtility.findRecipesLazyLoad(ingredients, recipes);
            recipeListView.displayRecipes();

        } else {
            frame.getContentPane().removeAll();
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();
    }
}