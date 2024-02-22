package gui;

import java.awt.BorderLayout;
import java.util.EnumSet;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.DB;
import domain.logic.Container;
import domain.logic.FoodFreshness;
import domain.logic.FoodGroup;
import domain.logic.Item;

/**
 * Represents a panel that displays a list of items within a container.
 * It provides functionalities to add and remove items, and update item properties directly from the table.
 */
public class ItemsListView extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	// private List<Item> items;

	private HomeView home;

	private DB data;
	private Container container;

	/**
	 * Constructs an ItemsListView panel associated with a specific container.
	 * @param home The reference to the Home GUI, allowing for interaction with the main application frame.
	 * @param container The container whose items are to be displayed and managed.
	 */
	public ItemsListView(HomeView home, Container container) {
		this.home = home;
		this.data = home.data;
		this.container = container;
		setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		// items = new ArrayList<>();

		// Define table columns
		tableModel.addColumn("Name");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Expiry Date");
		tableModel.addColumn("Food Group");
		tableModel.addColumn("Food Freshness");

		// Add a model listener to capture changes to the table model
		tableModel.addTableModelListener(e -> {
			if (e.getType() == TableModelEvent.UPDATE) {
				int row = e.getFirstRow();
				int column = e.getColumn();
				if (row >= 0 && column >= 0) {
					updateItemFromTable(row, column);
				}
			}
		});

		// Populate JComboBoxes with enum values
		JComboBox<FoodGroup> foodGroupComboBox = new JComboBox<>(FoodGroup.values());
		JComboBox<FoodFreshness> foodFreshnessComboBox = new JComboBox<>(FoodFreshness.values());

		// Set up the food group and food freshness columns with JComboBoxes
		table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(foodGroupComboBox));
		table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(foodFreshnessComboBox));

		add(new JScrollPane(table), BorderLayout.CENTER);
	}


	/**
	 * Adds an item to the table and updates the underlying container.
	 * @param item The item to be added.
	 */
	public void addItem(Item item) {
		tableModel.addRow(
				new Object[] { item.getName(), item.getQuantity(), item.getExpiryDate().toString(), null, null });
		data.addItem(container, item.getName(), item); // Keep track of the added item
		home.data.printItems();

	}

	/**
	 * Removes an item from the table based on its name.
	 * @param itemName The name of the item to be removed.
	 */
	public void removeItem(String itemName) {
		// Iterate through the table to find the row with the given item name
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (itemName.equals(tableModel.getValueAt(i, 0))) {

				// Remove the row from the table model
				tableModel.removeRow(i);
				break;
			}
		}
	}

	/**
	 * Updates an item's properties based on changes made within the table.
	 * @param row The row index of the item in the table.
	 * @param column The column index of the property that was changed.
	 */
	private void updateItemFromTable(int row, int column) {
		// Get the item name from the table model, in the first column
		String itemName = (String) table.getModel().getValueAt(row, 0);

		// Retrieve the Item object from the DB using the container and item name
		Item item = data.getItem(container, itemName);

		// Proceed only if the item was successfully retrieved
		if (item != null) {
			TableModel model = table.getModel();
			switch (column) {
			case 3: // Food Group column
				FoodGroup selectedGroup = (FoodGroup) model.getValueAt(row, column);
				// Create a Set containing just the selected FoodGroup
				Set<FoodGroup> foodGroupSet = EnumSet.of(selectedGroup);
				item.setFoodGroupTagsEnum(foodGroupSet);
				break;
			case 4: // Food Freshness column
				FoodFreshness selectedFreshness = (FoodFreshness) model.getValueAt(row, column);
				item.setFoodFreshnessTag(selectedFreshness);
				break;
			default:
				break;
			}
			home.data.printItems();
		} else {
			// Handle the case where the item is not found
		}
	}

}
