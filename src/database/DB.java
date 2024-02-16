package database;

import java.util.ArrayList;
import java.util.HashMap;

import domain.logic.Container;
import domain.logic.Item;

public class DB {

	private HashMap<String, Container> containers = new HashMap<String, Container>();

	private HashMap<Container, ArrayList<Item>> items = new HashMap<Container, ArrayList<Item>>();

	public void getContainer(String containerName) {
		// return containers.get(containerName);
	}

	public void getListOfItems(Integer container) {

	}

	public void getItemByExpiry(Integer container) {

	}

	public void getItemsNearExpiry(Integer container) {

	}

}
