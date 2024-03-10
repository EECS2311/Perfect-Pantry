package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import database.DB;
import domain.logic.Container;

public class GroceryListView extends JPanel implements ActionListener {
	// Data model and components
    private DefaultTableModel tableModel;
    private JTable table;
    private DB data;
    private static GroceryListView groceryListView;
    
    private JLabel titleLabel = new JLabel("Grocery List");
    private JPanel viewOfAllPanel = new JPanel();
    private JPanel tablePanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();

    // Buttons for interacting with the grocery list
    JButton addButton = new JButton("Add Item");
    JButton removeButton = new JButton("Remove Item");
    JButton backButton = new JButton("Back");
    JButton exportButton = new JButton("Export to .txt");
    
    /**
	 * Launches the application and initializes the main GUI components.
	 */
    public GroceryListView() {
    	groceryListView = this;
        this.data = HomeView.data;

        // Initialize the view panels
        viewOfAllPanel.setLayout(null);
        viewOfAllPanel.setBackground(new Color(253, 241, 203));
        
        tablePanel.setLayout(null); // Set layout to null for absolute positioning
        tablePanel.setBackground(new Color(253, 241, 203));
        
        buttonsPanel.setLayout(null); // Set layout to null for absolute positioning
        buttonsPanel.setBackground(new Color(253, 241, 203));

        // Add panels and label to the main panel
        viewOfAllPanel.add(tablePanel);
        viewOfAllPanel.add(buttonsPanel);
        viewOfAllPanel.add(titleLabel);

        // Set positions and sizes of components
        tablePanel.setBounds(10, 80, 800, 300);
        buttonsPanel.setBounds(10, 400, 530, 50);
        titleLabel.setBounds(300, 0, 600, 100);
        titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        
        // Initialize the table model
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Name"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
            	return column == 1; 
            }
        };
        // Create a JTable with the specified table model
        table = new JTable(tableModel);
        
        // Create a scroll pane to hold the table, allowing scrolling if needed
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Set the bounds of the scroll pane (x, y, width, height)
        scrollPane.setBounds(10, 10, 760, 300);
        
        // Add the scroll pane to the table panel
        tablePanel.add(scrollPane);
        
        // Set the position of buttons and add buttons to the buttons panel
        addButton.setBounds(20, 0, 100, 30);
        removeButton.setBounds(140, 0, 120, 30);
        exportButton.setBounds(280, 0, 100, 30);
        backButton.setBounds(400, 0, 80, 30);
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(exportButton);
        buttonsPanel.add(backButton);  
    }
    
    /**
	 * Sets the visibility of the GroceryListView GUI depending on the boolean passed
	 * @param b the value of whether the visibility is true or not
	 */
    public void setGroceryListViewVisibility(boolean b) {
        if (b) {
            HomeView.getHomeView().setHomeViewVisibility(false);
            DeleteContainerView.getDeleteContainerView().setDeleteContainerViewVisibility(false);
            EditContainerView.getEditContainerView().setEditContainerViewVisibility(false);

            // Attach action listeners to buttons
            addButton.addActionListener(this);
            removeButton.addActionListener(this);
            exportButton.addActionListener(this);
            backButton.addActionListener(this);
            
            // Refresh the table
            refreshTable();
            
            //Add all panels
            HomeView.getFrame().add(viewOfAllPanel);
            
            // Set visibility to true
            viewOfAllPanel.setVisible(true); 
            
        } else {
            // Remove action listeners
            addButton.removeActionListener(this);
            removeButton.removeActionListener(this);
            exportButton.removeActionListener(this);
            backButton.removeActionListener(this);

            // Hide the view
            viewOfAllPanel.setVisible(false);
        }
    }

    /**
     * Adds an item to the table and database.
     */
    private void addItem() {
        String itemName = JOptionPane.showInputDialog(this, "Enter item name:");
        if (itemName != null && !itemName.trim().isEmpty()) {
            try {
                data.addToGroceryList(itemName);
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Removes the selected item from the table and database.
     */
    private void removeItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) tableModel.getValueAt(selectedRow, 0);
            data.removeFromGroceryList(itemName);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
        refreshTable();
    }

    /**
     * Export the grocery list to a text file.
     */
    private void exportToTxt() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("grocery_list.txt"));
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.write((String) tableModel.getValueAt(i, 0));
                writer.newLine();
            }
            writer.close();
            JOptionPane.showMessageDialog(this, "Grocery list exported successfully to grocery_list.txt", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting grocery list: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refreshes the table to update it.
     */
    private void refreshTable() {
        // Clear the existing table data
        tableModel.setRowCount(0);

        // Retrieve the updated data from the database
        Object[][] newData = data.getAllGroceryItems(); // Implement this method in your DB class

        // Add the updated data to the table model
        for (Object[] row : newData) {
            tableModel.addRow(row);
        }

        // Repaint the table to reflect the changes
        table.repaint();
    }
    
    /**
	 * Provides access to this instance of GroceryListView
	 * 
	 * @return the current instance of GroceryListView
	 */
	public static GroceryListView getGroceryListView() {
		return groceryListView;
	}
	
	/**
	 * Handles action events triggered by various GUI components.
	 * This method is the central hub for processing user interactions within the grocery list view.
	 *
	 * @param e The ActionEvent object containing details about the event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source ==  backButton) {
			HomeView.getHomeView().setHomeViewVisibility(true);
			setGroceryListViewVisibility(false);
		} else if (source == addButton) {
			addItem();
		} else if (source == removeButton) {
			removeItem();
		} else if (source == exportButton){
			exportToTxt();
		}
	}

}
