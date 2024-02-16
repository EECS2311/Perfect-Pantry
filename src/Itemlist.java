
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

	import java.util.Vector;

	public class Itemslist2 {
		
		public static void main(String[] args) {
	        Itemslist2 app = new Itemslist2();
	        app.setupGUI(); // Setup and display the GUI
	    }

	    private JList<String> itemList;
	    private Vector<String> itemsVector; // Using Vector to easily manage items dynamically

	    public Itemslist2() {
	        itemsVector = new Vector<>();
	    }

	    // Method to print items to the console
	    private void printItemsToConsole() {
	        System.out.println("Current Items in List:");
	        for (String item : itemsVector) {
	            System.out.println(item);
	        }
	        System.out.println("--- End of List ---\n");
	    }

	    // GUI setup method
	    private void setupGUI() {
	        // Creating the main frame
	        JFrame frame = new JFrame("Combined List Display");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 300);

	        // Main panel
	        JPanel panel = new JPanel(new BorderLayout());

	        // Input panel with text field and buttons
	        JPanel inputPanel = new JPanel();
	        JTextField itemInputField = new JTextField(20);
	        JButton addButton = new JButton("Add Item");
	        JButton clearButton = new JButton("Clear List");

	        // Add components to input panel
	        inputPanel.add(itemInputField);
	        inputPanel.add(addButton);
	        inputPanel.add(clearButton);

	        // List setup
	        itemList = new JList<>(itemsVector);
	        JScrollPane scrollPane = new JScrollPane(itemList);

	        // Adding action listeners to buttons
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String item = itemInputField.getText().trim();
	                if (!item.isEmpty()) {
	                    itemsVector.add(item); // Add item to the vector
	                    itemList.setListData(itemsVector); // Refresh the JList
	                    itemInputField.setText(""); // Clear input field
	                    printItemsToConsole(); // Print updated list to console
	                }
	            }
	        });

	        clearButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                itemsVector.clear(); // Clear all items from the vector
	                itemList.setListData(itemsVector); // Refresh the JList
	                printItemsToConsole(); // Print updated list to console
	            }
	        });

	        // Adding components to the main panel
	        panel.add(inputPanel, BorderLayout.NORTH);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        // Adding the main panel to the frame
	        frame.getContentPane().add(panel);

	        // Display the GUI
	        SwingUtilities.invokeLater(() -> frame.setVisible(true));
	    }

	  
	}

	
	
	
	
	
	
	
	
//	 // Method to display items in the console
//    public void displayItemsConsole(String[] items) {
//        System.out.println("Displaying Items in Console:");
//        for (String item : items) {
//            System.out.println(item);
//        }
//    }
//
//    // Method to display items in a GUI
//    public void displayItemsGUI(String[] items) {
//        // Creating the frame for the GUI
//        JFrame frame = new JFrame("List of Items in GUI");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 200);
//
//        // Creating the JList and setting its data
//        JList<String> itemList = new JList<>(items);
//
//        // Adding the JList to a JScrollPane
//        JScrollPane scrollPane = new JScrollPane(itemList);
//        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
//
//        // Displaying the GUI
//        SwingUtilities.invokeLater(() -> frame.setVisible(true));
//    }
//
//    public static void main(String[] args) {
//        // Sample list of items
//        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
//
//        Items app = new Items();
//
//        // Display items in console
//        app.displayItemsConsole(items);
//
//        // Display items in GUI
//        app.displayItemsGUI(items);
//    }

//
//	public static void main(String[] args) {
//
//        // Sample list of items
//        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
//
//        // Displaying each item in the console
//        for (String item : items) {
//            System.out.println(item);
//        }
//    }
//	
//	public static void main(String[] args) {
//        // Sample list of items
//        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
//
//        // Creating the frame for the GUI
//        JFrame frame = new JFrame("List of Items");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 200);
//
//        // Creating the JList and setting its data
//        JList<String> itemList = new JList<>(items);
//
//        // Adding the JList to a JScrollPane in case of many items
//        JScrollPane scrollPane = new JScrollPane(itemList);
//
//        // Adding the JScrollPane to the frame
//        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
//
//        // Displaying the GUI
//        frame.setVisible(true);
//    }
	
	    // Method to display items in the console

