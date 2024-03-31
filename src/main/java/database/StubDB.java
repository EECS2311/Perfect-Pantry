package main.java.database;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.domain.logic.container.Container;
import main.java.domain.logic.item.FoodFreshness;
import main.java.domain.logic.item.FoodGroup;
import main.java.domain.logic.item.GenericTag;
import main.java.domain.logic.item.Item;
import main.java.domain.logic.recipe.DailyLimitExceededException;
import main.java.domain.logic.recipe.Ingredient;
import main.java.domain.logic.recipe.RateLimitPerMinuteExceededException;
import main.java.domain.logic.recipe.Recipe;


public class StubDB extends DB {

	public HashMap<String, Container> containerMap = new HashMap<String, Container>();
	public HashMap<String, Item> itemMap = new HashMap<String, Item>();
	public HashMap<String, String> storageTipMap = new HashMap<String, String>();
	public ArrayList<String> groceryList = new ArrayList<String>();

	private Map<Integer, Recipe> recipesMap = new HashMap<>();
	private Map<Integer, Set<Ingredient>> recipeIngredientsMap = new HashMap<>();
	private Map<Integer, Map<Integer, String>> recipeInstructionsMap = new HashMap<>();
	private String[] settings = {"14", "true"};


	/**
	 * Inserts a new container into the database
	 *
	 * @param nameOfContainer
	 */
	public void putContainer(String nameOfContainer) {

		Container c = new Container(nameOfContainer);
		containerMap.put(nameOfContainer, c);

	}

	/**
	 * Returns a list of the containers currently stored in the database
	 *
	 * @return A list of container names. The caller method will create the
	 *         containers
	 */
	public List<String> retrieveContainers() {

		List<String> l = new ArrayList<String>();

		for (String s : containerMap.keySet()) {
			l.add(s);
		}

		if (l.size() > 0) {
			return l;
		}

		return null;

	}

	/**
	 * Verifies if the container is in the database
	 *
	 * @param name The name of the database to be found.
	 * @return True or false depending on if the container is in the database.
	 */
	public boolean findContainer(String name) {

		Object b = containerMap.get(name);

		return !(b == null);

	}

	/**
	 * Removes container from the database
	 *
	 * @param name The name of the database to be removed.
	 */
	public void removeContainer(String name) {
		containerMap.remove(name);
	}

	/**
	 * Updates the name of a specific container
	 *
	 * @param prevName The previous name of the container
	 * @param newName  The new name of the container
	 */
	public void editContainer(String prevName, String newName) {

		containerMap.put(newName, containerMap.remove(prevName));

	}

	/**
	 * Removes all the items from a container in the database
	 *
	 * @param c the container whose items will be removed from
	 */
	public void emptyContainer(Container c) {

		containerMap.clear();
	}

	/**
	 * Adds a new container to the database.
	 *
	 * @param containerName The name of the container to add.
	 * @param c             The {@link Container} object to be added.
	 */
	public void addContainer(String containerName, Container c) {
		this.putContainer(containerName);

	}

	/**
	 * Adds an item to a specific container.
	 *
	 * @param c    The container to which the item will be added.
	 * @param name The name of the item.
	 * @param ite  The {@link Item} object to be added.
	 * @return True if the item was added sucessfully. False otherwise
	 */
	public Boolean addItem(Container c, String name, Item ite) {

		Item it = itemMap.get(name);

		if (it != null) {
			return false;
		} else {
			itemMap.put(name, ite);
			c.addNewItem(ite);
			return true;

		}

	}

	/**
	 * Removes an item from a specified container.
	 *
	 * @param c    The container from which the item will be removed.
	 * @param name The name of the item to be removed.
	 */
	public void removeItem(Container c, String name) {

		itemMap.remove(name);
		c.removeItem(name);

	}

	/**
	 * Retrieves an {@link Item} by its container and name.
	 *
	 * @param c        The container in which the item is stored.
	 * @param itemName The name of the item to retrieve.
	 * @return The {@link Item} object if found, {@code null} otherwise.
	 */
	public Item getItem(Container c, String itemName) {

		return c.getItem(itemName);

	}

	/**
	 * Updates the food group of a specific item within a container. If the new
	 * values for food group are provided (not null), the corresponding field of the
	 * item are updated in the database. If value is null, no update is made.
	 *
	 * @param c            The container that contains the item to be updated.
	 * @param itemName     The name of the item to be updated.
	 * @param newFoodGroup The new food group classification for the item (can be
	 *                     null if not updating).
	 */
	public void updateItemFoodGroup(Container c, String itemName, FoodGroup newFoodGroup) {

		Item it = c.getItem(itemName);
		if (newFoodGroup != null) {
			it.setFoodGroupTag(newFoodGroup);
		}

	}

	/**
	 * Returns a list of Items belonging to a Container in the database
	 *
	 * @param c a Container object to retrieve items from
	 * @return a list of Items belonging to a Container in the database
	 */
	public List<Item> retrieveItems(Container c) {

		return c.getItems();

	}

