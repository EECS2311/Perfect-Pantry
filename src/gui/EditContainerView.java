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

public class EditContainerView implements ActionListener{

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
	 * Holds current instance of EditContainerView
	 */
	private static EditContainerView editContainers;


	/**
	 * Launches the application and initializes the main GUI components.
	 */
	public EditContainerView() {
		editContainers = this;
	}

	/**
	 * Sets the visibility of the EditContainerView GUI depending on the boolean passed
	 * 
	 * @param b the value of whether the visibility is true or not
	 */
	public void setEditContainerViewVisibility(boolean b) {
		if (b == true) {
			HomeView.getHomeView().setHomeViewVisibility(false);
			SeeContainersView.getContainersView().setSeeContainersViewVisibility(false);
			DeleteContainerView.getDeleteContainerView().setDeleteContainerViewVisibility(false);

			// Initialise all actionlisteners here
			editBackToContainerView.addActionListener(this);
			
			editPanel.setLayout(null);
			HomeView.getFrame().add(editPanel);

			editNameOfContainerPanel.setBounds(0, 0, 800, 80);
			editNameOfContainerPanel.setBackground(new Color(203, 253, 232));
			editPanel.add(editNameOfContainerPanel);

			editNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
			editNameOfContainerPanel.add(editNameLabel);

			editGUIButtonsPanel.setBackground(new Color(203, 253, 232));
			editGUIButtonsPanel.setBounds(0, HomeView.getFrame().getHeight() - 90, 800, 90);
			editPanel.add(editGUIButtonsPanel);

			editGUIButtonsPanel.add(editBackToContainerView);

			editContainerButtonsPanel.setBounds(0, 80, 800, 500);
			editContainerButtonsPanel.setBackground(new Color(203, 253, 232));
			editContainerButtonsPanel.setLayout(new FlowLayout());
			SeeContainersView.getContainersView().addContainerButtons(editContainerButtonsPanel);

			editPanel.add(editContainerButtonsPanel);
			editPanel.setVisible(true);

		}
		if (b == false) {
			// Remove ActionListeners
			editBackToContainerView.removeActionListener(this);
			HomeView.getContainerMap().forEach((button, container) -> {
				button.removeActionListener(this);
			});
			editPanel.setVisible(false);
		}
	}

	/**
	 * Initiates the container renaming process for a given container button.
	 * 
	 * @param b The button corresponding to the container to be renamed.
	 */
	public void renameContainerButton(JButton b) {
		Container c = HomeView.getContainerMap().get(b);

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


	/**
	 * Handles action events triggered by various GUI components.
	 * This method is the central hub for processing user interactions within the home view.
	 *
	 * @param e The ActionEvent object containing details about the event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == editBackToContainerView) {
			SeeContainersView.getContainersView().setSeeContainersViewVisibility(true);
		} else {
			Container c = HomeView.getContainerMap().get(source); // This will return null if the button is not found
			if (c != null) {
				renameContainerButton((JButton) source);
			}
		}



	}

	/**
	 * Provide Accces to this instance of EditContainerView
	 * 
	 * @return the current instance of EditContainerView
	 */
	public static EditContainerView getEditContainerView() {
		return editContainers;
	}

}



