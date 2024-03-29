package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeUtility;

/**
 * A panel that displays a list of recipes, allowing users to browse through them.
 * It includes functionality to display recipe details and navigate back to the home view.
 */
public class RecipeListView extends JPanel implements ActionListener {
    protected static RecipeListView instance;
    protected JButton backButton = new JButton("Back to Home");
    protected JPanel recipesPanel = new JPanel();
    protected static Recipe wow = new Recipe(640352, "Cranberry Apple Crisp", "https://spoonacular.com/recipeImages/640352-312x231.jpg");
    protected static Set<String> ingredients = new HashSet<>();
    private List<Recipe> recipes = new ArrayList<>(); // Instance variable
    protected JScrollPane scrollPane;
    protected static RecipeDetailView recipeDetailView = RecipeDetailView.getInstance(wow);

    /**
     * constructor for initializing the RecipeListView panel with a back button,
     * a panel for recipes, and a scroll pane.
     */
    protected RecipeListView() {
        setLayout(new BorderLayout());
        backButton.addActionListener(this);
        add(backButton, BorderLayout.NORTH);
        recipesPanel.setLayout(new GridLayout(0, 1)); // Dynamic grid layout, 1 column
        scrollPane = new JScrollPane(recipesPanel);
        add(scrollPane, BorderLayout.CENTER);
        recipeDetailView.setPreferredSize(new Dimension(600, 400)); // Example size, adjust as needed

        displayRecipes();
    }

    /**
     * Provides access to the instance of RecipeListView, creating it if it does not already exist.
     *
     * @return The instance of RecipeListView.
     */
    public static RecipeListView getInstance() {
        if (instance == null) {
            instance = new RecipeListView();
        }
        return instance;
    }

    /**
     * Displays recipes in the panel, fetching and updating recipe details.
     * If the recipes list is empty, displays a message encouraging the user to add ingredients to their pantry.
     */
    protected void displayRecipes() {
        recipesPanel.removeAll(); // Clear the panel before adding new components
        if (recipes.isEmpty()) {
            // Display a message when there are no recipes
            JLabel emptyMessageLabel = new JLabel("<html><center>Start adding non-expire food to your pantry to see recipes.</center></html>");
            emptyMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            recipesPanel.setLayout(new BorderLayout());
            recipesPanel.add(emptyMessageLabel, BorderLayout.CENTER);
        } else {
            // Reset to grid layout for recipes display
            recipesPanel.setLayout(new GridLayout(0, 1));
            for (Recipe recipe : recipes) {
                JPanel recipePanel = createRecipePanel(recipe);
                recipesPanel.add(recipePanel);
            }
        }

        recipesPanel.revalidate();
        recipesPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    /**
     * Creates and returns a JPanel that represents the visual representation of a recipe.
     *
     * @param recipe The recipe to create a panel for.
     * @return A JPanel that displays the recipe's details.
     */
    protected JPanel createRecipePanel(Recipe recipe) {
        JPanel recipePanel = new JPanel(new BorderLayout(5, 0));

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
                    imageLabel.setText("");
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
        if (usedIngredientsList.isEmpty()) usedIngredientsList = "<li>No available ingredients</li>";

        String missedIngredientsList = recipe.getMissedIngredients().stream()
                .map(ingredient -> "<li>" + ingredient.getName() + "</li>")
                .reduce("", (a, b) -> a + b);
        if (missedIngredientsList.isEmpty()) missedIngredientsList = "<li>No missing ingredients</li>";

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(245, 223, 162));

        // use HTML for list formatting
        JButton recipeButton = new JButton("<html><body style='text-align:left;'>"
                + "<h3>" + recipe.getTitle() + "</h3>"
                + "<br><b>Available Ingredients:</b> <ul>" + usedIngredientsList + "</ul>"
                + "<br><b>Missing Ingredients:</b> <ul>" + missedIngredientsList + "</ul>"
                + "</body></html>");
        recipeButton.setHorizontalAlignment(SwingConstants.LEFT);
        recipeButton.setFocusable(false);
        recipeButton.addActionListener(e -> showRecipeDetails(recipe));

        detailsPanel.add(recipeButton, BorderLayout.CENTER);

        recipePanel.add(detailsPanel, BorderLayout.CENTER);

        recipesPanel.add(recipePanel);
        return recipePanel;
    }

    /**
     * Shows the detail view for a selected recipe.
     *
     * @param recipe The recipe to display details for.
     */
    protected void showRecipeDetails(Recipe recipe) {
        SwingUtilities.invokeLater(() -> {
            recipeDetailView.setRecipeDetailViewVisibility(true);
            recipeDetailView.setTextArea(recipe);
        });
    }

    /**
     * Handles action events, such as clicking the back button to return to the home view.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            HomeView.getHomeView().setHomeViewVisibility(true);
            setRecipeListViewVisibility(false);
        }
    }

    /**
     * Sets the visibility of the RecipeListView panel, updating the main frame's content pane as needed.
     *
     * @param visible If true, makes the RecipeListView visible; if false, hides it.
     */
    public void setRecipeListViewVisibility(boolean visible) {
        if (visible) {
            HomeView.getFrame().getContentPane().removeAll();

            RecipeUtility.findRecipesLazyLoad(ingredients, recipes);

            this.addActionListeners();
            this.displayRecipes();

            // Add this view
            HomeView.getFrame().add(this);
            this.setVisible(true);
        } else {
            this.setVisible(false);
            this.removeActionListeners(backButton);
            HomeView.getFrame().getContentPane().remove(this);
        }

        // Repaint and validate to reflect changes
        HomeView.getFrame().revalidate();
        HomeView.getFrame().repaint();
        HomeView.getHomeView().setHomeViewVisibility(!visible);
    }

    /**
     * Safely adds action listeners to buttons.
     */
    public void addActionListeners() {
        removeActionListeners(backButton);
        backButton.addActionListener(this);
    }

    /**
     * Removes all action listeners from a button.
     *
     * @param button The JButton to clear listeners from.
     */
    public void removeActionListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

}