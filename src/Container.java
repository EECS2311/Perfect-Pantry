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

	public void getGUI() {
		Home.setupHomeGUI(false);
		containerViewgui.setupContainerViewGUI(true);
		
	}
	
	
}
