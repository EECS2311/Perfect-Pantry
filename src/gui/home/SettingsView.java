package gui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsView  implements ActionListener{

	/**
	 * Main panel for settingsView
	 */
	private JPanel settings = new JPanel();

	/**
	 * Closes settings panel
	 */
	private JButton close = new JButton("Close Settings");

	/**
	 * toggles notifications on or off
	 */
	private JButton notifOn = new JButton("Notification On/Off");

	/**
	 * Displays current value of notifOn
	 */
	private JLabel notifStatus = new JLabel();

	/**
	 * Decrease font size by 1
	 */
	private JButton decrease = new JButton("-");

	/**
	 * Display current fontSize
	 */
	private JLabel fontSizeCurrent = new JLabel();

	/**
	 * increase fontsize by 1
	 */
	private JButton increase = new JButton("+");

	/**
	 * Homepanel to add settings on top of
	 */
	private JPanel homePanel;

	/**
	 * Lowest font point
	 */
	private int lowestFontSize = 10;

	/** 
	 * highest font point
	 */
	private int highestFontSize = 50;

	/**
	 * Able to get settingsView
	 */
	private static SettingsView settingsView;
	
	/**
	 * Panel to hold components in the north part of BorderLayout
	 */
	private JPanel northPanel = new JPanel();


	/**
	 * Instaiates SettingsView
	 * @param homePanel The Panel the settingsView should be on
	 */
	public SettingsView(JPanel homePanel) {
		settingsView = this;
		this.homePanel = homePanel;
	}

	/**
	 * Sets the visibility of SettingsView
	 * @param b Boolean to know if visibility is true or not
	 */
	public void setSettingsViewVisibility(boolean b) {
		if(b) {
			this.homePanel.add(settings, 0);

			settings.setBounds(10, 80, 200, 300);
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

	/**
	 * Sets the notifcation status label value
	 */
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

	/**
	 * Sets the font sizes of the view
	 * @param n the font size it should be set to
	 */
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
			HomeView.getHomeView().addFonts();
			homePanel.repaint();
			

		}
	}

	/**
	 * Provides access to the settingsView
	 * @return the current access of settingsView
	 */
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
