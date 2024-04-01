package test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domain.logic.home.Settings;

class SettingsTest {

	@Test
	void getSettingsTest() {
		Settings s = new Settings();
		
		s.setFontSize(30);
		s.setNotificationBoolean(false);
		
		assertEquals(30, s.getFontSize());
		assertEquals(false, s.getNotificationBoolean());
		
	}
	
	@Test
	void setFontsizeLessThanMax() {
		Settings s = new Settings();
		int original = s.getFontSize();
		
		s.setFontSize(3);
		s.setNotificationBoolean(false);
		
		assertEquals(original, s.getFontSize());
		assertEquals(false, s.getNotificationBoolean());
		
	}
	
	@Test
	void setNotificationBoolean() {
		Settings s = new Settings();
		s.setNotificationBoolean(true);
		assertEquals(true, s.getNotificationBoolean());
		
	}
	


}
