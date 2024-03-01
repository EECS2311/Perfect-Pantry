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

public class DeleteContainerView implements ActionListener {

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

	private static DeleteContainerView deleteView;


	/**
	 * Launches the application and initializes the main GUI components.
	 */
	public DeleteContainerView() {
		deleteView = this;
		deleteBackToContainerView.addActionListener(this);

	}

	public void setDeleteContainerViewVisibility(boolean b) {
		if(b == true) {
			HomeView.getHomeView().setHomeViewVisibility(false);
			SeeContainersView.getContainersView().setSeeContainersViewVisibility(false);
			EditContainerView.getEditContainerView().setEditContainerViewVisibility(false);

			deletePanel.setLayout(null);
			HomeView.getFrame().add(deletePanel);

			deleteContainerTitlePanel.setBounds(0, 0, 800, 80);
			deleteContainerTitlePanel.setBackground(new Color(253, 206, 203));
			deletePanel.add(deleteContainerTitlePanel);

			deleteLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
			deleteContainerTitlePanel.add(deleteLabel);

			deleteGUIButtonsPanel.setBackground(new Color(253, 206, 203));
			deleteGUIButtonsPanel.setBounds(0, HomeView.getFrame().getHeight() - 90, 800, 90);
			deletePanel.add(deleteGUIButtonsPanel);

			deleteGUIButtonsPanel.add(deleteBackToContainerView);

			deleteContainerButtonsPanel.setBounds(0, 80, 800, 500);
			deleteContainerButtonsPanel.setBackground(new Color(253, 206, 203));
			deleteContainerButtonsPanel.setLayout(new FlowLayout());
			addContainerButtons(deleteContainerButtonsPanel);

			deletePanel.add(deleteContainerButtonsPanel);
			deletePanel.setVisible(true);

		}
		
		if (b == false) {
			deletePanel.setVisible(false);
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
		HomeView.getContainerMap().forEach((button, container) -> {
			p.add(button);
			button.addActionListener(this);
		});
		p.revalidate(); // refresh panel
	}

	/**
	 * Deletes a container from the data and its map
	 * 
	 * @param b the button corresponding to the container to be deleted
	 */
	public void deleteContainerButton(JButton b) {
		Container c = HomeView.getContainerMap().get(b);
		int opt = JOptionPane.showConfirmDialog(HomeView.getFrame(), "Delete Container \"" + c.getName() + "\"?");
		if (opt == JOptionPane.YES_OPTION) { // if not cancelled
			ContainerUtility.verifyDeleteContainer(c.getName(), HomeView.data, b, HomeView.getContainerMap(), (errorMsg) -> JOptionPane
					.showMessageDialog(HomeView.getFrame(), errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE), () -> {
						HomeView.getHomeView().setHomeViewVisibility(true);
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

		if (source == deleteBackToContainerView) {
			SeeContainersView.getContainersView().setSeeContainersViewVisibility(true);
		} else {
			Container c = HomeView.getContainerMap().get(source); // This will return null if the button is not found
			if (c != null) {
				deleteContainerButton((JButton) source);
			}
		}

	}
	
	public static DeleteContainerView getDeleteContainerView() {
		return deleteView;
	}





}
