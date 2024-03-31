package main.java.domain.logic.container;

import java.awt.Font;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.swing.JButton;

import main.java.database.DB;
import main.java.gui.home.HomeView;

/**
 * Utility class for operations related to containers in the inventory DB system.
 * This includes adding, deleting, editing, and initializing containers.
 */
public class ContainerUtility {

	/**
	 * Validates the input data for a new container. Valid inputs are added to the
	 * home's container map. In case of validation errors, it utilizes a Consumer to
	 * handle the error message.
	 * 
	 * @param name
	 * @param data
	 * @param homeView
	 * @param map
	 * @param errorHandler    A Consumer that handles error messages.
	 * @param successCallback A Runnable that is executed upon successful addition.
	 */
	public static void verifyAddContainer(String name, DB data, HomeView homeView,
			ConcurrentHashMap<JButton, Container> map, Consumer<String> errorHandler, Runnable successCallback) {

		try {
			name = name.trim();
			if (name.isEmpty()) {
				errorHandler.accept("Container Name cannot be empty!");
				return;
			}
			if (data.findContainer(name)) {
				errorHandler.accept("You cannot have duplicate containers!");
				return;
			}

			Container c = new Container(name, homeView);
			JButton b = new JButton(name);
			b.setFont(new Font("Lucida Grande", Font.PLAIN, 17));

			map.put(b, c);
			data.putContainer(name);
			successCallback.run();
		} catch (Exception e) {

		}

	}

	/**
	 * Ensures deletion of containers. In case of validation errors, it utilizes a
	 * consumer to handle the error message.
	 * 
	 * @param name            The name of the container to be deleted.
	 * @param data            The database object
	 * @param b               The JButton object
	 * @param map             The map of the container and the JButton
	 * @param errorHandler    A Consumer that handles error messages.
	 * @param successCallback A Runnable that is executed upon successful addition.
	 */
	public static void verifyDeleteContainer(String name, DB data, JButton b, ConcurrentHashMap<JButton, Container> map,
			Consumer<String> errorHandler, Runnable successCallback) {

		try {

			map.remove(b);
			data.removeContainer(name);
			successCallback.run();
		} catch (Exception e) {

		}

	}

	/**
	 * 
	 * Validates the input data for a new container. Valid inputs are added to the
	 * home's container map. In case of validation errors, it utilizes a Consumer to
	 * handle the error message.
	 * 
	 * @param prevName        Previous container name
	 * @param newName         New Container name
	 * @param data            The database object
	 * @param b               The JButton object
	 * @param map             The map holding containers and buttons
	 * @param errorHandler    A Consumer that handles error messages.
	 * @param successCallback A Runnable that is executed upon successful addition.
	 */
	public static void verifyEditContainer(String prevName, String newName, DB data, JButton b,
			ConcurrentHashMap<JButton, Container> map, Consumer<String> errorHandler, Runnable successCallback) {

		try {

			if (newName == null) {
				return;
			}
			newName = newName.trim();
			if (newName.isEmpty()) {
				errorHandler.accept("Container Name cannot be empty!");
				return;
			}

			data.editContainer(prevName, newName);
			successCallback.run();
		} catch (Exception e) {

		}

	}

	/**
	 * Initializes the container buttons and associates them with their respective container objects upon
	 * application startup. It retrieves all container names from the database and adds them to the container map.
	 *
	 * @param map      A ConcurrentHashMap that maps buttons to containers.
	 * @param data     The database object used to retrieve container information.
	 * @param homeView The HomeView which contains the container map.
	 */
	public static void initContainers(ConcurrentHashMap<JButton, Container> map, DB data, HomeView homeView) {
		List<String> a = data.retrieveContainers();

		for (String t : a) {
			Container c = new Container(t, homeView);
			JButton b = new JButton(t);
			b.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
			map.put(b, c);
		}

	}
	

}
