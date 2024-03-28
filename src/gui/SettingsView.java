package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.logic.Settings;

public class SettingsView extends Settings {

	JPanel settings = new JPanel();

	JButton close = new JButton("Close Settings");

	JButton notifOn = new JButton("Notification On/Off");

	JLabel notifStatus = new JLabel();

	JLabel fontSizeLabel = new JLabel("Fontsize");

	JButton decrease = new JButton("-");

	JLabel fontSizeCurrent = new JLabel();

	JButton increase = new JButton("+");
	
	JPanel homePanel;

	int lowestFontSize = 10;

	int highestFontSize = 50;
	
	



	public SettingsView(JPanel homePanel) {
		this.homePanel = homePanel;
		this.homePanel.add(settings);
		settings.setBounds(10, 60, 200, 300);
		settings.setBackground(Color.PINK);
		settings.setLayout(new BorderLayout(8, 20));
		settings.setBackground(new Color(253, 241, 203));
		settings.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(253, 241, 203));
		northPanel.add(notifOn);
		northPanel.add(notifStatus);
		
		settings.add(northPanel, BorderLayout.NORTH);
		notifOn.setSize(150, 20);
		notifOn.addActionListener(e -> {
			setNotificationBoolean(!getNotificationBoolean());
			setNotifStatus();
		});

		setNotifStatus();

		settings.add(fontSizeLabel, BorderLayout.CENTER);

		settings.add(decrease, BorderLayout.WEST);
		decrease.addActionListener(e -> {
			int fontSize = getFontSize() - 1;
			setFontSizeCurrent(fontSize);
		});

		settings.add(fontSizeCurrent, BorderLayout.CENTER);
		setFontSizeCurrent(getFontSize());

		settings.add(increase, BorderLayout.EAST);
		increase.addActionListener(e -> {
			int fontSize = getFontSize() + 1;
			setFontSizeCurrent(fontSize);
		});
		
		settings.add(close, BorderLayout.SOUTH);
		close.addActionListener(e ->{
			settings.setVisible(false);
		});
		
		settings.setSize(settings.getPreferredSize());



	}

	public void setNotifStatus() {
		boolean notifBool = getNotificationBoolean();
		if(notifBool) {
			notifStatus.setText("On");
		}
		else {
			notifStatus.setText("Off");
		}

		settings.repaint();
	}

	public void setFontSizeCurrent(int n) {
		if(n >= lowestFontSize && n <= highestFontSize) {
			setFontSize(n);
			fontSizeCurrent.setText(Integer.toString(n));
			fontSizeCurrent.setFont(new Font("Lucida Grande", Font.PLAIN, n));;
			
			settings.setSize(settings.getPreferredSize());
			settings.repaint();
		}
	}


}
