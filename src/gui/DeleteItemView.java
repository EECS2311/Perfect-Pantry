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

	public DeleteItemView(JPanel containerPanel) {

		String item = null;
		Boolean success;
		Container c;

		do {
			item = JOptionPane.showInputDialog(containerPanel, "Type the name of the item to delete",
					"*Delete Item from Pantry", 3);
			if (item == null) {
				break;
			}

			success = verifyItem(item);

			if (success) {
				JOptionPane.showMessageDialog(containerPanel, "Item Successfully Deleted!", item, 3);
				item = null;

			} else {
				JOptionPane.showMessageDialog(containerPanel, "This item does not exist in your pantry!", item, 0);
			}

		} while (item != null);

	}

	public Boolean verifyItem(String itemName) {
		return false;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
