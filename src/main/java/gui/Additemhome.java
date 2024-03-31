package main.java.gui;

import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.java.domain.logic.container.Container;
import main.java.domain.logic.item.Item;
import main.java.gui.home.HomeView;

public class Additemhome extends JDialog {
    private JTextField itemNameField = new JTextField(10);
    private JComboBox<Container> containerComboBox = new JComboBox<>();
    private JTextField expiryDateField = new JTextField(10);
    private JTextField quantityField = new JTextField(10);
    private JButton addButton = new JButton("Add");

    public Additemhome(JFrame owner) {
        super(owner, "Add New Item", true);
        setLayout(new GridLayout(5, 3));

        add(new JLabel("Item Name:"));
        add(itemNameField);

        add(new JLabel("Container:"));
        for (Container container : HomeView.getContainerMap().values()) {
            containerComboBox.addItem(container);
        }
        add(containerComboBox);

        add(new JLabel("Expiry Date (yyyy-MM-dd):"));
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
        Container selectedContainer = (Container) containerComboBox.getSelectedItem();
        String expiryDateStr = expiryDateField.getText();
        String quantityStr = quantityField.getText();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int quantity = Integer.parseInt(quantityStr);
            Item newItem = Item.getInstance(itemName, quantity, sdf.parse(expiryDateStr));
            selectedContainer.addNewItem(newItem);

            JOptionPane.showMessageDialog(this, "Item '" + itemName + "' added successfully.");
            dispose();
        } catch (NumberFormatException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

