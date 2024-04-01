package gui.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import database.DB;
import database.StubDB;
import domain.logic.container.Container;
import domain.logic.container.ContainerUtility;
import domain.logic.home.Settings;
import domain.logic.item.ItemUtility;
import gui.AddItemHomeView;
import gui.SeeContainersView;
import gui.grocery.GroceryListView;
import gui.recipe.RecipeListView;
import gui.recipe.StarredRecipeListView;
import gui.statistics.StatsView;

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
	 * Provide Access to stub Database
	 */
	public static StubDB stubData = new StubDB();

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
	private JLabel titleLabel = new JLabel("Perfect Pantry");

	/**
	 * Button that will add new container, apart of homeButtonsPanel
	 */
	private JButton createContainer = new JButton("Create");

	/**
	 * Add text for name of container
	 */
	private JTextField newContainerText = new JTextField(40);

	/**
	 * Button to go to container list view
	 */
	private JButton viewContainers = new JButton("View Containers");

	// Button for the grocery list
	private JButton groceryListButton = new JButton("Grocery List");

	private JButton recipeListButton = new JButton("Recipe List");


	private JButton settingsButton = new JButton("Settings");

	private static Settings setting;

	private JButton statisticsButton = new JButton("Statistics");

	private JButton starredRecipeListButton = new JButton("Starred Recipes");

	private JPanel buttonsPanel = new JPanel();

	private JScrollPane scrollButtonsPanel  = new JScrollPane(buttonsPanel);

	private JButton addItemButton = new JButton("Add Item");


	/**
	 * Holds this instance of HomeView
	 */
	private static HomeView home;

	public static void main(String[] args) {
		setting = new Settings();

		// initialise other views
		SeeContainersView m = new SeeContainersView();
		GroceryListView g = new GroceryListView();
		home = new HomeView();
	}

	/**
	 * Launches the application and initializes the main GUI components.
	 */
	public HomeView() {
		SettingsView s = new SettingsView(homePanel);
		String[] settings = data.getSettings();
		boolean notificationOn = Boolean.parseBoolean(settings[1]);

		// Initialize frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Close on exit
		frame.setVisible(true);
		frame.setResizable(false); // stop resize
		frame.setMinimumSize(new Dimension(800, 600));
		frame.getContentPane().setBackground(new Color(245, 223, 162));

		// Initialize containerMap
		containerMap = new ConcurrentHashMap<>();
		ContainerUtility.initContainers(containerMap, data, this);

		setHomeViewVisibility(true);

		ItemUtility.updateFreshness(HomeView.data);

		if(notificationOn) {
			new NotificationView();
		}

	}

	/**
	 * Sets the HomeView Gui components visibility depending on the boolean passed
	 * through
	 * 
	 * @param b, Value whether gui should be visible or not
	 */
	public void setHomeViewVisibility(boolean b) {
		if (b) {
			// Set other gui visibility to false
			SeeContainersView.getContainersView().setSeeContainersViewVisibility(false);

			// Remove existing action listeners, prevents creating multiple containers
			newContainerText.removeActionListener(this);
			createContainer.removeActionListener(this);
			viewContainers.removeActionListener(this);
			groceryListButton.removeActionListener(this);
			recipeListButton.removeActionListener(this);
			starredRecipeListButton.removeActionListener(this);
			statisticsButton.removeActionListener(this);


			// Initialise all actionlisteners
			newContainerText.addActionListener(this);
			createContainer.addActionListener(this);
			viewContainers.addActionListener(this);
			groceryListButton.addActionListener(this);

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

			homePanel.add(scrollButtonsPanel);
			scrollButtonsPanel.setBounds(570, 10, 200, 150);
			buttonsPanel.setLayout(new GridLayout(4, 1));
			buttonsPanel.setBackground(new Color(253, 241, 203));

			buttonsPanel.add(groceryListButton);
			groceryListButton.setBackground(new Color(76, 183, 242));
			groceryListButton.setBounds(570, 10, 200, 40);

			buttonsPanel.add(recipeListButton);
			recipeListButton.setBackground(new Color(76, 183, 242));
			recipeListButton.setBounds(570, 60, 200, 40);
			recipeListButton.addActionListener(this);


			homePanel.add(settingsButton);
			settingsButton.setBackground(new Color(76, 183, 242));
			settingsButton.setBounds(10, 10, 205, 60);
			settingsButton.addActionListener(this);

			buttonsPanel.add(starredRecipeListButton);
			starredRecipeListButton.setBackground(new Color(76, 183, 242));
			starredRecipeListButton.setBounds(570, 100, 200, 40);
			starredRecipeListButton.addActionListener(this);

			buttonsPanel.add(statisticsButton);
			statisticsButton.setBackground(new Color(76, 183, 242));
			statisticsButton.setBounds(570, 150, 200, 40); // Adjust the positioning as needed
			statisticsButton.addActionListener(this);

			homePanel.add(addItemButton);
			addItemButton.addActionListener(this);
			addItemButton.setBounds(240, 350, 250, 40);  // Adjust the positioning as needed

			addFonts();

			homePanel.setVisible(true);
		}
		if (!b) {
			// RemoveActionListeners
			newContainerText.removeActionListener(this);
			createContainer.removeActionListener(this);
			viewContainers.removeActionListener(this);
			groceryListButton.removeActionListener(this);
			recipeListButton.removeActionListener(this);
			settingsButton.removeActionListener(this);
			starredRecipeListButton.removeActionListener(this);
			statisticsButton.removeActionListener(this);
			addItemButton.removeActionListener(this);

			homePanel.setVisible(false);
		}
	}

	/**
	 * Add fonts to components
	 */
	public void addFonts() {
		Font f = new Font("Lucida Grande", Font.PLAIN, getSettings().getFontSize());
		settingsButton.setFont(f);
		groceryListButton.setFont(f);
		recipeListButton.setFont(f);
		starredRecipeListButton.setFont(f);
		statisticsButton.setFont(f);
		
		newContainerText.setFont(f);
		viewContainers.setFont(f);
		createContainer.setFont(f);
		addItemButton.setFont(f);
		
		updateBounds(f.getSize());
		
	}
	
	/**
	 * Update bounds of components depending on font size
	 * @param size The current font size
	 */
	public void updateBounds(int size) {
		
		if(size >=30) {
			newContainerText.setBounds(160, 250, 410, 60);
			createContainer.setBounds(570, 250, 200, 60);
			viewContainers.setBounds(160, 310, 410, 60);
			addItemButton.setBounds(160, 380, 410, 60);
		}
		else if(size >= 22) {
			newContainerText.setBounds(240, 250, 250, 40);
			createContainer.setBounds(500, 250, 110, 40);
			viewContainers.setBounds(240, 300, 250, 40);
			addItemButton.setBounds(240, 350, 250, 40);
		}
		
		else {
			newContainerText.setBounds(240, 250, 250, 40);
			createContainer.setBounds(500, 250, 80, 40);
			viewContainers.setBounds(240, 300, 250, 40);
			addItemButton.setBounds(240, 350, 250, 40);
			
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
	 * Handles action events triggered by various GUI components. This method is the
	 * central hub for processing user interactions within the home view.
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
		} else if (source == groceryListButton) {
			GroceryListView.getGroceryListView().setGroceryListViewVisibility(true);
		} else if (source == recipeListButton) {
			RecipeListView.getInstance().setRecipeListViewVisibility(true);
		} else if(source == settingsButton) {
			SettingsView.getSettingsView().setSettingsViewVisibility(true);
		} else if (source == starredRecipeListButton) {
			StarredRecipeListView.getInstance().setRecipeListViewVisibility(true);
		} else if (source == statisticsButton) {
			StatsView.getInstance().setStatsViewVisibility(true);
		} else if (source == addItemButton) {
			openAddItemDialog();
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
	public static ConcurrentHashMap<JButton, Container> getContainerMap() {
		return containerMap;
	}

	public static Settings getSettings() {
		return setting;
	}



	private void openAddItemDialog() {
		AddItemHomeView dialog = new AddItemHomeView(frame);
	
	}

}
