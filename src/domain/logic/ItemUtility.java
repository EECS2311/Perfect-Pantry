package domain.logic;

import gui.HomeView;
import gui.ItemsListView;
import gui.ItemsListView;
import java.util.function.Consumer;
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

    public static void verifyAddItem(String name, String quantityStr, String expiryDate, ItemsListView itemsListPanel, Consumer<String> errorHandler, Runnable successCallback) {
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
            itemsListPanel.addItem(item);
            successCallback.run();
        } catch (Exception ex) {
            errorHandler.accept("Error adding item: " + ex.getMessage());
        }
    }

}
