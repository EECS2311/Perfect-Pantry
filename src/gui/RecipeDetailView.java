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
        System.out.println("Text Area Size: " + instructionsArea.getSize());

        Component parent = this.getParent();
        while (parent != null) {
            System.out.println(parent.getClass().getName());
            parent = parent.getParent();
        }

        this.recipe = recipe;
        setLayout(new BorderLayout());
        backButton.addActionListener(this);
        add(backButton, BorderLayout.NORTH);
        instructionsArea.setVisible(true);

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
        System.out.println("Updating RecipeDetailView with new recipe");
        this.recipe = recipe; // Update the recipe instance

        // Clear existing text
        instructionsArea.setText("");
        instructionsArea.setPreferredSize(new Dimension(500, 300)); // Adjust the dimensions as needed

        if (recipe != null) {
            System.out.println("Recipe is not null");
            instructionsText = new StringBuilder();
            recipe.getDetailedInstructions().forEach((stepNumber, instruction) -> {
                System.out.println("Instruction step: " + stepNumber + " - " + instruction);
                instructionsText.append(stepNumber).append(". ").append(instruction).append("\n");
            });
            instructionsArea.setText(instructionsText.toString());

            instructionsArea.revalidate();
            instructionsArea.repaint();
            System.out.println(instructionsArea.getText());


        } else {
            System.out.println("Recipe is null");
        }
        this.revalidate(); // Ensure the RecipeDetailView layout is refreshed
        this.repaint(); // Ensure the RecipeDetailView is repainted
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
        instructionsArea.setVisible(visible);
        instructionsArea.revalidate();
        instructionsArea.repaint();

        if (visible) {
            // Add the RecipeDetailView to the main application frame
            JFrame mainFrame = HomeView.getFrame();
            mainFrame.getContentPane().removeAll();
            mainFrame.getContentPane().add(this);
            mainFrame.getContentPane().revalidate();
            mainFrame.getContentPane().repaint();
            mainFrame.pack(); // Adjust the frame size to fit the content

            // Revalidate and repaint the RecipeDetailView
            this.revalidate();
            this.repaint();
        }
    }
}
