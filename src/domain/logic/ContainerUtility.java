package domain.logic;

import java.awt.Font;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.swing.JButton;

import database.DB;
import gui.HomeView;

public class ContainerUtility {

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

}
