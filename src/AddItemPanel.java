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

    private void addItem() {
        try {
            String name = itemNameField.getText().trim(); // Trim to remove leading and trailing spaces
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop the method execution if the name is empty
            }

            int quantity;
            try {
                quantity = Integer.parseInt(itemQuantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop the method execution if the quantity is not a valid number
            }

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop the method execution if the quantity is less than or equal to 0
            }

            String expiryDate = itemExpiryField.getText();

            // Create and add the item
            Item item = Item.getInstance(name, quantity, expiryDate);
            items.add(item);

            // Update display area
            updateDisplayArea();

            // Clear the fields for new inputs
            itemNameField.setText("");
            itemQuantityField.setText("");
            itemExpiryField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateDisplayArea() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item).append("\n");
        }
        displayArea.setText(sb.toString());
    }

}
