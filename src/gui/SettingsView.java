package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.logic.Settings;

public class SettingsView  implements ActionListener{

	JPanel settings = new JPanel();

	JButton close = new JButton("Close Settings");

	JButton notifOn = new JButton("Notification On/Off");

	JLabel notifStatus = new JLabel();

	JButton decrease = new JButton("-");

	JLabel fontSizeCurrent = new JLabel();

	JButton increase = new JButton("+");

	JPanel homePanel;

	int lowestFontSize = 10;

	int highestFontSize = 50;

	private static SettingsView settingsView;
	
	JPanel northPanel = new JPanel();


	public SettingsView(JPanel homePanel) {
		settingsView = this;
		this.homePanel = homePanel;
	}

	public void setSettingsViewVisibility(boolean b) {
		if(b) {
			this.homePanel.add(settings, 0);

			settings.setBounds(10, 60, 200, 300);
			settings.setBackground(Color.PINK);
			settings.setLayout(new BorderLayout(8, 20));
			settings.setBackground(new Color(253, 241, 203));
			settings.setBorder(BorderFactory.createLineBorder(Color.black));

			northPanel.setBackground(new Color(253, 241, 203));
			northPanel.add(notifOn);
			northPanel.add(notifStatus);

			settings.add(northPanel, BorderLayout.NORTH);
			notifOn.setSize(150, 20);

			setNotifStatus();

			settings.add(decrease, BorderLayout.WEST);
			settings.add(fontSizeCurrent, BorderLayout.CENTER);
			settings.add(increase, BorderLayout.EAST);
			settings.add(close, BorderLayout.SOUTH);

			setFontSizeCurrent(HomeView.getSettings().getFontSize());
			settings.setSize(settings.getPreferredSize());
			
			close.addActionListener(this);
			notifOn.addActionListener(this);
			decrease.addActionListener(this);
			increase.addActionListener(this);
			
			settings.setVisible(true);
		}
		else {
			settings.setVisible(false);
			close.removeActionListener(this);
			notifOn.removeActionListener(this);
			decrease.removeActionListener(this);
			increase.removeActionListener(this);
		
		}

	}

	public void setNotifStatus() {
		boolean notifBool = HomeView.getSettings().getNotificationBoolean();
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
			HomeView.getSettings().setFontSize(n);
			Font f = new Font("Lucida Grande", Font.PLAIN, n);
			fontSizeCurrent.setText(Integer.toString(n));
			fontSizeCurrent.setFont(f);
			close.setFont(f);
			notifOn.setFont(f);
			notifStatus.setFont(f);
			decrease.setFont(f);
			increase.setFont(f);

			settings.setSize(settings.getPreferredSize());
			settings.repaint();

		}
	}

	public static SettingsView getSettingsView() {
		return settingsView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == notifOn) {
			HomeView.getSettings().setNotificationBoolean(!HomeView.getSettings().getNotificationBoolean());
			setNotifStatus();

		}

		else if (e.getSource() == decrease) {
			int fontSize = HomeView.getSettings().getFontSize() - 1;
			setFontSizeCurrent(fontSize);

		}

		else if (e.getSource() == increase) {
			int fontSize = HomeView.getSettings().getFontSize() + 1;
			setFontSizeCurrent(fontSize);
		}

		else if (e.getSource() == close) {
			setSettingsViewVisibility(false);
			

		}
	}



}
