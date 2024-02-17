package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.logic.Container;

/**
 * Provides a graphical interface for deleting an item from a specified container.
 * This class displays a dialog asking the user to enter the name of the item they wish to delete
 * and performs the deletion if the item exists within the container.
 */
public class DeleteItemView implements ActionListener {

	private JFrame deleteFrame;
	private JPanel deletePanel;
	private JButton confirm;
	private JLabel t;
	private JTextField searchField;

	/**
	 * Constructs a DeleteItemView and initiates the item deletion process.
	 * @param containerPanel The panel from which the delete action was initiated.
	 * @param c The container from which an item is to be deleted.
	 * @param itemsListPanel The Itemslist panel showing the list of items.
	 */
	public DeleteItemView(JPanel containerPanel, Container c, Itemslist itemsListPanel) {

		String item = null;
		Boolean success;

		item = JOptionPane.showInputDialog(containerPanel, "Type the name of the item to delete",
				"*Delete Item from Pantry", 3);

		success = verifyItem(item, c, itemsListPanel);

		if (success) {
			JOptionPane.showMessageDialog(containerPanel, "Item Successfully Deleted!", item, 3);
			item = null;

		} else {
			if (item != null) {
				JOptionPane.showMessageDialog(containerPanel, "This item does not exist in your pantry!", item, 0);
			}

		}

	}

	/**
	 * Verifies and deletes an item from the specified container and updates the Itemslist panel.
	 * @param itemName The name of the item to verify and delete.
	 * @param con The container from which to delete the item.
	 * @param list The Itemslist panel to update after deletion.
	 * @return Boolean indicating the success or failure of the item deletion.
	 */
	public Boolean verifyItem(String itemName, Container con, Itemslist list) {

		// Checking to see if item is in the database
		if (Home.data.getItem(con, itemName) != null) {
			// Remove the item if its present
			Home.data.removeItem(con, itemName, null);

			// Remove the item from the datalist
			list.removeItem(itemName);
			return true;
		} else
			return false;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
