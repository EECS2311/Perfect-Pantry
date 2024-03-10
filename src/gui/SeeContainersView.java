package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
	 * Allows containerButtonPanel to be Scrollable
	 */
	JScrollPane pane = new JScrollPane(containerButtonsPanel);

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
	
	/**
	 * Holds this isntance of SeeContainersView
	 */
	private static SeeContainersView containersView;



	/**
	 * Launches the application and initializes the main GUI components.
	 */
	public SeeContainersView() {
		containersView = this;
	}

	/**
	 * Sets the visibility of the SeeContainersView GUI depending on the boolean passed
	 * @param b the value of whether the visibility is true or not
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

			
//			viewOfContainerPanel.add(containerButtonsPanel);
			containerButtonsPanel.setBounds(0, 50, 800, 500);
			containerButtonsPanel.setBackground(new Color(253, 241, 200));
			containerButtonsPanel.setLayout(new GridLayout((HomeView.getContainerMap().size()/3 +1), 3));
			
			
			containerButtonsPanel.removeAll();
			HomeView.getContainerMap().forEach((button, container) -> {
				containerButtonsPanel.add(button);
				button.addActionListener(this);
			});
			
			viewOfContainerPanel.add(pane);
			pane.setBounds(0, 50, 800, 500);
		
			viewOfContainerPanel.setVisible(true);

		}
		if (b == false) {
			//Remove Action listeners
			HomeView.getContainerMap().forEach((button, container) -> {
				button.removeActionListener(this);
			});
			viewOfContainer2HomeButton.removeActionListener(this);
			editContainerNameButton.removeActionListener(this);
			deleteContainerButton.removeActionListener(this);
			viewOfContainerPanel.setVisible(false);

		}
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
	
	/**
	 * Provide Accces to this instance of SeeContainersView
	 * 
	 * @return the current instance of SeeContainersView
	 */
	public static SeeContainersView getContainersView() {
		return containersView;
	}



}
