package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.DB;

class AddContainerTest {

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
			s.execute("INSERT into container (container_name) VALUES('MainPantry'), ('NewPantry')");

			ResultSet result = s.executeQuery("Select * from container");

			while (result.next()) {
				l.add(result.getString("container_name"));
			}

		} catch (SQLException e) {

		}

		assertEquals(l, l2);

	}

}
