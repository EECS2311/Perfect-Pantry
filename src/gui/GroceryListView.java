package gui;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DB;

public class GroceryListView extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private DB data;

    JButton addButton = new JButton("Add Item");
    JButton removeButton = new JButton("Remove Item");
    public static JButton backButton = new JButton("Back");
    JButton exportButton = new JButton("Export to TXT"); // New button for exporting to TXT

    /**
     * Constructs a new instance of the GroceryListView panel.
     *
     * @param home The HomeView instance that contains the database connection and serves as the parent component.
     */
    public GroceryListView(HomeView home) {
        this.data = HomeView.data;

        // Initialize the table model
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Name"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

     // Create a JTable with the specified table model
        table = new JTable(tableModel);
        // Create a scroll pane to hold the table, allowing scrolling if needed
        JScrollPane scrollPane = new JScrollPane(table);
        // Set the bounds of the scroll pane (x, y, width, height)
        scrollPane.setBounds(10, 10, 600, 600);
        // Add the scroll pane to the panel
        add(scrollPane);

     // Add button for adding items to the grocery list
        addButton.setBounds(100, 620, 100, 30); // Set bounds (x, y, width, height)
        add(addButton); // Add the button to the panel

        // Add button for removing items from the grocery list
        removeButton.setBounds(220, 620, 120, 30); // Set bounds (x, y, width, height)
        add(removeButton); // Add the button to the panel

        // Add button for exporting the grocery list to a text file
        exportButton.setBounds(340, 620, 100, 30); // Set bounds (x, y, width, height)
        add(exportButton); // Add the button to the panel

        // Add button for navigating back to the previous view
        backButton.setBounds(460, 620, 80, 30); // Set bounds (x, y, width, height)
        add(backButton); // Add the button to the panel

        // Action listeners
        addButton.addActionListener(e -> addItem());
        removeButton.addActionListener(e -> removeItem());
        exportButton.addActionListener(e -> exportToTxt()); // Add action listener for export button
        backButton.addActionListener((ActionEvent e) -> home.actionPerformed(e));

        refreshTable();
        revalidate();
        repaint();
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
}