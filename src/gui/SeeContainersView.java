package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DB;
import domain.logic.Container;
import domain.logic.ContainerUtility;
import domain.logic.ItemUtility;

/**
 * The GUI which shows the list of Containers the user made,
 *  and is able to go to deleteContainerView, editContainerView, HomeView and ItemsView
 */
public class SeeContainersView implements ActionListener{

	/**
	 * Hold buttons pertaining to its containers
	 */
	private JPanel containerButtonsPanel = new JPanel();

	/**
	 * Button to go to container list view
	 */

	/**
	 * Holds components for the container list view
	 */
	private JPanel viewOfContainerPanel = new JPanel();

	/**
	 * Holds button on container list view
	 */
	private JPanel backPanel = new JPanel();

	/**
	 * Button to go from container view to home
	 */
	private JButton viewOfContainer2HomeButton = new JButton("Back");

	/**
	 * Button to change name of container
	 */
	private JButton editContainerNameButton = new JButton("Edit Name of Container");

	/**
	 * Button to go to delete Container view
	 */
	private JButton deleteContainerButton = new JButton("Delete a Container");
	
	private static SeeContainersView containersView;



	/**
	 * Launches the application and initializes the main GUI components.
	 */
	public SeeContainersView() {
		containersView = this;
	}

	/**
	 * 
	 * Changes the stage of the home screen between the main view, the Container
	 * list view, edit container view and delete container view
	 */
	public void setSeeContainersViewVisibility(boolean b) {
		if (b == true) { 
			HomeView.getHomeView().setHomeViewVisibility(false);
			DeleteContainerView.getDeleteContainerView().setDeleteContainerViewVisibility(false);
			EditContainerView.getEditContainerView().setEditContainerViewVisibility(false);
			
			viewOfContainer2HomeButton.addActionListener(this);
			editContainerNameButton.addActionListener(this);
			deleteContainerButton.addActionListener(this);
			
			HomeView.getFrame().add(viewOfContainerPanel);
			viewOfContainerPanel.setLayout(null);
			viewOfContainerPanel.setBackground(new Color(253, 241, 203));

			backPanel.setBackground(new Color(253, 241, 203));
			viewOfContainerPanel.add(backPanel);
			backPanel.setLayout(new FlowLayout());
			backPanel.setBounds(0, 0, 800, 50);

			backPanel.add(viewOfContainer2HomeButton);
			backPanel.add(editContainerNameButton);
			backPanel.add(deleteContainerButton);

			viewOfContainerPanel.add(containerButtonsPanel);
			containerButtonsPanel.setBounds(0, 50, 800, 500);
			containerButtonsPanel.setBackground(new Color(253, 241, 200));
			addContainerButtons(containerButtonsPanel);

			viewOfContainerPanel.setVisible(true);

		}
		if (b == false) {
			viewOfContainer2HomeButton.removeActionListener(this);
			editContainerNameButton.removeActionListener(this);
			deleteContainerButton.removeActionListener(this);
			viewOfContainerPanel.setVisible(false);

		}
	}



	/**
	 * Dynamically adds container buttons to the specified panel based on the
	 * current containerMap state.
	 * 
	 * @param p The panel to which container buttons will be added.
	 */
	public void addContainerButtons(JPanel p) {
		p.removeAll();
		HomeView.getContainerMap().forEach((button, container) -> {
			p.add(button);
			button.addActionListener(this);
		});
		p.revalidate(); // refresh panel
	}




	/**
	 * Handles action events triggered by various GUI components.
	 * This method is the central hub for processing user interactions within the home view.
	 *
	 * @param e The ActionEvent object containing details about the event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == viewOfContainer2HomeButton) {
			HomeView.getHomeView().setHomeViewVisibility(true);

		} else if (source == editContainerNameButton) {
			EditContainerView.getEditContainerView().setEditContainerViewVisibility(true);
		} else if (source == deleteContainerButton) {
			DeleteContainerView.getDeleteContainerView().setDeleteContainerViewVisibility(true);
		} else {
			Container c = HomeView.getContainerMap().get(source); // This will return null if the button is not found
			if (c != null) {
				c.getGUI();
			}

		}


	}
	
	public static SeeContainersView getContainersView() {
		return containersView;
	}



}
