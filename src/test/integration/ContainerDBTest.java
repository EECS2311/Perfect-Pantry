package test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.DB;
import domain.logic.Container;
import domain.logic.ContainerUtility;

class ContainerDBTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void addContainer() {
		DB data = new DB();

		Connection conn = data.init();
		List<String> l = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
		l2.add("MainPantry");
		l2.add("NewPantry");

		try {
			Statement s = conn.createStatement();

			// Remove all the table entries;
			s.execute("TRUNCATE container cascade");
			data.putContainer("MainPantry");
			data.putContainer("NewPantry");

			ResultSet result = s.executeQuery("Select * from container");

			while (result.next()) {
				l.add(result.getString("container_name"));
			}

		} catch (SQLException e) {

		}

		assertEquals(l, l2);

	}

	@Test
	void testDupeContainer() {

		DB data = new DB();

		Connection conn = data.init();
		try {
			Statement s = conn.createStatement();
			// Remove all the table entries;
			s.execute("TRUNCATE container cascade");
			data.putContainer("MainPantry");

			conn.close();
		} catch (SQLException e) {

		}

		ContainerUtility.verifyAddContainer("MainPantry", data, null, null,
				(errorMsg) -> assertEquals(errorMsg, "You cannot have duplicate containers!"), null);

	}

	@Test
	void emptyContainerTest() {
		DB data = new DB();

		Connection conn = data.init();
		try {
			Statement s = conn.createStatement();
			// Remove all the table entries;
			s.execute("TRUNCATE container cascade");
			data.putContainer("MainPantry");

			conn.close();
		} catch (SQLException e) {

		}

		ContainerUtility.verifyAddContainer("", data, null, null,
				(errorMsg) -> assertEquals(errorMsg, "Container Name cannot be empty!"), null);

	}

	@Test
	void deleteContainer() {

		DB data = new DB();

		Connection conn = data.init();
		try {
			Statement s = conn.createStatement();
			// Remove all the table entries;
			s.execute("TRUNCATE container cascade");
			data.putContainer("MainPantry");
			conn.close();

		} catch (SQLException e) {

		}

		ContainerUtility.verifyDeleteContainer("MainPantry", data, new JButton(),
				new ConcurrentHashMap<JButton, Container>(), null, null);

		assertFalse(data.findContainer("MainPantry"));

	}

}
