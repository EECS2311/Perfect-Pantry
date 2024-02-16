import java.util.ArrayList;

public class Container {
    private String name;
    private ArrayList<Item> listOfItems;
    private ContainerView containerViewgui;

    public Container(String n, Home home) { // Pass Home instance
        this.name = n;
        listOfItems = new ArrayList<>();
        containerViewgui = new ContainerView(home); // Pass Home instance to ContainerView
    }

    public String getName() {
        return name;
    }

    public void addNewItem(Item item) { // Assuming you want to add an item
        listOfItems.add(item);
    }

    public void getGUI() {
        containerViewgui.setupContainerViewGUI(true);
    }

    public void setName(String nameOfContainer) {
        this.name = nameOfContainer;
    }
}
