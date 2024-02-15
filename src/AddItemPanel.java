import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddItemPanel extends JPanel {
    private JTextField itemNameField = new JTextField(10);
    private JTextField itemQuantityField = new JTextField(5);
    private JTextField itemExpiryField = new JTextField(10);
    private JButton addButton = new JButton("Add Item");

    private ArrayList<Item> items;
    private JTextArea displayArea;

    public AddItemPanel(ArrayList<Item> items, JTextArea displayArea) {
        this.items = items;
        this.displayArea = displayArea;

        setLayout(new FlowLayout());
        add(new JLabel("Item Name:"));
        add(itemNameField);
        add(new JLabel("Quantity:"));
        add(itemQuantityField);
        add(new JLabel("Expiration Date (dd-MMMM-yyyy):"));
        add(itemExpiryField);
        add(addButton);

        addButton.addActionListener(e -> addItem());
    }

    private void addItem(){

    }

}
