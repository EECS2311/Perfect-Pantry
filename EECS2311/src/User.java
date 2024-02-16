/*
 * A class that lets the client create and modify users.
 */

import java.util.*;

public class User {
	private String name;
	private ArrayList<Item> listOfItems;
	
	// Constructors are private to enforce the use of factory methods for instance creation.
	/*
	 * Constructor that adds a default new user.
	 */
	private User() {
		this.setName("User");
		this.listOfItems = new ArrayList<>();
	}
	/*
	 * Constructor that adds a new user where the name is given.
	 * @param name The name of the user.
	 */
	private User(String name) {
		this.setName(name);
		this.listOfItems = new ArrayList<>();
	}
	/*
	 * Constructor that adds a new user where the name and the list of items owned is given.
	 * @param name The name of the user.
	 * @param listOfItems The list of items owned by the user.
	 */
	private User(String name, ArrayList<Item> listOfItems) {
		this.setName(name);
		this.listOfItems = new ArrayList<>();
		for(Item item : listOfItems) {
			this.listOfItems.add(item);
		}
	}
	//Factory method to create an instance of User with default properties.
	public static User getInstance() {
		User user = User.getInstance();
		return user;
	}
	/*
	 * Factory method to create an instance of User with assigned name.
	 * @param name The name of the user.
	 */
	public static User getInstance(String name) {
		User user = User.getInstance(name);
		return user;
	}
	/*
	 * Factory method to create an instance of User with assigned name and list of items owned.
	 * @param name The name of the user.
	 * @param listOfItems The list of items owned by the user.
	 */
	public static User getInstance(String name, ArrayList<Item> listOfItems) {
		User user = User.getInstance(name, listOfItems);
		return user;
	}
	/*
	 * Returns the name of the user.
	 */
	public String getName() {
		return this.name;
	}
	/*
	 * Modifies the name of the user.
	 * @param name The new name assigned to the user.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
