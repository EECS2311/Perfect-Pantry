package domain.logic;

import gui.ContainerView;
import gui.Home;

import java.util.ArrayList;
import java.util.Objects;

public class Container {
	private String name;
	private ArrayList<Item> listOfItems;
	private ContainerView containerViewgui;

	public Container(String n, Home home) {
		this.name = n;
		listOfItems = new ArrayList<>();
		containerViewgui = new ContainerView(home); // Pass Home instance to ContainerView
	}
	
	public Container(String name) {
		this.name = name;
		listOfItems = new ArrayList<>();
	}
	
	public Container(Container container) {
		this.name = container.getName();
		this.listOfItems = new ArrayList<>();
		ArrayList<Item> containerItems = container.getItems();
		for(Item item : containerItems) {
			this.listOfItems.add(item);
		}		
	}
	
	public Container(String name, ArrayList<Item> items) {
		this.name = name;
		this.listOfItems = new ArrayList<>();
		for(Item item : items) {
			this.listOfItems.add(item);
		}		
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Add new items into the container
	 */
	public void addNewItem(Item item) {
		listOfItems.add(item);
	}
	
	public void removeItem(Item item) {
		listOfItems.remove(item);
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Container container = (Container) o;
        return Objects.equals(this.name, container.getName());
    }
	
	public ArrayList<Item> getItems(){
		return this.listOfItems;
	}

	public void getGUI() {
		containerViewgui.setupContainerViewGUI(true);
	}

	public void setName(String nameOfContainer) {
		this.name = nameOfContainer;
	}
}