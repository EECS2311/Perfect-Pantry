package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import database.DB;
import domain.logic.GenericTag;
import domain.logic.Container;

import domain.logic.Item;
import domain.logic.FoodGroup;
import domain.logic.FoodFreshness;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
public class Itemslist extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	// private List<Item> items;

	private Home home;

	private DB data;
	private Container container;


	/**
	 *
	 * @param home The reference to the home GUI.
	 * @param container The reference to Container object.
	 */
	public Itemslist(Home home, Container container) {
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

	public void addItem(Item item) {
		tableModel.addRow(new Object[]{
				item.getName(),
				item.getQuantity(),
				item.getExpiryDate().toString(),
				null,
				null
		});
		data.addItem(container, item.getName(), item);  // Keep track of the added item
		// Optionally, update dropdowns or other UI elements based on the new item list
	}


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
		} else {
			// Handle the case where the item is not found
		}
	}


}
