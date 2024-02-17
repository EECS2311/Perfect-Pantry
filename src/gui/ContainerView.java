package gui;

import domain.logic.Item;
import domain.logic.Container;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ContainerView implements ActionListener {
	private JPanel containerView;
	private JButton back;
	private JButton delete;

	private Home home;
	private Itemslist itemsListPanel;
	private AddItemPanel addItemPanel;
	private Container container;

	public ContainerView(Home home, Container container) {
		this.home = home;
		this.container = container;
		containerView = new JPanel(new BorderLayout()); // Set BorderLayout for the main panel
		back = new JButton("Back");
		delete = new JButton("Delete Item");
		// add = new JButton("Add Item");

		itemsListPanel = new Itemslist(home, container);
		addItemPanel = new AddItemPanel(itemsListPanel);

		back.addActionListener(this);
		delete.addActionListener(this);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Create a panel for buttons with FlowLayout
		buttonPanel.add(back); // Add back button to the button panel
		buttonPanel.add(delete); // Add delete button to the button panel

		containerView.add(buttonPanel, BorderLayout.NORTH); // Add the button panel to the top of the main panel
		containerView.add(itemsListPanel, BorderLayout.CENTER); // Add the items list panel to the center
		containerView.add(addItemPanel, BorderLayout.SOUTH); // Add the add item panel to the bottom
	}

	public void setupContainerViewGUI(boolean isVisible) {
		JFrame frame = Home.getFrame(); // Get the main frame
		if (isVisible) {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			frame.getContentPane().add(containerView); // Add this container view
		} else {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			home.initializeHomeGUI(); // Reinitialize home GUI components
			// No need to set the frame itself to invisible, just refresh its content
		}
		frame.revalidate();
		frame.repaint();
		// Instead of toggling JFrame visibility, ensure the content pane is correctly updated
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			setupContainerViewGUI(false); // Hide this view
		}

		if (e.getSource() == delete) {

			new DeleteItemView(containerView);
		}
	}
}