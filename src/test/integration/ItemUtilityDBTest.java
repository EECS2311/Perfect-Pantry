package test.integration;

import domain.logic.item.*;
import domain.logic.container.Container;
import database.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ItemUtilityDBTest {
    private static DB database;
    private Container testContainer;

    @BeforeAll
    static void setupClass() {
        database = new DB();
        database.init();
    }

    @BeforeEach
    void setUp() {
        testContainer = new Container("TestContainer");
        if (!database.findContainer(testContainer.getName())) {
            database.addContainer(testContainer.getName(), testContainer);
        }
    }

    @AfterEach
    void tearDown() {
        database.emptyContainer(testContainer);
        database.removeContainer(testContainer.getName());
    }

    @Test
    void testAddAndRetrieveItem() {
        String itemName = "NewItem";
        Item newItem = Item.getInstance(itemName, 10, new java.sql.Date(System.currentTimeMillis()));
        boolean addItemResult = database.addItem(testContainer, itemName, newItem);
        assertTrue(addItemResult, "Item should be added to the database.");
        Item retrievedItem = database.getItem(testContainer, itemName);
        assertNotNull(retrievedItem, "The item should be retrievable from the database.");
    }

    @Test
    void testDeleteItem() {
        String itemName = "ToDeleteItem";
        Item itemToDelete = Item.getInstance(itemName, 5, new java.sql.Date(System.currentTimeMillis()));
        database.addItem(testContainer, itemName, itemToDelete);
        database.removeItem(testContainer, itemName);
        Item retrievedItem = database.getItem(testContainer, itemName);
        assertNull(retrievedItem, "The item should not exist in the database after deletion.");
    }

    @Test
    void testUpdateItemQuantity() {
        String itemName = "ToUpdateItem";
        int newQuantity = 10;
        Item itemToUpdate = Item.getInstance(itemName, 5, new java.sql.Date(System.currentTimeMillis()));

        database.addItem(testContainer, itemName, itemToUpdate);
        database.updateQuantity(itemName, newQuantity, testContainer);
        Item updatedItem = database.getItem(testContainer, itemName);

        assertNotNull(updatedItem, "The item should exist in the database after update.");
        assertEquals(newQuantity, updatedItem.getQuantity(), "The item's quantity should be updated in the database.");
    }

    @Test
    void testAddAndRetrieveItemUsingUtility() {
        // Example for adding an item
        String itemName = "NewItem";
        String quantity = "10";
        String expiryDate = "20-may-2025";

        Consumer<String> errorHandler = errorMsg -> fail("Error: " + errorMsg);
        assertTrue(ItemUtility.verifyAddItem(itemName, quantity, expiryDate, errorHandler), "Item should be verified and added.");
    }

    @Test
    void testDeleteItemUsingUtility() {
        String itemName = "ToDeleteItem";
        assertTrue(database.addItem(testContainer, itemName, Item.getInstance(itemName, 5, new java.sql.Date(System.currentTimeMillis()))), "Item should be added for deletion test.");

        assertTrue(ItemUtility.verifyDeleteItem(itemName, testContainer, database), "Item should be deleted.");

        Item retrievedItem = database.getItem(testContainer, itemName);
        assertNull(retrievedItem, "The item should not exist in the database after deletion.");
    }

    @Test
    void testUpdateItemQuantityUsingUtility() {
        String itemName = "ToUpdateQuantity";
        assertTrue(database.addItem(testContainer, itemName, Item.getInstance(itemName, 5, new java.sql.Date(System.currentTimeMillis()))), "Item should be added for update test.");

        Consumer<String> errorHandler = errorMsg -> fail("Error updating quantity: " + errorMsg);
        Runnable successCallback = () -> {}; // No operation, just for the sake of completeness

        ItemUtility.verifyEditQuantity("10", database, testContainer, itemName, errorHandler, successCallback);

        Item updatedItem = database.getItem(testContainer, itemName);
        assertNotNull(updatedItem, "The item should exist after update.");
        assertEquals(10, updatedItem.getQuantity(), "The item's quantity should be updated.");
    }

    @Test
    void testAddItemWithBoundaryExpiryDate() {
        // Test adding an item with an expiry date right at the boundary of being considered fresh or near expiry
        String itemName = "BoundaryExpiryItem";
        String quantity = "5";
        // Assuming today's date is the boundary condition
        String expiryDateToday = new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date());

        Consumer<String> errorHandler = errorMsg -> fail("Error: " + errorMsg);
        assertTrue(ItemUtility.verifyAddItem(itemName, quantity, expiryDateToday, errorHandler), "Boundary expiry date item should be verified and added.");
    }

    @Test
    void testUpdateItemExpiryDate() {
        String itemName = "UpdateExpiryItem";
        Item item = Item.getInstance(itemName, 1, new Date(System.currentTimeMillis()));
        database.addItem(testContainer, itemName, item);

        // New expiry date set to 10 days from now
        String newExpiryDate = new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000));

        Consumer<String> errorHandler = errorMsg -> fail("Error updating expiry date: " + errorMsg);
        Runnable successCallback = () -> {};

        Item updatedItem = database.getItem(testContainer, itemName);
        assertNotNull(updatedItem, "The item should exist after expiry date update.");
    }

    @Test
    void testAssignAndCheckFreshness() {
        // Given an item with an expiry date in the near future
        String itemName = "FreshItem";
        Date nearFutureDate = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000); // 3 days from now
        Item freshItem = Item.getInstance(itemName, 10, nearFutureDate);
        database.addItem(testContainer, itemName, freshItem);

        // When updating freshness statuses in the database
        ItemUtility.updateFreshness(database);

        // Then the item should be marked as Near_Expiry
        Item updatedItem = database.getItem(testContainer, itemName);
        assertEquals(FoodFreshness.NEAR_EXPIRY, updatedItem.getFoodFreshnessTag().getTag(), "Item should be marked as Near Expiry.");
    }

    @Test
    void testRetrieveStorageTip() {
        // Given a known food item with storage tips available in the database
        String foodName = "Apple";

        String storageTip = ItemUtility.retrieveStorageTip(foodName, database);

        assertNotNull(storageTip, "Storage tip should not be null.");
        assertFalse(storageTip.isEmpty(), "Storage tip should not be empty.");
    }

}
