package domain.logic;

import gui.HomeView;
import gui.ItemslistView;

public class DeleteItem {

	/**
	 * Verifies and deletes an item from the specified container and updates the
	 * Itemslist panel.
	 * 
	 * @param itemName The name of the item to verify and delete.
	 * @param con      The container from which to delete the item.
	 * @param list     The Itemslist panel to update after deletion.
	 * @return Boolean indicating the success or failure of the item deletion.
	 */
	public static Boolean verifyItem(String itemName, Container con, ItemslistView list) {

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

}
