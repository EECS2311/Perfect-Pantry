package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.logic.Container;
import domain.logic.Item;

/**
 * The {@code DB} class represents a simple database for storing and managing
 * containers and their associated items. This class provides methods to add and
 * retrieve containers and items, remove items, and print all items within
 * containers.
 */
public class DB {

	Connection conn;
	private HashMap<String, Container> containers = new HashMap<>();

	private HashMap<Container, HashMap<String, Item>> items = new HashMap<>();

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
			List<String> l = new ArrayList<>();

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
	 * Removes all the items from a container in the database
	 * @param c the container whose items will be removed from
	 */
	public void emptyContainer(Container c) {

		Connection conn = init();

		try {
			Statement s = conn.createStatement();
			s.execute(String.format("DELETE FROM item WHERE container='%s'", c.getName()));
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
		this.putContainer(containerName);

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

		if (this.getItem(c, name) != null) {
			return;
		}

		try {
			Statement s = conn.createStatement();
			s.execute("INSERT INTO item(name, container, quantity, expiry) VALUES('" + name + "', "
					+ "'" + c.getName() +"', "
					+ "" + ite.getQuantity() + ", '"
							+ "" + ite.getExpiryDate() +"')");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

	}

	/**
	 * Retrieves an {@link Item} by its container and name.
	 *
	 * @param container The container in which the item is stored.
	 * @param itemName  The name of the item to retrieve.
	 * @return The {@link Item} object if found, {@code null} otherwise.
	 */
	public Item getItem(Container c, String itemName) {
		Connection conn = init();

		try {
			System.out.println(itemName);
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(String.format("SELECT * FROM item WHERE name='%s' AND container='%s'", itemName, c.getName()));

			if (rs.next()) {
				System.out.println(itemName);
				conn.close();
				return Item.getInstance(rs.getString("name"), rs.getInt("quantity"), rs.getDate("expiry"));
			}
			// Return null if the Container or Item is not found.
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Returns a list of Items belonging to a Container in the database
	 * @param c a Container object to retrieve items from
	 * @return a list of Items belonging to a Container in the database
	 */
	public List<Item> retrieveItems(Container c) {
		Connection conn = init();
		try {
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(String.format("SELECT * FROM item WHERE container='%s'", c.getName()));
			List<Item> l = new ArrayList<>();

			while (result.next()) {
				l.add(this.getItem(new Container(result.getString("container")), result.getString("name")));
			}
			return l;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Adds an item to the grocery list in the database.
	 *
	 * @param itemName The name of the item to add to the grocery list.
	 */
	public void addToGroceryList(String itemName) {
	    // Establish a connection to the database
	    Connection conn = init();
	    try {
	        // Prepare an SQL statement to insert an item into the grocery table
	        PreparedStatement statement = conn.prepareStatement("INSERT INTO grocery (name) VALUES (?)");
	        // Set the item name as a parameter in the SQL statement
	        statement.setString(1, itemName);
	        // Execute the SQL statement to insert the item into the grocery table
	        statement.executeUpdate();
	        // Close the prepared statement
	        statement.close();
	        // Close the database connection
	        conn.close();
	    } catch (SQLException e) {
	        // Handle any SQL exceptions by printing the stack trace
	        e.printStackTrace();
	    }
	}

	/**
	 * Removes an item from the grocery list in the database.
	 *
	 * @param itemName The name of the item to remove from the grocery list.
	 */
	public void removeFromGroceryList(String itemName) {
	    // Establish a connection to the database
	    Connection conn = init();
	    try {
	        // Prepare an SQL statement to delete an item from the grocery table based on its name
	        PreparedStatement statement = conn.prepareStatement("DELETE FROM grocery WHERE name = ?");
	        // Set the item name as a parameter in the SQL statement
	        statement.setString(1, itemName);
	        // Execute the SQL statement to delete the item from the grocery table
	        statement.executeUpdate();
	        // Close the prepared statement
	        statement.close();
	        // Close the database connection
	        conn.close();
	    } catch (SQLException e) {
	        // Handle any SQL exceptions by printing the stack trace
	        e.printStackTrace();
	    }
	}

	/**
     * Retrieves all grocery items from the database.
     *
     * @return A 2D array containing all grocery items, where each row represents an item.
     */
    public Object[][] getAllGroceryItems() {
        Connection conn = init();
        List<Object[]> itemList = new ArrayList<>();

        if (conn != null) {
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM grocery");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String itemName = resultSet.getString("name");
                    // You can add more columns as needed, such as ID, quantity, etc.
                    // For simplicity, this example assumes only the item name is retrieved.

                    // Create an array representing the current item
                    Object[] itemData = { itemName };
                    itemList.add(itemData);
                }

                resultSet.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Convert the list to a 2D array
        Object[][] itemArray = new Object[itemList.size()][];
        itemList.toArray(itemArray);

        return itemArray;
    }

}
