package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.logic.StatsUtilities;

public class StatsView extends JPanel implements ItemListener {

	private static StatsView instance;
	private JPanel foodGroups;
	private JLabel title;
	private JButton backButton;
	private JComboBox containerSelect;
	private StatsRectangle st;
	List<String> containers;
	public ArrayList<String> allItems;
	private int totalItemCount;

	private final int BAR_LENGTH = 600;
	private final int LEFT_EDGE = 100;

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		st.drawRect(g, 150, allItems);
		st.drawRect(g, 400, HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()));

	}


	public StatsView() {

		foodGroups = new JPanel(new GridLayout(1, 5, 7, 0));
		title = new JLabel("Pantry Composition: ");
		backButton = new JButton("Back");
		containers = HomeView.data.retrieveContainers();
		containerSelect = new JComboBox(containers.toArray(new String[0]));
		allItems = HomeView.data.getTotalCount(null);
		totalItemCount = allItems.size();
		st = new StatsRectangle(containers.get(0));
	

		JLabel protein = new JLabel("Protein: " + StatsUtilities.getPercent(allItems, totalItemCount, "Protein") + "%");
		protein.setForeground(new Color(252, 156, 156));
		JLabel vegetable = new JLabel(
				"Vegetables: " + StatsUtilities.getPercent(allItems, totalItemCount, "Vegetable") + "%");
		vegetable.setForeground(new Color(193, 219, 155));
		JLabel grain = new JLabel("Grain: " + StatsUtilities.getPercent(allItems, totalItemCount, "Grain") + "%");
		grain.setForeground(new Color(255, 235, 156));
		JLabel fruit = new JLabel("Fruit: " + StatsUtilities.getPercent(allItems, totalItemCount, "Fruit") + "%");
		fruit.setForeground(new Color(195, 177, 225));
		JLabel dairy = new JLabel("Dairy: " + StatsUtilities.getPercent(allItems, totalItemCount, "Dairy") + "%");
		dairy.setForeground(new Color(207, 219, 231));

		JLabel b = new JLabel("All Pantries: " + totalItemCount + " Items");
		b.setBounds(LEFT_EDGE, 90, 300, 100);

		foodGroups.add(protein);
		foodGroups.add(vegetable);
		foodGroups.add(grain);
		foodGroups.add(fruit);
		foodGroups.add(dairy);

		foodGroups.setBounds(LEFT_EDGE, 220, 600, 30);

		foodGroups.setBackground(new Color(0, 0, 0));

		title.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		title.setBounds(LEFT_EDGE, 50, 500, 100);

		backButton.setBounds(20, 20, 80, 30);
		backButton.addActionListener(e -> {
			HomeView.getHomeView().setHomeViewVisibility(true);
			setStatsViewVisibility(false);
		});

		containerSelect.setBounds(LEFT_EDGE, 300, 200, 50);
		containerSelect.addItemListener(this);

		add(containerSelect);
		add(backButton);
		add(foodGroups);
		add(title);
		add(b);
		add(st);
		
		setBackground(new Color(253, 241, 203));
		setLayout(null);
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

	@Override
	public void itemStateChanged(ItemEvent e) {
			
		if (e.getSource() == containerSelect && e.getStateChange() == ItemEvent.SELECTED) {
		
			repaint();
			
		}
		
	}

}
