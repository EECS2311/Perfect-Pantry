import java.util.ArrayList;

public class Container {
	private String name;
	private ArrayList<Item> listOfItems;
	
	
	public Container(String n) {
		this.name = n;
		listOfItems = new ArrayList<Item>();
	}
	
	public String getName() {
		return this.name;
	}

	public void getGUI() {

	}
	
	
}
