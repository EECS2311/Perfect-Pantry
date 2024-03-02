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
import javax.swing.table.TableCellRenderer;
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
	 *
	 * @param home      The reference to the Home GUI, allowing for interaction with the main application frame.
	 * @param container The container whose items are to be displayed and managed.
	 */
	public ItemsListView(HomeView home, Container container) {
		this.home = home;
		this.data = home.data;
		this.container = container;
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 4; // Make all columns editable except Food Freshness
			}

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
						return String.class;
					case 4:
						return String.class;
					default:
						return Object.class;
				}
			}
		};

		table = new JTable(tableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row)) { // Check if the current row is not selected
					Object freshnessValue = getValueAt(row, 4); // Retrieve the value without casting
					String freshness; // To store the string representation of the freshness value

					// Check the type of the freshness value and convert it to String appropriately
					if (freshnessValue instanceof GenericTag) {
						freshness = ((GenericTag<FoodFreshness>) freshnessValue).toString();
					} else if (freshnessValue != null) {
						freshness = freshnessValue.toString(); // fallback
					} else {
						freshness = ""; // Default case if the value is null
					}

					// Apply background color based on the freshness string
					switch (freshness) {
						case "Expired":
							c.setBackground(new Color(252, 156, 156)); // Custom red
							break;
						case "Near_Expiry":
							c.setBackground(new Color(236, 236, 127)); // Custom yellow
							break;
						case "Fresh":
							c.setBackground(new Color(145, 252, 145)); // Custom green, darker than Color.GREEN
							break;
						default:
							c.setBackground(Color.WHITE); // Default background
							break;
					}
				} else {
					// If row is selected, use default selection background
					c.setBackground(getSelectionBackground());
				}
				return c;
			}
		};

		// Define table columns
		tableModel.addColumn("Name");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Expiry Date");
		tableModel.addColumn("Food Group");
		tableModel.addColumn("Food Freshness");

		table.getColumnModel().getColumn(3).setCellEditor(new EnumComboBoxEditor(FoodGroup.values()));

		add(new JScrollPane(table), BorderLayout.CENTER);

		// Initialize items and assign food freshness
		ItemUtility.assignFoodFreshness(data, this.container);
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
		ItemUtility.assignFoodFreshness(data, this.container);
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
		ItemUtility.assignFoodFreshness(data, this.container);
		ItemUtility.initItems(data, this.container, tableModel);
	}

}
