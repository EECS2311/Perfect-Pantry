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

public class DeleteItemView implements ActionListener {

	private JFrame deleteFrame;
	private JPanel deletePanel;
	private JButton confirm;
	private JLabel t;
	private JTextField searchField;

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
