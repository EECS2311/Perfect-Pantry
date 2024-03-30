package gui;

import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class StarredRecipeListView extends RecipeListView {
    private static StarredRecipeListView instance;
    private List<Recipe> starredRecipes = new ArrayList<>();

    private JLabel titleLabel = new JLabel("Starred Recipes");

    private StarredRecipeListView() {
        super();
        titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
    }

    public static StarredRecipeListView getInstance() {
        if (instance == null) {
            instance = new StarredRecipeListView();
        }
        return instance;
    }

    @Override
    protected void displayRecipes() {
        recipesPanel.removeAll();
        recipesPanel.setBackground(new Color(245, 223, 162));

        starredRecipes = HomeView.data.getAllStarredRecipes();

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        recipesPanel.add(titleLabel);

        if (starredRecipes.isEmpty()) {
            JLabel emptyMessageLabel = new JLabel("No starred recipes. Start exploring and star your favorites!");
            emptyMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            recipesPanel.add(emptyMessageLabel);
        } else {
            recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
            for (Recipe recipe : starredRecipes) {
                JPanel recipePanel = createRecipePanel(recipe);
                recipesPanel.add(recipePanel);
            }
        }
        recipesPanel.revalidate();
        recipesPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    @Override
    public JPanel createRecipePanel(Recipe recipe) {
        JPanel recipePanel = new JPanel(new BorderLayout(5, 0));
        // Create popup menu
        JPopupMenu popup = createPopupMenu(recipe, recipePanel);
        recipePanel.setComponentPopupMenu(popup);

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

        String usedIngredientsList = recipe.getUsedIngredients().stream()
                .map(ingredient -> "<li>" + ingredient.getName() + "</li>")
                .reduce("", (a, b) -> a + b);
        if (usedIngredientsList.isEmpty()) usedIngredientsList = "<li>No ingredients</li>";
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(245, 223, 162));

        JButton recipeButton = new JButton("<html><body style='text-align:left;'>"
                + "<h3>" + recipe.getTitle() + "</h3>"
                + "<br><b>Ingredients:</b> <ul>" + usedIngredientsList + "</ul>"
                + "</body></html>");
        recipeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        recipeButton.setHorizontalAlignment(SwingConstants.LEFT);
        recipeButton.setFocusable(false);
        recipeButton.addActionListener(e -> showRecipeDetails(recipe));
        detailsPanel.add(recipeButton, BorderLayout.CENTER);

        recipePanel.add(detailsPanel, BorderLayout.CENTER);
        recipesPanel.add(recipePanel);
        return recipePanel;
    }

    private JPopupMenu createPopupMenu(Recipe recipe, JPanel recipePanel) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem deleteRecipeBtn = new JMenuItem("Delete Recipe");
        deleteRecipeBtn.addActionListener(e -> deleteRecipe(recipe, recipePanel));
        popup.add(deleteRecipeBtn);
        return popup;
    }

    private void deleteRecipe(Recipe recipe, JPanel recipePanel) {
        int opt = JOptionPane.showConfirmDialog(HomeView.getFrame(), "Delete Recipe \"" + recipe.getTitle() + "\"?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            HomeView.data.removeStarredRecipe(recipe);
            displayRecipes();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void setRecipeListViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame();
        frame.getContentPane().removeAll();
        if (visible) {

            StarredRecipeListView recipeListView = StarredRecipeListView.getInstance();

            HomeView.getFrame().add(recipeListView);

            recipeListView.displayRecipes();
        } else {
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();
    }
}