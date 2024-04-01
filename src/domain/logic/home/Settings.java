package domain.logic.home;

import gui.home.HomeView;

public class Settings {
	private int fontSize;

	private boolean notificationBoolean;

	private String [] settings;


	public Settings() {
		getSettings();

		fontSize = Integer.parseInt(settings[0]);
		notificationBoolean = Boolean.parseBoolean(settings[1]);

	}

	public void getSettings() {
		settings = HomeView.data.getSettings();
	}

	public void setFontSize(int n) {
		if (n>= 10 && n <= 50) {
			HomeView.data.setFontsize(n);
			fontSize = n;
		}
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setNotificationBoolean(boolean b) {
		HomeView.data.setNotificationBoolean(b);
		notificationBoolean = b;
	}

	public boolean getNotificationBoolean() {
		return notificationBoolean;
	}



}
