package test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import database.DB;
import domain.logic.home.Settings;

class SettingsDBTest {

	
	@Test
	void getSettingsTest() {
		Settings s = new Settings();
		s.setFontSize(30);
		s.setNotificationBoolean(false);
		
		DB d = new DB();
		
		String [] settings = new String [2];
		Connection conn = d.init();
		if (conn != null) {
			try {
				String sql = "SELECT fontsize, notificationBoolean FROM settings";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while(rs.next()) {
					String font = rs.getString("fontsize").toLowerCase();
					String notifBool = rs.getString("notificationBoolean").toLowerCase();

					settings[0] = font;
					settings[1] = notifBool;
				}


				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			assertEquals(30, Integer.parseInt(settings[0]));
			assertEquals(false, Boolean.parseBoolean(settings[1]));
		}
		
	}
	
	@Test
	void setFontsizeTest() {

		DB d = new DB();
		int original = 20;
		boolean orignalBool = false;
		Connection conn = d.init();
		try {
			String query = "UPDATE settings SET fontsize = ? WHERE setting_type = 'User'";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, 30);
			p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Settings s = new Settings();
		assertNotEquals(original, s.getFontSize());
		assertEquals(orignalBool, s.getNotificationBoolean());
		
	}
	
	@Test
	void setBooleanTest() {
		
		DB d = new DB();
		int original = 20;
		boolean orignalBool = false;
		Connection conn = d.init();
		try {
			String query = "UPDATE settings SET notificationboolean = ? WHERE setting_type = 'User';";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, Boolean.toString(true));
			p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Settings s = new Settings();
		s.setFontSize(20);

		assertEquals(original, s.getFontSize());
		assertNotEquals(orignalBool, s.getNotificationBoolean());
		
	}


}
