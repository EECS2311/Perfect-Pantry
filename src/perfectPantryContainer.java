import java.util.ArrayList;

public class perfectPantryContainer {
	private String name;
	private ArrayList<Item> listOfItems;
	
	public perfectPantryContainer(String n) {
		this.name = n;
		listOfItems = new ArrayList<Item>();
	}
	
	public String getName() {
		return this.name;
	}
	
	
}