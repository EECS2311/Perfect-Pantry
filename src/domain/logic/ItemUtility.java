package domain.logic;

import gui.HomeView;
import gui.ItemsListView;
import gui.ItemsListView;
import java.util.function.Consumer;

import database.DB;
import domain.logic.Container;
import domain.logic.FoodGroup;
import domain.logic.FoodFreshness;
import domain.logic.Item;
import java.util.EnumSet;
import java.util.Set;

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

    public static boolean updateItem(DB data, Container container, int row, String itemName, Object newValue, int column) {
        Item item = data.getItem(container, itemName);

        if (item == null) {
            return false; // Item not found
        }

        switch (column) {
            case 3: // Food Group column
                if (newValue instanceof FoodGroup) {
                    FoodGroup selectedGroup = (FoodGroup) newValue;
                    Set<FoodGroup> foodGroupSet = EnumSet.of(selectedGroup);
                    item.setFoodGroupTagsEnum(foodGroupSet);
                }
                break;
            case 4: // Food Freshness column
                if (newValue instanceof FoodFreshness) {
                    FoodFreshness selectedFreshness = (FoodFreshness) newValue;
                    item.setFoodFreshnessTag(selectedFreshness);
                }
                break;
            default:
                return false; // Invalid column for update
        }
        return true; // Successfully updated
    }

}
