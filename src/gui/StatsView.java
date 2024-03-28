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

public class StatsView extends JPanel implements ItemListener {

	private static StatsView instance;
	private JPanel foodGroups;
	private JLabel title;
	private JButton backButton;
	private JComboBox containerSelect;
	private StatsRectangle st;
	StatsDesc d1;
	StatsDesc dx;
	JLabel b2;
	List<String> containers;
	public ArrayList<String> allItems;
	private int totalItemCount;

	private final int BAR_LENGTH = 600;
	private final int LEFT_EDGE = 100;

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		st.drawRect(g, 150, allItems, 50);
		st.drawRect(g, 400, HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()), 30);

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
		d1 = new StatsDesc(220);
		d1.setDesc(allItems);
		dx = new StatsDesc(450);
		dx.setDesc(HomeView.data.getTotalCount(containers.get(0)));
	

		JLabel b = new JLabel("All Pantries: " + totalItemCount + " Items");
		b.setBounds(LEFT_EDGE, 90, 300, 100);
		
		b2 = new JLabel(HomeView.data.getTotalCount(containers.get(0)).size() + " Items");
		b2.setBounds(LEFT_EDGE, 340, 300, 100);


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
		add(b2);
		add(st);
		add(d1);
		add(dx);
		
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
			
			dx.setDesc(HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()));
			b2.setText(HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()).size() + " Items");
			repaint();
			
		}
		
	}

}
