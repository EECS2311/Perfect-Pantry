package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteItemView implements ActionListener {

	private JFrame deleteFrame;
	private JPanel deletePanel;
	private JButton confirm;
	private JLabel t;
	private JTextField searchField;

	public DeleteItemView() {

		// Setup Jframe
		deleteFrame = new JFrame("*Delete Item from Pantry");
		deletePanel = new JPanel();
		String item;

		do {
			item = JOptionPane.showInputDialog(deletePanel, "Type the name of the item to delete",
					"*Delete Item from Pantry", 3);

			if (verifyItem(item) == false) {

				JOptionPane.showMessageDialog(deletePanel, "This item does not exist in your pantry!", item, 0);
			}

		} while (item != null);

		JOptionPane.showMessageDialog(deleteFrame, "Item Successfully Deleted!", item, 3);

		deleteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new DeleteItemView();

	}

	public Boolean verifyItem(String itemName) {

		return false;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
