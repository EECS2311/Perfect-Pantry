package gui;

import domain.logic.recipe.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeDetailView extends JPanel implements ActionListener {
    private Recipe recipe;
    private JButton backButton = new JButton("Back");

    JTextArea instructionsArea = new JTextArea();
    StringBuilder instructionsText;

    public RecipeDetailView(Recipe recipe) {
        this.recipe = recipe;
        setLayout(new BorderLayout());
        backButton.addActionListener(this);
        add(backButton, BorderLayout.NORTH);

        if(recipe != null){
            instructionsText = new StringBuilder();
            recipe.getDetailedInstructions().forEach((stepNumber, instruction) -> instructionsText.append(stepNumber).append(". ").append(instruction).append("\n"));
            instructionsArea.setText(instructionsText.toString());
            instructionsArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(instructionsArea);
            add(scrollPane, BorderLayout.CENTER);
        }

    }

    public void setTextArea(Recipe recipe) {
        this.recipe = recipe; // Update the recipe instance
        // Update the instructions text based on the new recipe
        instructionsArea.setText(""); // Clear existing text
        if (recipe != null) {
            instructionsText = new StringBuilder();
            recipe.getDetailedInstructions().forEach((stepNumber, instruction) ->
                    instructionsText.append(stepNumber).append(". ").append(instruction).append("\n"));
            instructionsArea.setText(instructionsText.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setRecipeDetailViewVisibility(false);
            RecipeListView.showListView();
        }
    }


    public void setRecipeDetailViewVisibility(boolean visible) {
        backButton.addActionListener(this);
        this.setVisible(visible);
    }
}
