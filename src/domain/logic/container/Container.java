package domain.logic.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import domain.logic.item.Item;
import gui.add_items.ContainerView;
import gui.home.HomeView;

/**
 * Represents a container that can hold multiple items. A container has a name
 * and a list of items. It can be associated with a GUI representation for user
 * interaction.
 */
public class Container {
	private String name;

	private HashMap<String, Item> listOfItem;
	private ContainerView containerViewgui;

	public ContainerView getContainerViewgui() {
		return containerViewgui;
	}


	/**
	 * Constructs a new Container with a name and associates it with a GUI view and
	 * a Home instance.
	 * 
	 * @param n    The name of the container.
	 * @param home The Home instance to associate with this container's GUI view.
	 */
	public Container(String n, HomeView home) {
		this.name = n;
		listOfItem = new HashMap<String, Item>();
		containerViewgui = new ContainerView(home, this); // Pass Home instance to ContainerView
	}

	/**
	 * Constructs a new Container with a specified name.
	 * 
	 * @param name The name of the container.
	 */
	public Container(String name) {
		this.name = name;
		listOfItem = new HashMap<String, Item>();
	}

	/**
	 * Copy constructor. Creates a new Container with the same name and items as the
	 * provided container.
	 * 
	 * @param container The container to copy from.
	 */
	public Container(Container container) {
		this.name = container.getName();
		this.listOfItem = new HashMap<String, Item>();

		List<Item> containerItems = container.getItems();
		for (Item item : containerItems) {
			this.listOfItem.put(item.getName(), item);
		}
	}

	/**
	 * Constructs a new Container with a specified name and a list of items.
	 * 
	 * @param name  The name of the container.
	 * @param items The list of items to be contained.
	 */
	public Container(String name, List<Item> items) {
		this.name = name;
		this.listOfItem = new HashMap<String, Item>();
		for (Item item : items) {
			this.listOfItem.put(item.getName(), item);
		}
	}

	/**
	 * Returns the name of the container.
	 * 
	 * @return The name of the container.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Adds a new item to the container.
	 * 
	 * @param item The item to add.
	 */
	public void addNewItem(Item item) {
		listOfItem.put(item.getName(), item);

	}

	/**
	 * Removes a specified item from the container.
	 * 
	 * @param itemName The name of item to remove.
	 */
	public void removeItem(String itemName) {
		listOfItem.remove(itemName);
	}

	/**
	 * Checks if this container is equal to another object. Equality is based on the
	 * container's name.
	 * 
	 * @param o The object to compare with this container.
	 * @return true if the specified object is a container with the same name as
	 *         this one; false otherwise.
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Container container = (Container) o;
		return Objects.equals(this.name, container.getName());
	}

	/**
	 * Returns a list of items contained in this container.
	 * 
	 * @return A list of items.
	 */
	public List<Item> getItems() {

		List<Item> l = new ArrayList<Item>();
		for (String s : listOfItem.keySet()) {
			l.add(listOfItem.get(s));
		}
		return l;
	}

	public Item getItem(String s) {

		return listOfItem.get(s);

	}

	/**
	 * Initializes and shows the GUI view associated with this container.
	 */
	public void getGUI() {
		containerViewgui.setupContainerViewGUI(true);
	}

	/**
	 * Sets the name of the container.
	 * 
	 * @param nameOfContainer The new name for the container.
	 */
	public void setName(String nameOfContainer) {
		this.name = nameOfContainer;
	}
}