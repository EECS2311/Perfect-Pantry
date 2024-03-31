
package test.java.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.database.DB;

class GroceryListDBTest {

    private DB db;
    private String itemName;
    @BeforeEach
    void setUp() {
        db = new DB(); // Assuming this initializes the database connection
     // Add an item to the grocery list
        itemName = "Test Item";
        db.addToGroceryList(itemName);
    }
    
    @Test
    void testAddToGroceryList() {

        // Retrieve all grocery items and check if the added item is present
        Object[][] items = db.getAllGroceryItems();
        boolean found = false;
        for (Object[] item : items) {
            if (itemName.equals(item[0])) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Item was not added to the grocery list");
    }

    @Test
    void testRemoveFromGroceryList() {
    	// Remove the added item
        db.removeFromGroceryList(itemName);

        // Retrieve all grocery items and check if the item is removed
        Object[][] items = db.getAllGroceryItems();
        boolean found = false;
        for (Object[] item : items) {
            if (itemName.equals(item[0])) {
                found = true;
                break;
            }
        }
        assertFalse(found, "Item was not removed from the grocery list");
    }

    @Test
    void testGetAllGroceryItems() {
        // Assuming some items are already in the grocery list

        // Retrieve all grocery items
        Object[][] items = db.getAllGroceryItems();

        // Check if the returned array is not null
        assertNotNull(items, "Returned grocery items array should not be null");

        // Check if each item has at least one column (name)
        for (Object[] item : items) {
            assertTrue(item.length >= 1, "Each grocery item should have at least one column (name)");
        }
    }

}
