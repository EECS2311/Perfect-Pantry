package domain.logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.table.DefaultTableModel;

import database.DB;
import gui.HomeView;
import gui.ItemsListView;

/**
 * Utility class for operations related to items in the inventory such as
 * adding, deleting, and updating item details. This class holds logic that GUI
 * classes can use.
 */
public class ItemUtility {
	/**
	 * Verifies and deletes an item from the specified container and updates the
	 * Itemslist panel.
	 *
	 * @param itemName The name of the item to verify and delete.
	 * @param con      The container from which to delete the item.
	 * @param list     The Itemslist panel to update after deletion.
	 * @return Boolean indicating the success or failure of the item deletion.
	 */
	public static Boolean verifyDeleteItem(String itemName, Container con, ItemsListView list) {

		// Checking to see if item is in the database
		if (HomeView.data.getItem(con, itemName) != null) {
			// Remove the item if its present
			HomeView.data.removeItem(con, itemName, null);

			// Remove the item from the datalist
			list.removeItem(itemName);
			return true;
		} else
			return false;

	}

	/**
	 * Validates the input data for a new item and, if valid, adds it to the
	 * ItemsListView panel. In case of validation errors, it utilizes a Consumer to
	 * handle the error message.
	 *
	 * @param name            The name of the new item.
	 * @param quantityStr     The quantity of the new item as a string.
	 * @param expiryDate      The expiry date of the new item.
	 * @param itemsListPanel  The ItemsListView panel to which the item will be
	 *                        added.
	 * @param errorHandler    A Consumer that handles error messages.
	 * @param successCallback A Runnable that is executed upon successful addition.
	 */
	public static void verifyAddItem(String name, String quantityStr, String expiryDate, ItemsListView itemsListPanel,
			Consumer<String> errorHandler, Runnable successCallback) {
		try {
			name = name.trim();
			if (name.isEmpty()) {
				errorHandler.accept("Item name cannot be empty.");
				return;
			}

			int quantity;
			try {
				quantity = Integer.parseInt(quantityStr);
			} catch (NumberFormatException ex) {
				errorHandler.accept("Please enter a valid number for quantity.");
				return;
			}

			if (quantity <= 0) {
				errorHandler.accept("Quantity must be greater than 0.");
				return;
			}

			Item item = Item.getInstance(name, quantity, expiryDate);

			Boolean valid = HomeView.data.addItem(itemsListPanel.getC(), item.getName(), item);
			if (!valid) {
				errorHandler.accept("No Duplicate Items!");
				return;
			}
			itemsListPanel.addItem(item);
			successCallback.run();
		} catch (Exception ex) {
			errorHandler.accept("Error adding item: " + ex.getMessage());
		}
	}

	/**
	 * Updates the specified item's properties based on the provided new value and
	 * column index. The update is only performed if the item exists in the
	 * container.
	 *
	 * @param data      The DB instance containing item data.
	 * @param container The container where the item resides.
	 * @param itemName  The name of the item to be updated.
	 * @param newValue  The new value to be set for the item's property.
	 * @param column    The column index corresponding to the property to be
	 *                  updated.
	 * @return true if the item was successfully updated, false otherwise.
	 */
	public static void updateItem(DB data, Container container, String itemName, Object newValue, int column) {
		if (column == 3 && newValue instanceof FoodGroup) {
			data.updateItemFoodGroup(container, itemName, (FoodGroup) newValue);
		}
	}

	/**
	 * Retrieves and initializes the rows in ItemsListViews from the database for a
	 * specified container.
	 * 
	 * @param data       access to the database
	 * @param c          Container object to initialize the items for
	 * @param tableModel the table object to initialize the rows for
	 */
	public static void initItems(DB data, Container c, DefaultTableModel tableModel) {
		List<Item> items = data.retrieveItems(c);
		tableModel.setRowCount(0);
		for (Item item : items) {
			tableModel.addRow(new Object[] { item.getName(), item.getQuantity(), dateFormat(item.getExpiryDate()),
					item.getFoodGroupTag(), item.getFoodFreshnessTag() });
		}
	}

	/**
	 * Formats a Date object into a string representation with the format
	 * "yyyy-MM-dd". This format is often used for SQL date columns.
	 *
	 * @param expiryDate The date to be formatted.
	 * @return A string representation of the date in "yyyy-MM-dd" format.
	 */
	public static String dateFormat(Date expiryDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(expiryDate);
		return format;
	}

	/**
	 * Assigns FoodFreshness tags to items based on their expiry dates.
	 * 
	 * @param data      The database object to update item freshness.
	 * @param container The container whose items' freshness will be updated.
	 */
	public static void assignFoodFreshness(DB data, Container container) {
		data.batchUpdateItemFreshness(container);
	}

	public static String retrieveStorageTip(String name) {
		// TODO Auto-generated method 
		return null;
	}
}
