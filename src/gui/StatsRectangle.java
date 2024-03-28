package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import domain.logic.StatsUtilities;

public class StatsRectangle extends JPanel {
	
	
	public ArrayList<String> allItems;
	private int totalItemCount;
	
	private final int BAR_LENGTH = 600;
	private final int LEFT_EDGE = 100;
	private final int yLevel = 400;
	
	public StatsRectangle(String cName) {
		
		allItems = HomeView.data.getTotalCount(cName);
		totalItemCount = allItems.size();
		setLayout(null);
		setVisible(true);
	
	}
	

	public void drawRect(Graphics g, int yLevel, ArrayList<String> allItems, int height) {

	
		double protein = (double) StatsUtilities.getTotalFoodGroup("Protein", allItems) / allItems.size();
		double vegetable = (double) StatsUtilities.getTotalFoodGroup("Vegetable", allItems) / allItems.size();
		double grain = (double) StatsUtilities.getTotalFoodGroup("Grain", allItems) / allItems.size();
		double fruit = (double) StatsUtilities.getTotalFoodGroup("Fruit", allItems) / allItems.size();
		double dairy = (double) StatsUtilities.getTotalFoodGroup("Dairy", allItems) / allItems.size();

		g.setColor(new Color(252, 156, 156));	
		
		g.fillRect(LEFT_EDGE, yLevel, (int) (BAR_LENGTH * protein), height);

		g.setColor(new Color(193, 219, 155));
		g.fillRect(LEFT_EDGE + (int) (BAR_LENGTH * protein), yLevel, (int) (BAR_LENGTH * vegetable), height);
		g.setColor(new Color(255, 235, 156));
		g.fillRect(LEFT_EDGE + (int) (BAR_LENGTH * protein) + (int) (BAR_LENGTH * vegetable), yLevel,
				(int) (BAR_LENGTH * grain), height);
		g.setColor(new Color(195, 177, 225));
		g.fillRect(
				LEFT_EDGE + (int) (BAR_LENGTH * protein) + (int) (BAR_LENGTH * vegetable) + (int) (BAR_LENGTH * grain),
				yLevel, (int) (BAR_LENGTH * fruit), height);
		g.setColor(new Color(207, 219, 231));
		g.fillRect(LEFT_EDGE + (int) (BAR_LENGTH * protein) + (int) (BAR_LENGTH * vegetable)
				+ (int) (BAR_LENGTH * grain) + (int) (BAR_LENGTH * fruit), yLevel, (int) (BAR_LENGTH * dairy), height);

	}
	
	
	

}
