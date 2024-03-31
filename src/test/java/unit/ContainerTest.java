
package test.java.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import main.java.domain.logic.container.Container;
import main.java.domain.logic.item.Item;

public class ContainerTest {

	private Container container;
	private Item item1;
	private Item item2;

	@BeforeEach
	void setUp() {
		container = new Container("Test Container");
		item1 = Item.getInstance("Milk", 1, new Date());
		item2 = Item.getInstance("Bread", 2, new Date());
	}

	@Test
	void testAddNewItem() {
		assertTrue(container.getItems().isEmpty(), "Container should be empty initially");

		container.addNewItem(item1);
		assertEquals(1, container.getItems().size(), "Container should contain 1 item after addition");

		container.addNewItem(item2);
		assertEquals(2, container.getItems().size(), "Container should contain 2 items after second addition");
	}

	@Test
	void testRemoveItem() {
		container.addNewItem(item1);
		container.addNewItem(item2);

		container.removeItem(item1.getName());
		assertEquals(1, container.getItems().size(), "Container should contain 1 item after removal");
		assertEquals(item2, container.getItems().get(0), "Remaining item should be item2");

		container.removeItem(item2.getName());
		assertTrue(container.getItems().isEmpty(), "Container should be empty after removing all items");
	}

	@Test
	void testConstructorWithItems() {
		List<Item> items = Arrays.asList(item1, item2);
		Container newContainer = new Container("New Container", items);
		assertEquals(2, newContainer.getItems().size(), "Container should be initialized with 2 items");
	}

	@Test
	void testCopyConstructor() {
		container.addNewItem(item1);
		Container copyContainer = new Container(container);
		assertEquals(container.getName(), copyContainer.getName(), "Copy container should have the same name");
		assertEquals(1, copyContainer.getItems().size(), "Copy container should contain 1 item");
	}

	@Test
	void testEqualsMethod() {
		Container container1 = new Container("Container");
		Container container2 = new Container("Container");
		Container container3 = new Container("Another Container");

		assertEquals(container1, container2, "Containers with the same name should be equal");
		assertNotEquals(container1, container3, "Containers with different names should not be equal");
	}

	@Test
	void testSetName() {
		String newName = "Updated Container";
		container.setName(newName);
		assertEquals(newName, container.getName(), "Container name should be updated correctly");
	}
}