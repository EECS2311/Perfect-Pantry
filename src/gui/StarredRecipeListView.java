package gui;

import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


public class StarredRecipeListView extends RecipeListView {
    private static StarredRecipeListView instance;
    private List<Recipe> starredRecipes = new ArrayList<>(); // Instance variable


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

        starredRecipes = HomeView.data.getAllStarredRecipes();

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
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void setRecipeListViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame();
        frame.getContentPane().removeAll();
        if (visible) {

            StarredRecipeListView recipeListView = StarredRecipeListView.getInstance();

            //Add all panels
            HomeView.getFrame().add(recipeListView);

            recipeListView.displayRecipes();
        } else {
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();
    }
}
