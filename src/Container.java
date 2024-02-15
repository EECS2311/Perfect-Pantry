import java.util.ArrayList;

public class Container {
	private String name;
	private ArrayList<Item> listOfItems;
	private ContainerView containerViewgui;
	
	
	public Container(String n) {
		this.name = n;
		listOfItems = new ArrayList<Item>();
		containerViewgui = new ContainerView();
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Add new items into the container
	 */
	public void addNewItem() {
		
		
	}

	public void getGUI() {
		Home.setupHomeGUI(false);
		containerViewgui.setupContainerViewGUI(true);
		
	}

	public void setName(String nameOfContainer) {
		this.name = nameOfContainer;
		
	}
	
	
}
