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
 * The main GUI frame for the application, serving as the entry point for user
 * interaction. It includes functionality to add and edit container names, and
 * navigate between different views.
 */
public class HomeView implements ActionListener {

	/**
	 * Frame window of the application
	 */
	static JFrame frame = new JFrame("Perfect Pantry");

	/**
	 * Provide Access to Database
	 */
	public static DB data = new DB();
	
	/**
	 * Map of button and its corresponding Container object
	 */
	private static ConcurrentHashMap<JButton, Container> containerMap;

	/**
	 * home panel of the application, will hold all components pertaining to home
	 * screen
	 */
	private JPanel homePanel = new JPanel();

	/**
	 * Title name
	 */
	private JLabel titleLabel = new JLabel("Perfect Pantry");;

	/**
	 * Button that will add new container, apart of homeButtonsPanel
	 */
	private JButton createContainer = new JButton("Create");;

	/**
	 * Add text for name of container
	 */
	private JTextField newContainerText = new JTextField(40);;

	/**
	 * Button to go to container list view
	 */
	private JButton viewContainers = new JButton("View Containers");

	/**
	 * Holds this instance of HomeView
	 */
	private static HomeView home;
	

	public static void main(String[] args) {
		//initialise other views
		SeeContainersView m = new SeeContainersView();
		DeleteContainerView d = new DeleteContainerView();
		EditContainerView e = new EditContainerView();
		home = new HomeView();

	}


	/**
	 * Launches the application and initializes the main GUI components.
	 */
	public HomeView() {
		// Initialize frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
		frame.setVisible(true);
		frame.setResizable(false); // stop resize
		frame.setMinimumSize(new Dimension(800, 600));
		frame.getContentPane().setBackground(new Color(245, 223, 162));
		
		// Initialize containerMap
		containerMap = new ConcurrentHashMap<>();
		ContainerUtility.initContainers(containerMap, data, this);

		setHomeViewVisibility(true);
	}

	/**
	 * Sets the HomeView Gui components visibility depending on the boolean passed through
	 * @param b, Value whether gui should be visible or not
	 */
	public void setHomeViewVisibility(boolean b) {
		if (b == true) {
			//Set other gui visibility to false
			EditContainerView.getEditContainerView().setEditContainerViewVisibility(false);
			SeeContainersView.getContainersView().setSeeContainersViewVisibility(false);
			DeleteContainerView.getDeleteContainerView().setDeleteContainerViewVisibility(false);
			
			// Initialise all actionlisteners 
			newContainerText.addActionListener(this);
			createContainer.addActionListener(this);
			viewContainers.addActionListener(this);
			
			// Initialse mainMenuPanel
			homePanel.setLayout(null);
			frame.getContentPane().add(homePanel);
			frame.setLocationRelativeTo(null);

			homePanel.setBackground(new Color(253, 241, 203));
			homePanel.setVisible(true);

			// Initialize titleLabel
			homePanel.add(titleLabel);
			titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
			titleLabel.setBounds(240, 150, 500, 100);

			homePanel.add(newContainerText);
			newContainerText.setText("Pantry" + (containerMap.size() + 1));
			newContainerText.setForeground(Color.GRAY);
			newContainerText.setVisible(true);
			newContainerText.setBounds(240, 250, 250, 40);

			homePanel.add(createContainer);

			createContainer.setBackground(new Color(76, 183, 242));
			createContainer.setBounds(500, 250, 80, 40);

			homePanel.add(viewContainers);
			viewContainers.setBackground(new Color(76, 183, 242));
			viewContainers.setBounds(240, 300, 250, 40);
			homePanel.setVisible(true);
		}
		if (b == false) {
			//RemoveActionListeners
			newContainerText.removeActionListener(this);
			createContainer.removeActionListener(this);
			viewContainers.removeActionListener(this);
			homePanel.setVisible(false);
		}
	}


	/**
	 * Adds a new container and its corresponding button to the GUI.
	 */
	private void addNewContainer() {

		String nameOfContainer = newContainerText.getText();

		int opt = JOptionPane.showConfirmDialog(frame, "Create Container \"" + nameOfContainer + "\"?");
		if (opt == JOptionPane.YES_OPTION) {
			ContainerUtility
			.verifyAddContainer(
					nameOfContainer, data, this, containerMap, (errorMsg) -> JOptionPane
					.showMessageDialog(frame, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE),
					() -> {
						newContainerText.setText("Pantry" + (containerMap.size() + 1));
					});
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

			if (source == createContainer) {
				addNewContainer();
			} else if (source == viewContainers) {
				SeeContainersView.getContainersView().setSeeContainersViewVisibility(true);

			}

		
	}

	/**
	 * Provides access to the main application frame.
	 * 
	 * @return The main JFrame of the application.
	 */
	public static JFrame getFrame() {
		return frame;
	}
	
	/**
	 * Provides access to HomeView object
	 * 
	 * @return The HomeView object
	 */
	public static HomeView getHomeView() {
		return home;
	}
	
	/**
	 * Provides access to the hashed JButton and Container map
	 * 
	 * @return the ConcurrentHashmap containerMap
	 */
	public static ConcurrentHashMap<JButton, Container> getContainerMap(){
		return containerMap;
	}
}
