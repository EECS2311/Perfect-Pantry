package gui;

import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StarredRecipeListView extends RecipeListView {
    private static StarredRecipeListView instance;

    private StarredRecipeListView() {
        super();
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

        // Assuming HomeView.data.getAllStarredRecipes() is a static method that fetches the recipes
//        recipes = HomeView.data.getAllStarredRecipes(); // Fetch starred recipes from database

        if (recipes.isEmpty()) {
            JLabel emptyMessageLabel = new JLabel("No starred recipes. Start exploring and star your favorites!");
            emptyMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            recipesPanel.add(emptyMessageLabel);
        } else {
            // This assumes a vertical BoxLayout is what you want. If not, set your desired layout
            recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
            for (Recipe recipe : recipes) {
                JPanel recipePanel = createRecipePanel(recipe);
                recipesPanel.add(recipePanel);
                // Removed the line adding a Box.createRigidArea
            }
        }

        recipesPanel.revalidate();
        recipesPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e); // This will handle the backButton among other things if you have more actions
    }

    @Override
    public void setRecipeListViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame();
        frame.getContentPane().removeAll();
        if (visible) {

            StarredRecipeListView recipeListView = StarredRecipeListView.getInstance();

            //Add all panels
            HomeView.getFrame().add(recipeListView);

            recipes = HomeView.data.getAllStarredRecipes();

            recipeListView.displayRecipes();
        } else {
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();
    }
}
