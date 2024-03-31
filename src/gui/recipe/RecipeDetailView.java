package gui.recipe;

import javax.swing.*;
import javax.swing.text.Utilities;

import domain.logic.recipe.DailyLimitExceededException;
import domain.logic.recipe.RateLimitPerMinuteExceededException;
import domain.logic.recipe.Recipe;
import domain.logic.recipe.RecipeUtility;
import gui.home.HomeView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

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

    private static Font customFont = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());

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
        detailsArea.setContentType("text/html");

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
            StringBuilder htmlContent = new StringBuilder("<html><head><style>body { font-family: ").append(customFont.getFamily()).append("; font-size: ").append(customFont.getSize()).append("pt; }</style></head><body>");
            htmlContent.append("<h1>").append(recipe.getTitle()).append("</h1>");
            htmlContent.append("<img src='").append(recipe.getImage()).append("' style='width: 200px; height: auto;'><br>");

            htmlContent.append("<h3>Ingredients:</h3><ul>");
            recipe.getUsedIngredients().forEach(ingredient -> htmlContent.append("<li>").append(ingredient.getOriginal()).append("</li>"));
            if (!recipe.getMissedIngredients().isEmpty()) {
                htmlContent.append("<h3>Missing Ingredients:</h3><ul>");
                recipe.getMissedIngredients().forEach(ingredient -> htmlContent.append("<li>").append(ingredient.getOriginal()).append("</li>"));
            }

            try {
                Map<Integer, String> instructions = recipe.getDetailedInstructions();
                if (instructions.isEmpty()) {
                    htmlContent.append("<p>No instructions available</p>");
                } else {
                    htmlContent.append("<h2>Instructions:</h2><ol>");
                    instructions.forEach((step, instruction) -> htmlContent.append("<li>").append(instruction).append("</li>"));
                    htmlContent.append("</ol>");
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Failed to load recipe instructions due to a network error.", "Network Error", JOptionPane.ERROR_MESSAGE));
            } catch (DailyLimitExceededException e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Daily API limit exceeded. Please try again later.", "API Limit Error", JOptionPane.WARNING_MESSAGE));
            } catch (RateLimitPerMinuteExceededException e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "API requests are too frequent. Please wait a moment before trying again.", "API Limit Error", JOptionPane.WARNING_MESSAGE));
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "An unexpected error occurred while loading recipe instructions.", "Error", JOptionPane.ERROR_MESSAGE));
            }

            htmlContent.append("</ol></body></html>");
            detailsArea.setText(htmlContent.toString());
        }
    }

    /**
     * Sets the recipe to be displayed in the text area and updates the view.
     * @param recipe The recipe to display.
     */
    public void setTextArea(Recipe recipe) {
        this.recipe = recipe;
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
            HomeView.getHomeView().setHomeViewVisibility(true);
            setRecipeDetailViewVisibility(false);
            HomeView.getFrame().remove(this);
        }
        else if (e.getSource() == starRecipeButton) {
            boolean isRecipeExists = RecipeUtility.verifySaveRecipeToDatabase(recipe, HomeView.data);
            if (!isRecipeExists) {
                JOptionPane.showMessageDialog(this, "This recipe is already saved.");
            }
        }
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
            customFont = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
            backButton.setFont(customFont);
            starRecipeButton.setFont(customFont);
            HomeView.getFrame().add(recipeDetailView);
        } else {
            frame.getContentPane().removeAll();
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();

    }

}