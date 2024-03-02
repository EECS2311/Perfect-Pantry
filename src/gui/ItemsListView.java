package gui;

import java.awt.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.DB;
import domain.logic.*;
import domain.logic.Container;

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

		// Initialize the table model
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				//  "Name" is at index 0, "Quantity" is at index 1, and "Expiry Date" is at index 2
				return !(column == 0 || column == 1 || column == 2); // columns 0, 1, 2 are not editable
			}

			// Enforce a certain type for each column
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
					case 0:
						return String.class;
					case 1:
						return Integer.class;
					case 2:
						return String.class;
					case 3:
						return FoodGroup.class;
					case 4:
						return FoodFreshness.class;
					default:
						return Object.class;
				}
			}
		};

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

		// Set up the food group and food freshness columns with EnumComboBoxEditor
		table.getColumnModel().getColumn(3).setCellEditor(new EnumComboBoxEditor(FoodGroup.values()));
		table.getColumnModel().getColumn(4).setCellEditor(new EnumComboBoxEditor(FoodFreshness.values()));


		add(new JScrollPane(table), BorderLayout.CENTER);
		
		ItemUtility.initItems(data, container, tableModel);
	}


	/**
	 * Adds an item to the table and updates the underlying container.
	 * @param item The item to be added.
	 */
	public void addItem(Item item) {
		tableModel.addRow(
				new Object[] { item.getName(), item.getQuantity(), item.getExpiryDate().toString(), null, null });
		data.addItem(container, item.getName(), item); // Keep track of the added item
		ItemUtility.initItems(data, this.container, tableModel);

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
		String itemName = (String) table.getModel().getValueAt(row, 0);
		Object newValue = table.getModel().getValueAt(row, column);

		// Call the new ItemUtility update method
		ItemUtility.updateItem(data, container, itemName, newValue, column);
		ItemUtility.initItems(data, this.container, tableModel);
	}

}
