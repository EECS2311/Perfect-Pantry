package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.logic.StatsUtilities;

public class StatsView extends JPanel {

	private static StatsView instance;

	public ArrayList<String> allItems = HomeView.data.getTotalCount();
	private int totalItemCount = allItems.size();

	private final int BAR_LENGTH = 550;
	private final int LEFT_EDGE = 100;

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		double protein = (double) StatsUtilities.getTotalFoodGroup("Protein", allItems) / totalItemCount;
		double vegetable = (double) StatsUtilities.getTotalFoodGroup("Vegetable", allItems) / totalItemCount;
		double grain = (double) StatsUtilities.getTotalFoodGroup("Grain", allItems) / totalItemCount;
		double fruit = (double) StatsUtilities.getTotalFoodGroup("Fruit", allItems) / totalItemCount;
		double dairy = (double) StatsUtilities.getTotalFoodGroup("Dairy", allItems) / totalItemCount;

		g.setColor(Color.BLACK);

		g.fillRect(LEFT_EDGE, 150, (int) (BAR_LENGTH * protein), 50);

		g.setColor(Color.RED);
		g.fillRect(LEFT_EDGE + (int) (BAR_LENGTH * protein), 150, (int) (550 * vegetable), 50);

		g.setColor(Color.ORANGE);
		g.fillRect(LEFT_EDGE + (int) (BAR_LENGTH * protein) + (int) (BAR_LENGTH * vegetable), 150,
				(int) (BAR_LENGTH * grain), 50);

		g.setColor(Color.RED);
		g.fillRect(
				LEFT_EDGE + (int) (BAR_LENGTH * protein) + (int) (BAR_LENGTH * vegetable) + (int) (BAR_LENGTH * grain),
				150, (int) (BAR_LENGTH * fruit), 50);

		g.setColor(Color.WHITE);
		g.fillRect(LEFT_EDGE + (int) (BAR_LENGTH * protein) + (int) (BAR_LENGTH * vegetable)
				+ (int) (BAR_LENGTH * grain) + (int) (BAR_LENGTH * fruit), 150, (int) (550 * dairy), 50);

	}

	public StatsView() {

		setBackground(new Color(245, 223, 162));
		setLayout(null);
		JLabel title = new JLabel("Pantry Composition: ");
		JLabel protein = new JLabel("<html>" + "<font color='Brown'>Protein: </font>"
				+ StatsUtilities.getPercent(allItems, totalItemCount, "Protein") + "%</html>");
		JLabel vegetable = new JLabel("<html> <font color='Green'>Vegetables: </font>"
				+ StatsUtilities.getPercent(allItems, totalItemCount, "Vegetable") + "%</html>");
		JLabel grain = new JLabel("<html><font color='Orange'>Grain: </font>"
				+ StatsUtilities.getPercent(allItems, totalItemCount, "Grain") + "%</html>");
		JLabel fruit = new JLabel("<html><font color='Red'>Fruit: </font>"
				+ StatsUtilities.getPercent(allItems, totalItemCount, "Fruit") + "%</html>");
		JLabel dairy = new JLabel("<html><font color='White'>Dairy: </font>"
				+ StatsUtilities.getPercent(allItems, totalItemCount, "Dairy") + "%</html>");

		String.format("%.2f",
				((StatsUtilities.getTotalFoodGroup("Dairy", allItems) + 0.0) / totalItemCount + 0.0) * 100);

		JLabel b = new JLabel("All Pantries: " + totalItemCount + " Items");
		b.setBounds(LEFT_EDGE, 90, 300, 100);
		
		JPanel foodGroups = new JPanel(new GridLayout(1, 5, 7, 0));
		foodGroups.add(protein);
		foodGroups.add(vegetable);
		foodGroups.add(grain);
		foodGroups.add(fruit);
		foodGroups.add(dairy);

		add(foodGroups);

		foodGroups.setBounds(LEFT_EDGE, 220, 600, 50);
		foodGroups.setBackground(null);

		title.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		title.setBounds(LEFT_EDGE, 50, 500, 100);

		add(title);
		add(b);
		setVisible(true);

	}
	
	

	public static StatsView getInstance() {
		if (instance == null) {
			instance = new StatsView();
		}
		return instance;
	}

	public void setStatsViewVisibility(boolean visible) {

		JFrame frame = HomeView.getFrame(); // Get the main frame
		if (visible) {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			StatsView sView = StatsView.getInstance();
			// Add all panels
			HomeView.getFrame().add(sView);

		} else {
			frame.getContentPane().removeAll();
			HomeView.getHomeView().setHomeViewVisibility(true);
		}
		frame.revalidate();
		frame.repaint();
	}

}
