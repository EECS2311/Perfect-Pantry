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

	// private JButton add;
	private Home home;
	private Itemslist itemsListPanel;
	private AddItemPanel addItemPanel;
	private Container container;

	public ContainerView(Home home, Container container) {
		this.home = home;
		this.container = container;
		containerView = new JPanel();
		back = new JButton("Back");
		delete = new JButton("Delete Item");
		// add = new JButton("Add Item");
		//items = new ArrayList<>();


		Itemslist itemsListPanel = new Itemslist();
		AddItemPanel addItemPanel = new AddItemPanel(home, container, itemsListPanel);

		back.addActionListener(this);
		delete.addActionListener(this);

		containerView.add(back);
		// containerView.add(add);
		containerView.add(delete);

		containerView.add(addItemPanel, BorderLayout.SOUTH);
		containerView.add(itemsListPanel, BorderLayout.CENTER);
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