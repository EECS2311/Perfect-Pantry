package domain.logic;

import java.awt.Font;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.swing.JButton;

import database.DB;
import gui.HomeView;

public class ContainerUtility {

	/**
	 * 
	 * @param name
	 * @param data
	 * @param homeView
	 * @param map
	 * @param errorHandler
	 * @param successCallback
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
	 * 
	 * @param containerMap
	 * @param data
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
