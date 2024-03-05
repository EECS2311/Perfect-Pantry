package gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StorageTipView extends JPanel {

	JLabel title;
	JLabel tip;

	public StorageTipView(String t, String tp) {

		JDialog d = new JDialog(HomeView.getFrame());

		d.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);

		title = new JLabel(t + " - Storage Tip");
		tip = new JLabel(tp);
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		d.add(title);
		d.add(tip);

		d.setSize(400, 600);
		d.setLocationRelativeTo(HomeView.getFrame());
		d.setBounds(400, 0, 400, 600);
		d.setLayout(new FlowLayout());
		d.getContentPane().setBackground(new Color(253, 241, 203));
		// d.setUndecorated(true);
		d.setResizable(false);
		d.setVisible(true);

	}

}
