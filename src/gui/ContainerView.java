package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DB;
import domain.logic.Container;

/**
 * Represents the GUI view for a container. It allows users to interact with a
 * container and its items through a graphical interface, including viewing
 * items, adding new items, and deleting existing items.
 */
public class ContainerView implements ActionListener {
	private JPanel containerView;
	private JButton back;
	private JButton filter = new JButton("Find");
	private JTextField itemFilterText;

	private HomeView home;
	private ItemsListView itemsListPanel;
	private AddItemView addItemPanel;
	private Container container;
	
	private JButton viewCalendar = new JButton ("View Calendar");

	DB data = home.data;

	/**
	 * Constructs a new ContainerView associated with a specific container and home.
	 * 
	 * @param home      The Home instance that this view is part of.
	 * @param container The container to be managed and displayed in this view.
	 */
	public ContainerView(HomeView home, Container container) {
		this.home = home;
		this.container = container;
		containerView = new JPanel(new BorderLayout()); // Set BorderLayout for the main panel
		back = new JButton("Back");

		itemFilterText = new JTextField(10);
		
		itemsListPanel = new ItemsListView(home, container);
		addItemPanel = new AddItemView(itemsListPanel);
		addItemPanel.add(viewCalendar);
		viewCalendar.addActionListener(this);

		back.addActionListener(this);
		filter.addActionListener(this);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Create a panel for buttons with FlowLayout
		buttonPanel.add(back); // Add back button to the button panel
		buttonPanel.add(itemFilterText);
		buttonPanel.add(filter);

		containerView.add(buttonPanel, BorderLayout.NORTH); // Add the button panel to the top of the main panel
		containerView.add(itemsListPanel, BorderLayout.CENTER); // Add the items list panel to the center
		containerView.add(addItemPanel, BorderLayout.SOUTH); // Add the add item panel to the bottom
	}

	/**
	 * Sets up and displays the container view GUI. This method adjusts the
	 * visibility and content of the main application frame based on the specified
	 * visibility flag.
	 * 
	 * @param isVisible Specifies whether the container view should be made visible.
	 */
	public void setupContainerViewGUI(boolean isVisible) {
		JFrame frame = HomeView.getFrame(); // Get the main frame
		if (isVisible) {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			frame.getContentPane().add(containerView); // Add this container view
		} else {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			home.setHomeViewVisibility(true); // Reinitialize home GUI components
			// No need to set the frame itself to invisible, just refresh its content
		}
		frame.revalidate();
		frame.repaint();
		// Instead of toggling JFrame visibility, ensure the content pane is correctly
		// updated
	}

	/**
	 * Handles action events triggered by GUI components (e.g., buttons). This
	 * method processes events such as navigating back to the home view and deleting
	 * an item.
	 * 
	 * @param e The action event that was triggered.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			setupContainerViewGUI(false); // Hide this view
		}

		if (e.getSource() == filter) {
			itemsListPanel.filterTable(getItemFilterText());
    }
    
		if(e.getSource() == viewCalendar) {
			setupContainerViewGUI(false); //hide this view
			home.setHomeViewVisibility(false); //hides the view setup displays
			new CalendarView(container, this, home); //initialises Calendar
		}

	}
	
	private String getItemFilterText() {
		return itemFilterText.getText();
	}
}