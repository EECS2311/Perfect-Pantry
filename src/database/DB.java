package database;

import java.util.HashMap;
import java.util.Map;

import domain.logic.Container;
import domain.logic.Item;

public class DB {

	private HashMap<String, Container> containers = new HashMap<String, Container>();

	private HashMap<Container, HashMap<String, Item>> items = new HashMap<Container, HashMap<String, Item>>();

	public Container getContainer(String containerName) {
		return containers.get(containerName);
	}

	public void addContainer(String containerName, Container c) {
		containers.put(containerName, c);

	}

	public void addItem(Container c, String name, Item ite) {

		// Check if container has associated items.
		HashMap<String, Item> n = items.get(c);
		// If there are no items associated with the container
		if (n != null) {
			n.put(name, ite);

			// If there is no associated hashmap for a given container. It must be a new
			// container.
		} else {
			HashMap<String, Item> m = new HashMap<String, Item>();
			m.put(name, ite);
			items.put(c, m);

		}

	}

	public void removeItem(Container c, String name, Item ite) {

		// Any method that calls removeItem() will ensure that the item exists.
		items.get(c).remove(name);

	}

	// Method to retrieve an Item by its Container and name.
	public Item getItem(Container container, String itemName) {
		if (items.containsKey(container) && items.get(container).containsKey(itemName)) {
			return items.get(container).get(itemName);
		}
		// Return null if the Container or Item is not found.
		return null;
	}

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
