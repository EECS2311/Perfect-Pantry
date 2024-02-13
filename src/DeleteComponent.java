import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteComponent implements ActionListener {

	private JFrame deleteFrame;
	private JPanel deletePanel;
	private JButton confirm;
	private JLabel t;
	private JTextField searchField;

	public DeleteComponent() {

		// Setup Jframe
		deleteFrame = new JFrame("*Delete Item from Pantry");
		String item = JOptionPane.showInputDialog(deleteFrame, "Type the name of the item to delete",
				"*Delete Item from Pantry", 3);

		deleteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new DeleteComponent();

	}

	public void verifyItem() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
