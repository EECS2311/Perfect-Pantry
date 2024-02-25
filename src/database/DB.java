package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.logic.Container;
import domain.logic.FoodFreshness;
import domain.logic.FoodGroup;
import domain.logic.Item;

/**
 * The {@code DB} class represents a simple database for storing and managing
 * containers and their associated items. This class provides methods to add and
 * retrieve containers and items, remove items, and print all items within
 * containers.
 */
public class DB {

	Connection conn;
	private HashMap<String, Container> containers = new HashMap<String, Container>();

	private HashMap<Container, HashMap<String, Item>> items = new HashMap<Container, HashMap<String, Item>>();

	/**
	 * Initializes a new database connection.
	 * 
	 * @return Returns the connection object to be used by other methods.
	 * 
	 */
	public Connection init() {
		try {
			conn = DriverManager.getConnection(info.url, info.dbUser, info.dbPass);
			return conn;
		} catch (SQLException e) {
			System.out.println("Connection Failure");
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * Inserts a new container into the database
	 * 
	 * @param nameOfContainer
	 */
	public void putContainer(String nameOfContainer) {

		Connection conn = init();
		try {
			Statement s = conn.createStatement();
			s.execute("INSERT into container (container_name) VALUES('" + nameOfContainer + "')");

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns a list of the containers currently stored in the database
	 * 
	 * @return A list of container names. The caller method will create the
	 *         containers
	 */
	public List<String> retrieveContainers() {

		Connection conn = init();
		try {
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery("Select * from container");
			List<String> l = new ArrayList<String>();

			while (result.next()) {
				l.add(result.getString("container_name"));
			}
			return l;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Verifies if the container is in the database
	 * 
	 * @param name The name of the database to be found.
	 * @return True or false depending on if the container is in the database.
	 */
	public boolean findContainer(String name) {
		Connection conn = init();

		try {
			Statement s = conn.createStatement();
			ResultSet result = s
					.executeQuery("select container_name from container WHERE container_name = '" + name + "'");

			Boolean b = result.next();
			conn.close();
			return b;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Removes container from the database
	 * 
	 * @param name The name of the database to be removed.
	 */
	public void removeContainer(String name) {
		Connection conn = init();

		try {
			Statement s = conn.createStatement();
			s.execute("DELETE from container WHERE container_name = '" + name + "'");
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Updates the name of a specific container
	 * 
	 * @param prevName The previous name of the container
	 * @param newName  The new name of the container
	 */
	public void editContainer(String prevName, String newName) {

		Connection conn = init();

		try {
			Statement s = conn.createStatement();
			s.execute("UPDATE container SET container_name = '" + newName + "' WHERE container_name = '" + prevName
					+ "'");
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Retrieves a {@link Container} by its name.
	 *
	 * @param containerName The name of the container to retrieve.
	 * @return The {@link Container} object if found, {@code null} otherwise.
	 */
	public Container getContainer(String containerName) {
		return containers.get(containerName);
	}

	/**
	 * Adds a new container to the database.
	 *
	 * @param containerName The name of the container to add.
	 * @param c             The {@link Container} object to be added.
	 */
	public void addContainer(String containerName, Container c) {
		containers.put(containerName, c);

	}

	/**
	 * Deletes container in database
	 *
	 * @param containerName The name of the container to deleted.
	 * @param c             The {@link Container} object to be deleted.
	 */
	public void deleteContainer(String containerName, Container c) {
		containers.remove(containerName, c);

	}

	/**
	 * Adds an item to a specific container.
	 *
	 * @param c    The container to which the item will be added.
	 * @param name The name of the item.
	 * @param ite  The {@link Item} object to be added.
	 */
	public void addItem(Container c, String name, Item ite) {
		
		Connection conn = init();
		
		try {
			Statement s = conn.createStatement();
			s.execute(String.format("INSERT INTO item VALUES('%s', '%s', "
					+ "'%d', '%s', '%s', '%s')", name, c.getName(), ite.getQuantity(), 
					ite.getExpiryDate(), ite.getFoodGroupTags(),ite.getFoodFreshnessTag()));
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Check if container has associated items.
//		HashMap<String, Item> n = items.get(c);
//		// If there are no items associated with the container
//		if (n != null) {
//			n.put(name, ite);
//
//			// If there is no associated hashmap for a given container. It must be a new
//			// container.
//		} else {
//			HashMap<String, Item> m = new HashMap<String, Item>();
//			m.put(name, ite);
//			items.put(c, m);
//
//		}

	}

	/**
	 * Removes an item from a specified container.
	 *
	 * @param c    The container from which the item will be removed.
	 * @param name The name of the item to be removed.
	 */
	public void removeItem(Container c, String name, Item ite) {

		Connection conn = init();
		
		try {
			Statement s = conn.createStatement();
			s.execute(String.format("DELETE FROM item WHERE name='%s'", name));
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Any method that calls removeItem() will ensure that the item exists.
//		items.get(c).remove(name);

	}

	/**
	 * Retrieves an {@link Item} by its container and name.
	 *
	 * @param container The container in which the item is stored.
	 * @param itemName  The name of the item to retrieve.
	 * @return The {@link Item} object if found, {@code null} otherwise.
	 */
	public Item getItem(Container container, String itemName) {
		Connection conn = init();
		
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(String.format("SELECT * FROM item WHERE name='%s'", itemName));
			conn.close();
			
			if (rs.next()) {
				
				return Item.getInstance(rs.getString("name"), rs.getInt("quantity"), rs.getDate("expiry"));
			}
			// Return null if the Container or Item is not found.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * Prints all items within each container to the standard output.
	 */
	public void printItems() {
		for (Map.Entry<Container, HashMap<String, Item>> containerEntry : items.entrySet()) {
			Container container = containerEntry.getKey();
			HashMap<String, Item> itemsInContainer = containerEntry.getValue();

			System.out.println("Container: " + container);

			for (Map.Entry<String, Item> itemEntry : itemsInContainer.entrySet()) {
				String itemName = itemEntry.getKey();
				Item item = itemEntry.getValue();

				System.out.println("\tItem Name: " + itemName + " -> Item: " + item);
			}
		}
	}

}
