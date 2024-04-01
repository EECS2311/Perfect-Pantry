package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.logic.container.Container;
import domain.logic.item.Item;
import domain.logic.item.ItemUtility;
import gui.home.HomeView;

public class AddItemHomeView {
    private JTextField itemNameField = new JTextField(10);
    private JComboBox<String> containerComboBox = new JComboBox<>(); // Changed to String type
    private JTextField expiryDateField = new JTextField(10);
    private JTextField quantityField = new JTextField(10);
    private JButton addButton = new JButton("Add");
	JDialog j;

   
    
    public AddItemHomeView(JFrame owner) {
    	Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
    	j = new JDialog(HomeView.getFrame(), "Add New Item");
 
        j.setLayout(new GridLayout(5, 2));

        JLabel itemName = new JLabel("Item Name:");
        itemName.setFont(f);
        j.add(itemName);
        
        itemNameField.setFont(f);
        j.add(itemNameField);

        JLabel container = new JLabel("Container:");
        container.setFont(f);
        j.add(container);
        
        List<String> containerNames = HomeView.data.retrieveContainers(); // Retrieve container names
        for (String containerName : containerNames) {
            containerComboBox.addItem(containerName); // Add container names to the combo box
        }
        containerComboBox.setFont(f);
        j.add(containerComboBox);

        JLabel expiry = new JLabel("Expiry Date (dd-MMM-yy):");
        expiry.setFont(f);
        j.add(expiry);
        expiryDateField.setFont(f);
        j.add(expiryDateField);

        JLabel quantity = new JLabel("Quantity:");
        quantity.setFont(f);
        j.add(quantity);
        quantityField.setFont(f);
        j.add(quantityField);

        addButton.setFont(f);
        addButton.addActionListener(e -> addItem());
        j.add(addButton);

        j.pack();
        j.setVisible(true);
        j.setLocationRelativeTo(HomeView.getFrame());
    }

    private void addItem() {
        String itemName = itemNameField.getText();
        String containerName = (String) containerComboBox.getSelectedItem(); // Get the selected container name
        String expiryDateStr = expiryDateField.getText();
        String quantityStr = quantityField.getText();
        Container c = null;

        
        for (Entry<JButton, Container> entry: HomeView.getContainerMap().entrySet()){
        	if (entry.getKey().getText().equals(containerName)) {
				c = entry.getValue();
			}
		}

		boolean isValid = ItemUtility.verifyAddItem(itemName, quantityStr, expiryDateStr,
				(errorMsg) -> JOptionPane.showMessageDialog(HomeView.getFrame(), errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE));

		if (isValid) {
			Item item = Item.getInstance(itemName, Integer.parseInt(quantityStr.trim()), expiryDateStr);

			boolean addedToData = HomeView.data.addItem(c, item.getName(), item);

			if (!addedToData) {
				JOptionPane.showMessageDialog(HomeView.getFrame(), "No Duplicate Items!", "Add Item Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			c.getContainerViewgui().getItemsListPanel().addItem(item); 
			
			itemNameField.setText("");
            quantityField.setText("");
            expiryDateField.setText("");
            
            j.dispose();
		}

	
        

    }
}