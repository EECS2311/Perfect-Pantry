package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import domain.logic.Item;
import database.DB;
import domain.logic.Container;
import domain.logic.ItemUtility;

/**
 * A JPanel subclass that provides a user interface for adding items with name,
 * quantity, and expiration date. It validates user input, adds created items to
 * a provided list, and updates a display area to show all added items.
 */
public class AddItemView extends JPanel {
	private JTextField itemNameField = new JTextField(10);
	private JTextField itemQuantityField = new JTextField(5);
	private JTextField itemExpiryField = new JTextField(10);
	private JButton addButton = new JButton("Add Item");
	private JButton viewCalendar = new JButton("View Calendar");

	private ItemsListView itemsListPanel;

	/**
	 * Constructs a new AddItemPanel with references to a list of items and a
	 * display area.
	 *
	 * @param itemsListPanel The text area where details of added items will be
	 *                       displayed.
	 */
	public AddItemView(ItemsListView itemsListPanel) {
		this.itemsListPanel = itemsListPanel; // Initialize the reference

		setLayout(new FlowLayout());
		add(new JLabel("Item Name:"));
		add(itemNameField);
		add(new JLabel("Quantity:"));
		add(itemQuantityField);
		add(new JLabel("Expiration Date (dd-MMM-yyyy):"));
		add(itemExpiryField);
		add(addButton);
//        add(viewCalendar);

		addButton.addActionListener(e -> addItem());
//        viewCalendar.addActionListener(e -> new CalendarView());
	}

	/**
	 * Attempts to add a new item based on the user input from the text fields.
	 * Validates the input and shows error messages if the validation fails. If the
	 * input is valid, creates a new Item, adds it to the items list, updates the
	 * display area, and clears the input fields for the next entry.
	 */
	private void addItem() {
		String name = itemNameField.getText();
		String quantityStr = itemQuantityField.getText();
		String expiryDate = itemExpiryField.getText();

		boolean isValid = ItemUtility.verifyAddItem(name, quantityStr, expiryDate,
				(errorMsg) -> JOptionPane.showMessageDialog(this, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE));

		if (isValid) {
			Item item = Item.getInstance(name, Integer.parseInt(quantityStr), expiryDate);

			// Assume itemsListPanel.getC() gets a context or container where items are to
			// be added
			boolean addedToData = HomeView.data.addItem(itemsListPanel.getC(), item.getName(), item);

			if (!addedToData) {
				JOptionPane.showMessageDialog(this, "No Duplicate Items!", "Add Item Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			itemsListPanel.addItem(item);

			// Clear the input fields and run the success callback logic
			itemNameField.setText("");
			itemQuantityField.setText("");
			itemExpiryField.setText("");
		}
	}

	/**
	 * Specifies the preferred size of the component, which in this case is adjusted
	 * to accommodate the layout.
	 *
	 * @return A Dimension object containing the preferred size.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(super.getPreferredSize().width, 100); // You can adjust the height as needed.
	}

}
