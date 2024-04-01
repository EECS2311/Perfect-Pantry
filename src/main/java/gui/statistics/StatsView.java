package main.java.gui.statistics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import main.java.gui.home.HomeView;

public class StatsView extends JPanel implements ItemListener {

	private static StatsView instance;
	private JPanel foodGroups;
	private JLabel title;
	private JButton backButton;
	private JComboBox containerSelect;
	private StatsRectangleView st;
	StatsDescView d1;
	StatsDescView dx;
	JLabel b1;
	JLabel bx;
	List<String> containers;
	public ArrayList<String> allItems;
	private int totalItemCount;

	private final int BAR_LENGTH = 600;
	private final int LEFT_EDGE = 100;

	JScrollPane scrollD1;
	JScrollPane scrollDx;


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
		st = new StatsRectangleView(containers.get(0));
		d1 = new StatsDescView(220);
		d1.setDesc(allItems);
		scrollD1 = d1.addJScrollPane();
		scrollD1.setBounds(LEFT_EDGE, 220, 600, 60);
		dx = new StatsDescView(450);
		dx.setDesc(HomeView.data.getTotalCount(containers.get(0)));
		scrollDx = dx.addJScrollPane();
		scrollDx.setBounds(LEFT_EDGE, 450, 600, 60);
	

		b1 = new JLabel("All Pantries: " + totalItemCount + " Items");
		b1.setBounds(LEFT_EDGE, 90, 300, 100);
		
		bx = new JLabel(HomeView.data.getTotalCount(containers.get(0)).size() + " Items");
		bx.setBounds(LEFT_EDGE, 340, 300, 100);


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
		add(b1);
		add(bx);
		add(st);
		add(scrollD1); //Yes
		add(scrollDx); //Yes

		addFonts();
		
		setBackground(new Color(253, 241, 203));
		setLayout(null);
		setVisible(true);

	}

	public void addFonts(){
		Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
		containerSelect.setFont(f);
		backButton.setFont(f);
		d1.addFonts();
		dx.addFonts();

		if(f.getSize() >= 22) {
			backButton.setBounds(20, 20, 200, 50);
			containerSelect.setBounds(LEFT_EDGE, 300, 300, 100);
		}
		else{
			backButton.setBounds(20, 20, 80, 30);
			containerSelect.setBounds(LEFT_EDGE, 300, 200, 50);

		}

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
			addFonts();
			refresh();
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
			bx.setText(HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()).size() + " Items");
			repaint();
			
		}
		
	}
	
	public void refresh() {
		allItems = HomeView.data.getTotalCount(null);
		b1.setText("All Pantries: " + allItems.size() + " Items");
		d1.setDesc(allItems);
		bx.setText(HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()).size() + " Items");
		dx.setDesc(HomeView.data.getTotalCount((String)containerSelect.getSelectedItem()));
		
	}

}
