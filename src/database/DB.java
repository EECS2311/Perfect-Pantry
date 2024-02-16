package database;

import java.util.HashMap;

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

}