	/**
	 * Updates the freshness status of all items within a specified container in the
	 * database. This method sets the freshness status based on the current date and
	 * the expiry date of the items. Items past the current date are marked as
	 * 'Expired', items expiring within the next 7 days are marked as 'Near_Expiry',
	 * and all other items are marked as 'Fresh'.
	 *
	 * @param container The container whose items' freshness statuses are to be
	 *                  updated.
	 */
	public void batchUpdateItemFreshness(Container container) {

		List<Item> l = container.getItems();
		Date curr = new Date();

		for (Item ite : l) {
			Date d = ite.getExpiryDate();
			long diff = curr.toInstant().until(d.toInstant(), ChronoUnit.DAYS);

			if (diff > 7) {
				ite.setFoodFreshnessTag(new GenericTag<>(FoodFreshness.FRESH));
			}
			if (diff >= 0) {
				ite.setFoodFreshnessTag(new GenericTag<>(FoodFreshness.NEAR_EXPIRY));
			} else {
				ite.setFoodFreshnessTag(new GenericTag<>(FoodFreshness.EXPIRED));
			}

		}
	}

	public String getStorageTip(String name) {

		return storageTipMap.get(name);

	}

	/**
	 * Adds an item to the grocery list in the database.
	 *
	 * @param itemName The name of the item to add to the grocery list.
	 */
	public void addToGroceryList(String itemName) {

		groceryList.add(itemName);

	}

	/**
	 * Removes an item from the grocery list in the database.
	 *
	 * @param itemName The name of the item to remove from the grocery list.
	 */
	public void removeFromGroceryList(String itemName) {

		groceryList.remove(itemName);

	}

	/**
	 * Retrieves all grocery items from the database.
	 *
	 * @return A 2D array containing all grocery items, where each row represents an
	 *         item.
	 */
	public Object[][] getAllGroceryItems() {
		List<Object[]> itemList = new ArrayList<>();

		for (String s : groceryList) {
			Object[] itemData = { s };
			itemList.add(itemData);
		}
		// Convert the list to a 2D array
		Object[][] itemArray = new Object[itemList.size()][];
		itemList.toArray(itemArray);

		return itemArray;
	}

	/**
	 * Updates the quantity of an item in the database
	 * 
	 * @param item  The name of the item
	 * @param value The quantity to be updated
	 * @param c     The container in which the item belongs to
	 */
	public void updateQuantity(String item, int value, Container c) {

		itemMap.get(item).setQuantity(value);

	}

	/**
	 * Retrieves the names of all items that are either Near Expiry or Fresh.
	 *
	 * @return A Set of Strings representing the names of items that are either near expiry or fresh.
	 */
	@Override
	public Set<String> getNearExpiryOrFreshItemNames() {
		Set<String> nearExpiryOrFreshItems = new HashSet<>();

		for (Map.Entry<String, Item> entry : itemMap.entrySet()) {
			Item item = entry.getValue();
			// Check if the item's freshness is either NEAR_EXPIRY or FRESH
			if (item.getFoodFreshnessTag() != null &&
					(item.getFoodFreshnessTag().getTag() == FoodFreshness.NEAR_EXPIRY ||
							item.getFoodFreshnessTag().getTag()== FoodFreshness.FRESH)) {
				nearExpiryOrFreshItems.add(entry.getKey());
			}
		}
		return nearExpiryOrFreshItems;
	}
	
	/**
	 * Retrieves settings for the program
	 * 
	 * @return a array representing the settings for the program
	 */
	public String[] getSettings(){

		return this.settings;
	}
	
	/**
	 * Updates the fontSize of font in the database
	 * @param n The fontSize 
	 */
	public void setFontsize(int n) {
		this.settings[0] = String.valueOf(n);
	}
	
	/**
	 * Sets whether or not opening the application will give the notifications and saves it in database
	 * 
	 * @param b Boolean value, true if notifcation should be on, false otherwise
	 */
	public void setNotificationBoolean(boolean b) {
		this.settings[1] = String.valueOf(b);
	}

	/**
	 * Saves a recipe to the stub database. This method stores the recipe object,
	 * its used and missed ingredients, and detailed instructions.
	 *
	 * @param recipe The recipe to be saved. Assumes that the recipe's ID is unique.
	 * @throws RuntimeException If an error occurs while fetching detailed instructions due to rate limits,
	 *                          IO issues, or daily limits being exceeded.
	 */
	@Override
	public void saveRecipeToDatabase(Recipe recipe) {
		recipesMap.put(recipe.getId(), recipe);
		Set<Ingredient> ingredients = new HashSet<>();
		ingredients.addAll(recipe.getUsedIngredients());
		ingredients.addAll(recipe.getMissedIngredients());
		recipeIngredientsMap.put(recipe.getId(), ingredients);
		try {
			recipeInstructionsMap.put(recipe.getId(), new HashMap<>(recipe.getDetailedInstructions()));
		} catch (RateLimitPerMinuteExceededException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (DailyLimitExceededException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks if a recipe is present in the stub database.
	 *
	 * @param recipeId The ID of the recipe to check.
	 * @return {@code true} if the recipe is present, {@code false} otherwise.
	 */
	@Override
	public boolean isRecipeInDatabase(int recipeId) {
		return recipesMap.containsKey(recipeId);
	}

	/**
	 * Retrieves all starred (saved) recipes from stub database.
	 *
	 * @return A list of all saved recipes.
	 */
	@Override
	public List<Recipe> getAllStarredRecipes() {
		return new ArrayList<>(recipesMap.values());
	}

	/**
	 * Removes a starred (saved) recipe from the stub database.
	 * This operation also removes any associated ingredients and instructions.
	 *
	 * @param recipe The recipe to remove. The recipe's ID is used to identify the recipe.
	 */
	@Override
	public void removeStarredRecipe(Recipe recipe) {
		recipesMap.remove(recipe.getId());
		recipeIngredientsMap.remove(recipe.getId());
		recipeInstructionsMap.remove(recipe.getId());
	}
}
