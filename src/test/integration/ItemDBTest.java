package test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DB;
import domain.logic.Container;
import domain.logic.Item;

class ItemDBTest {

	private DB data;
	private Container container;
	private Item item;

	@BeforeEach
	void setUp() throws Exception {
		data = new DB();
		Container container;
		Item item;

	}

	@AfterEach
	void cleanUp() throws Exception {
		data.emptyContainer(container);
		data.removeContainer(container.getName());
	}

	@Test
	void testAddItem() {

		container = new Container("Vegetables");
		item = Item.getInstance("Carrot", 12, "12-APR-2025");
		// Add item to the container in DB
		data.addContainer("Vegetables", container);
		data.addItem(container, "Carrot", item);

		// Validate that the item can be retrieved and its properties are as expected
		Item retrievedItem = data.getItem(container, "Carrot");
		assertNotNull(retrievedItem, "Item should exist in the database after being added.");
		assertEquals(item.getName(), retrievedItem.getName(),
				"Retrieved item name should match the added item's name.");
		assertEquals(item.getQuantity(), retrievedItem.getQuantity(),
				"Retrieved item quantity should match the added item's quantity.");
	}

	@Test
	void testGetItem() {
		container = new Container("Vegetables");
		data.addContainer("Vegetables", container);
		// Attempt to retrieve an item that has not been added yet
		Item retrievedItem = data.getItem(container, "Tomato");
		assertNull(retrievedItem, "Item should not exist in the database before being added.");

		// Now add the item and retrieve it
		data.addItem(container, "Tomato", Item.getInstance("Tomato", 15, "12-APR-2000"));
		retrievedItem = data.getItem(container, "Tomato");
		assertNotNull(retrievedItem, "Item should exist in the database after being added.");
		assertEquals("Tomato", retrievedItem.getName(), "Retrieved item name should match the added item's name.");
		assertEquals(15, retrievedItem.getQuantity(),
				"Retrieved item quantity should match the added item's quantity.");
	}

	@Test
	void testAddAndGetItem() {
		DB data = new DB();
		container = new Container("Fruits");
		item = Item.getInstance("Apple", 10, "12-APR-2024");
		data.addContainer("Fruits", container); // Ensure the container is added to the database
		data.addItem(container, "Apple", item); // Add item to the container in DB

		// Validate that the item can be retrieved and its properties are as expected
		Item retrievedItem = data.getItem(container, "Apple");
		assertNotNull(retrievedItem, "Item should exist in the database after being added.");
		assertEquals(item.getName(), retrievedItem.getName(),
				"Retrieved item name should match the added item's name.");
		assertEquals(item.getQuantity(), retrievedItem.getQuantity(),
				"Retrieved item quantity should match the added item's quantity.");
	}

	@Test
	void testRemove() {
		DB data = new DB();
		container = new Container("Cheese");
		Item item = Item.getInstance("Feta", 25, "25-APR-1999");
		data.addContainer("Cheese", container);
		data.addItem(container, "Feta", item);

		assertNotNull(data.getItem(container, "Feta"));
		data.removeItem(container, "Feta", item);

		assertTrue(data.getItem(container, "Feta") == null);

	}
}
