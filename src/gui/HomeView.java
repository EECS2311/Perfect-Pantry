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

	public static DB data = new DB();

	/**
	 * home panel of the application, will hold all components pertaining to home
	 * screen
	 */
	private JPanel homePanel = new JPanel();

	/**
	 * Title part of home screen
	 */
	private JPanel titlePanel = new JPanel();

	/**
	 * Title name
	 */
	private JLabel titleLabel = new JLabel("Perfect Pantry");;

	/**
	 * Button that will add new container, apart of homeButtonsPanel
	 */
	private JButton createContainer = new JButton("Create");;

	/**
	 * Hold buttons pertaining to its containers
	 */
	private JPanel containerButtonsPanel = new JPanel();

	/**
	 * Map of button and its corresponding Container object
	 */
	private ConcurrentHashMap<JButton, Container> containerMap;

	/**
	 * Stages of the home screen; 0 = home 1 = edit screen 2= view conntainers
	 */
	private int stage;

	/**
	 * main components of edit view
	 */
	private JPanel editPanel = new JPanel();

	/**
	 * Hold components of editView
	 */
	private JPanel editNameOfContainerPanel = new JPanel();

	/**
	 * Title panel of edit view
	 */
	private JPanel editNameOfContainerTitlePanel = new JPanel();

	/**
	 * Text of edit view
	 */
	private JLabel editNameLabel = new JLabel("Click the Container Button you wish to rename");;

	/**
	 * Holds buttons of edit view
	 */
	private JPanel editGUIButtonsPanel = new JPanel();

	/**
	 * Button to go back from edit screen to home screen
	 */
	private JButton editBackToContainerView = new JButton("Back");;

	/**
	 * Panel to hold container buttons
	 */
	private JPanel editContainerButtonsPanel = new JPanel();

	/**
	 * Add text for name of container
	 */
	private JTextField newContainerText = new JTextField(40);;

	/**
	 * Button to go to container list view
	 */
	private JButton viewContainers = new JButton("View Containers");

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
	 * Panel to hold delete view components
	 */
	private JPanel deletePanel = new JPanel();

	/**
	 * Panel to hold delete view title
	 */
	private JPanel deleteContainerTitlePanel = new JPanel();

	/**
	 * Label to tell to click on button to delete
	 */
	private JLabel deleteLabel = new JLabel("Click the Container button you wish to delete");

	/**
	 * Panel to hold delete view container buttons
	 */
	private JPanel deleteGUIButtonsPanel = new JPanel();

	/**
	 * Button to go back from delete view to containers view
	 */
	private JButton deleteBackToContainerView = new JButton("Back");

	/**
	 * Panel to hold delete view buttons
	 */
	private JPanel deleteContainerButtonsPanel = new JPanel();

	private static HomeView home;
	
	public static void main(String[] args) {
		home = new HomeView();

	}
	
	public static HomeView getHomeView() {
		return home;
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

		// Initialise all actionlisteners here
		newContainerText.addActionListener(this);
		createContainer.addActionListener(this);
		viewContainers.addActionListener(this);
		viewOfContainer2HomeButton.addActionListener(this);
		editContainerNameButton.addActionListener(this);
		editBackToContainerView.addActionListener(this);
		deleteContainerButton.addActionListener(this);
		deleteBackToContainerView.addActionListener(this);

		stage = 0; // home stage
		ContainerUtility.initContainers(containerMap, data, this);
		changeStageOfHome();
	}
	
	public void setHomeViewVisibility(boolean b) {
		if (b == false) {
			homePanel.setVisible(false);
		}
	}

	/**
	 * 
	 * Changes the stage of the home screen between the main view, the Container
	 * list view, edit container view and delete container view
	 */
	public void changeStageOfHome() {
		if (stage == 0) { // Home screen
			editPanel.setVisible(false);
			viewOfContainerPanel.setVisible(false);
			deletePanel.setVisible(false);

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

		} else if (stage == 1) { // Edit name of container screen
			homePanel.setVisible(false);
			viewOfContainerPanel.setVisible(false);
			deletePanel.setVisible(false);

			editPanel.setLayout(null);
			frame.add(editPanel);

			editNameOfContainerPanel.setBounds(0, 0, 800, 80);
			editNameOfContainerPanel.setBackground(new Color(203, 253, 232));
			editPanel.add(editNameOfContainerPanel);

			editNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
			editNameOfContainerPanel.add(editNameLabel);

			editGUIButtonsPanel.setBackground(new Color(203, 253, 232));
			editGUIButtonsPanel.setBounds(0, frame.getHeight() - 90, 800, 90);
			editPanel.add(editGUIButtonsPanel);

			editGUIButtonsPanel.add(editBackToContainerView);

			editContainerButtonsPanel.setBounds(0, 80, 800, 500);
			editContainerButtonsPanel.setBackground(new Color(203, 253, 232));
			editContainerButtonsPanel.setLayout(new FlowLayout());
			addContainerButtons(editContainerButtonsPanel);

			editPanel.add(editContainerButtonsPanel);
			editPanel.setVisible(true);
		}

		else if (stage == 2) { // See list of containers as buttons
			homePanel.setVisible(false);
			deletePanel.setVisible(false);
			editPanel.setVisible(false);

			frame.add(viewOfContainerPanel);
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

		else if (stage == 3) { // delete Container view
			homePanel.setVisible(false);
			viewOfContainerPanel.setVisible(false);
			editPanel.setVisible(false);

			deletePanel.setLayout(null);
			frame.add(deletePanel);

			deleteContainerTitlePanel.setBounds(0, 0, 800, 80);
			deleteContainerTitlePanel.setBackground(new Color(253, 206, 203));
			deletePanel.add(deleteContainerTitlePanel);

			deleteLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
			deleteContainerTitlePanel.add(deleteLabel);

			deleteGUIButtonsPanel.setBackground(new Color(253, 206, 203));
			deleteGUIButtonsPanel.setBounds(0, frame.getHeight() - 90, 800, 90);
			deletePanel.add(deleteGUIButtonsPanel);

			deleteGUIButtonsPanel.add(deleteBackToContainerView);

			deleteContainerButtonsPanel.setBounds(0, 80, 800, 500);
			deleteContainerButtonsPanel.setBackground(new Color(253, 206, 203));
			deleteContainerButtonsPanel.setLayout(new FlowLayout());
			addContainerButtons(deleteContainerButtonsPanel);

			deletePanel.add(deleteContainerButtonsPanel);
			deletePanel.setVisible(true);

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
	 * Dynamically adds container buttons to the specified panel based on the
	 * current containerMap state.
	 * 
	 * @param p The panel to which container buttons will be added.
	 */
	private void addContainerButtons(JPanel p) {
		p.removeAll();
		containerMap.forEach((button, container) -> {
			p.add(button);
			button.addActionListener(this);
		});
		p.revalidate(); // refresh panel
	}

	/**
	 * Initiates the container renaming process for a given container button.
	 * 
	 * @param b The button corresponding to the container to be renamed.
	 */
	private void renameContainerButton(JButton b) {
		Container c = containerMap.get(b);

		String nameOfContainer = JOptionPane.showInputDialog(frame,
				"What would you like to rename container " + c.getName() + " to?");

		ContainerUtility.verifyEditContainer(c.getName(), nameOfContainer, data, b, containerMap,
				(errorMsg) -> JOptionPane.showMessageDialog(frame, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE),
				() -> {
					c.setName(nameOfContainer);
					// Update the button text directly instead of replacing the button in the map
					b.setText(c.getName());
					stage = 0;
					changeStageOfHome();
				});
	}

	/**
	 * Deletes a container from the data and its map
	 * 
	 * @param b the button corresponding to the container to be deleted
	 */
	public void deleteContainerButton(JButton b) {
		Container c = containerMap.get(b);
		int opt = JOptionPane.showConfirmDialog(frame, "Delete Container \"" + c.getName() + "\"?");
		if (opt == JOptionPane.YES_OPTION) { // if not cancelled
			ContainerUtility.verifyDeleteContainer(c.getName(), data, b, containerMap, (errorMsg) -> JOptionPane
					.showMessageDialog(frame, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE), () -> {
						stage = 0;
						changeStageOfHome();
					});

			c = null;

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

		if (stage == 0) { // home screen
			if (source == createContainer) {
				addNewContainer();
			} else if (source == viewContainers) {
				stage = 2;
				changeStageOfHome();
			}

		} else if (stage == 1) { // edit name screen
			if (source == editBackToContainerView) {
				stage = 2;
				changeStageOfHome();
			} else {
				Container c = containerMap.get(source); // This will return null if the button is not found
				if (c != null) {
					renameContainerButton((JButton) source);
				}
			}
		}

		else if (stage == 2) { // view containers screen
			if (source == viewOfContainer2HomeButton) {
				stage = 0;
				changeStageOfHome();
			} else if (source == editContainerNameButton) {
				stage = 1;
				changeStageOfHome();
			} else if (source == deleteContainerButton) {
				stage = 3;
				changeStageOfHome();
			} else {
				Container c = containerMap.get(source); // This will return null if the button is not found
				if (c != null) {
					c.getGUI();
				}

			}
		}

		else if (stage == 3) {
			if (source == deleteBackToContainerView) {
				stage = 2;
				changeStageOfHome();
			} else {
				Container c = containerMap.get(source); // This will return null if the button is not found
				if (c != null) {
					deleteContainerButton((JButton) source);
				}
			}

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
}
