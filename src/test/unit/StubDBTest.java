package test.unit;

import org.junit.jupiter.api.Test;

import database.StubDB;
import domain.logic.container.Container;
import domain.logic.item.Item;

import static org.junit.jupiter.api.Assertions.*;

class StubDBTest {

	@Test
	void addContainer() {

		StubDB db = new StubDB();

		db.putContainer("NewContainer");

		int size = db.containerMap.size();
		assertEquals(size, 1);
		assertTrue(db.findContainer("NewContainer"));

	}

	@Test
	void deleteContainer() {
		StubDB db = new StubDB();
		db.putContainer("NewContainer");
		db.putContainer("MainPantry");
		db.removeContainer("NewContainer");

		int size = db.containerMap.size();
		assertEquals(size, 1);
		assertFalse(db.findContainer("NewContainer"));
		assertTrue(db.findContainer("MainPantry"));

	}

	@Test
	void editContainerName() {

		StubDB db = new StubDB();
		db.putContainer("NewContainer");

		db.editContainer("NewContainer", "Old");
		assertTrue(db.findContainer("Old"));
		assertFalse(db.findContainer("NewContainer"));

	}

	@Test
	void addItem() {
		StubDB db = new StubDB();

		Container c = new Container("NewNew");
		Item ite = Item.getInstance("Cheese");
		db.addItem(c, "Cheese", ite);

		Item a = db.getItem(c, "Cheese");

		int size = db.itemMap.size();

		assertEquals(a, ite);
		assertEquals(1, size);

	}

	@Test
	void deleteItem() {
		StubDB db = new StubDB();
		Container c = new Container("New");
		for (int i = 0; i < 5; i++) {
			db.addItem(c, "Cheese" + i, Item.getInstance("Cheese" + i));
		}

		db.removeItem(c, "Cheese2");

		int size = db.itemMap.size();

		assertEquals(4, size);
	}

	@Test
	void getContainerNothing() {

		StubDB db = new StubDB();
		db.retrieveContainers();
		assertNull(db.retrieveContainers());

	}

	@Test
	void testqty() {

		StubDB db = new StubDB();
		Container c = new Container("New");
		db.addItem(c, "Cheese", Item.getInstance("Cheese", 25));

		assertEquals(db.getItem(c, "Cheese").getQuantity(), 25);
		db.updateQuantity("Cheese", 30, c);
		assertEquals(db.getItem(c, "Cheese").getQuantity(), 30);
	}

	@Test
	void getStorageTip_DoesntExist() {
		StubDB db = new StubDB();
		assertNull(db.getStorageTip("Cheese"));

	}

	@Test
	void testGroceryList() {

		StubDB db = new StubDB();
		db.addToGroceryList("Cheese");
		assertEquals(db.groceryList.size(), 1);

		db.removeFromGroceryList("Grapes");
		assertEquals(db.groceryList.size(), 1);

		db.removeFromGroceryList("Cheese");
		assertEquals(db.groceryList.size(), 0);

	}

}
