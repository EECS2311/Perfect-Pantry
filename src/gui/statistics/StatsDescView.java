package gui.statistics;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.logic.statistics.StatsUtilities;
import gui.home.HomeView;

public class StatsDescView extends JPanel {

	JLabel protein;
	JLabel vegetable;
	JLabel grain;
	JLabel fruit;
	JLabel dairy;
	private final int LEFT_EDGE = 100;

	public StatsDescView(int yLevel) {
		setLayout(new GridLayout(1, 5, 7, 0));

		protein = new JLabel();
		protein.setForeground(new Color(252, 156, 156));

		vegetable = new JLabel();
		vegetable.setForeground(new Color(193, 219, 155));

		grain = new JLabel();
		grain.setForeground(new Color(255, 235, 156));

		fruit = new JLabel();
		fruit.setForeground(new Color(195, 177, 225));

		dairy = new JLabel();
		dairy.setForeground(new Color(207, 219, 231));

		add(protein);
		add(vegetable);
		add(grain);
		add(fruit);
		add(dairy);

		addFonts();

		setBounds(LEFT_EDGE, yLevel, 600, 30);
		setBackground(new Color(0, 0, 0));
		setVisible(true);

	}

	public void addFonts(){
		Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
		protein.setFont(f);
		vegetable.setFont(f);
		grain.setFont(f);
		fruit.setFont(f);
		dairy.setFont(f);

	}

	public JScrollPane addJScrollPane(){
		return new JScrollPane(this);
	}

	public void setDesc(ArrayList<String> allItems) {

		protein.setText("Protein: " + StatsUtilities.getPercent(allItems, allItems.size(), "Protein") + "%");
		vegetable.setText("Vegetables: " + StatsUtilities.getPercent(allItems, allItems.size(), "Vegetable") + "%");
		grain.setText("Grain: " + StatsUtilities.getPercent(allItems, allItems.size(), "Grain") + "%");
		fruit.setText("Fruit: " + StatsUtilities.getPercent(allItems, allItems.size(), "Fruit") + "%");
		dairy.setText("Dairy: " + StatsUtilities.getPercent(allItems, allItems.size(), "Dairy") + "%");

	}

}
