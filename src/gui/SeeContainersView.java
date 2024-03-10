package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import domain.logic.Container;
import domain.logic.ContainerUtility;

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
	 * Popup for edit or deleting container button
	 */
	private JPopupMenu popup;
	
	/**
	 * Option for popup to rename container button
	 */
	private JMenuItem renameContainerBtn = new JMenuItem("Rename Container");
	
	/**
	 * Option for container to remove container button
	 */
	private JMenuItem removeContainerBtn = new JMenuItem("Delete Container");
	
	/**
	 * Holds button which has been pressed
	 */
	private JButton buttonPressed;
	
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
			viewOfContainer2HomeButton.addActionListener(this);

			HomeView.getFrame().add(viewOfContainerPanel);
			viewOfContainerPanel.setLayout(null);
			viewOfContainerPanel.setBackground(new Color(253, 241, 203));

			backPanel.setBackground(new Color(253, 241, 203));
			viewOfContainerPanel.add(backPanel);
			backPanel.setLayout(new FlowLayout());
			backPanel.setBounds(0, 0, 800, 50);

			backPanel.add(viewOfContainer2HomeButton);

			containerButtonsPanel.setBounds(0, 50, 800, 500);
			containerButtonsPanel.setBackground(new Color(253, 241, 200));
			containerButtonsPanel.setLayout(new GridLayout((HomeView.getContainerMap().size()/3 +1), 3));


			containerButtonsPanel.removeAll();

			popup = new JPopupMenu();
			removeContainerBtn = new JMenuItem("Delete Container");
			renameContainerBtn = new JMenuItem("Rename Container");
			renameContainerBtn.addActionListener(this);
			removeContainerBtn.addActionListener(this);

			popup.add(renameContainerBtn);
			popup.add(removeContainerBtn);

			HomeView.getContainerMap().forEach((button, container) -> {
				containerButtonsPanel.add(button);
				button.addActionListener(this);
				button.setComponentPopupMenu(popup);
				button.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent p) {
						Boolean bool = SwingUtilities.isRightMouseButton(p);
						if (bool) {	
							buttonPressed = button;
						}
					}

				});
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
			renameContainerBtn.removeActionListener(this);
			removeContainerBtn.removeActionListener(this);
			viewOfContainer2HomeButton.removeActionListener(this);
			viewOfContainerPanel.setVisible(false);

		}
	}

	/**
	 * Initiates the container renaming process for a given container button.
	 * 
	 * @param b The button corresponding to the container to be renamed.
	 */
	public void renameContainer(JButton b) {
		Container c = HomeView.getContainerMap().get(b);
		if(c != null) {
			String nameOfContainer = JOptionPane.showInputDialog(HomeView.getFrame(),
					"What would you like to rename container " + c.getName() + " to?");

			ContainerUtility.verifyEditContainer(c.getName(), nameOfContainer, HomeView.data, b, HomeView.getContainerMap(),
					(errorMsg) -> JOptionPane.showMessageDialog(HomeView.getFrame(), errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE),
					() -> {
						c.setName(nameOfContainer);
						// Update the button text directly instead of replacing the button in the map
						b.setText(c.getName());
						HomeView.getHomeView().setHomeViewVisibility(true);
					});
		}
	}

	/**
	 * Deletes a container from the data and its map
	 * 
	 * @param b the button corresponding to the container to be deleted
	 */
	public void removeContainer(JButton b) {
		Container c = HomeView.getContainerMap().get(b);
		if(c!= null) {
			int opt = JOptionPane.showConfirmDialog(HomeView.getFrame(), "Delete Container \"" + c.getName() + "\"?");
			if (opt == JOptionPane.YES_OPTION) { // if not cancelled
				ContainerUtility.verifyDeleteContainer(c.getName(), HomeView.data, b, HomeView.getContainerMap(), (errorMsg) -> JOptionPane
						.showMessageDialog(HomeView.getFrame(), errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE), () -> {
							HomeView.getHomeView().setHomeViewVisibility(true);
						});

				c = null;

			}
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
		}

		else if(source == renameContainerBtn) {
			renameContainer(buttonPressed);
		}

		else if(source == removeContainerBtn) {
			removeContainer(buttonPressed);
		}
		else {
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
