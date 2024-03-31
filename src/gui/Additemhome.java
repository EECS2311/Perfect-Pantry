package gui;

import javax.swing.*;
import domain.logic.item.Item;
import domain.logic.item.ItemUtility;
import gui.home.HomeView;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Additemhome extends JDialog {
    private JTextField itemNameField = new JTextField(10);
    private JComboBox<String> containerComboBox = new JComboBox<>(); // Changed to String type
    private JTextField expiryDateField = new JTextField(10);
    private JTextField quantityField = new JTextField(10);
    private JButton addButton = new JButton("Add");

   // private ItemsListView itemsListPanel;
    
    public Additemhome(JFrame owner) {
        super(owner, "Add New Item", true);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Item Name:"));
        add(itemNameField);

        add(new JLabel("Container:"));
        List<String> containerNames = HomeView.data.retrieveContainers(); // Retrieve container names
        for (String containerName : containerNames) {
            containerComboBox.addItem(containerName); // Add container names to the combo box
        }
        add(containerComboBox);

        add(new JLabel("Expiry Date (dd-MMM-yy):"));
        add(expiryDateField);

        add(new JLabel("Quantity:"));
        add(quantityField);

        addButton.addActionListener(e -> addItem());
        add(addButton);

        pack();
        setLocationRelativeTo(owner);
    }

    private void addItem() {
        String itemName = itemNameField.getText();
        String containerName = (String) containerComboBox.getSelectedItem(); // Get the selected container name
        String expiryDateStr = expiryDateField.getText();
        String quantityStr = quantityField.getText();
        boolean isValid = ItemUtility.verifyAddItem(itemName, quantityStr, expiryDateStr,
                (errorMsg) -> JOptionPane.showMessageDialog(this, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE));

        if (isValid) {
            Item item = Item.getInstance(itemName, Integer.parseInt(quantityStr.trim()), expiryDateStr);
           
          //  boolean addedToData = HomeView.data.addItem(itemsListPanel.getC(), item.getName(), item);
            
            if (!isValid) {
                JOptionPane.showMessageDialog(this, "No Duplicate Items!", "Add Item Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
          //  itemsListPanel.addItem(item);
           
            itemNameField.setText("");
            quantityField.setText("");
            expiryDateField.setText("");

    }
}
}