package gui.customNote;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import domain.logic.item.Item;
import domain.logic.container.Container;
import database.DB;
import gui.add_items.ContainerView;
import gui.add_items.ItemsListView;
import gui.home.HomeView;

public class CustomNoteView extends JPanel implements ActionListener {
	private JPanel topPanel;
	private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel inputPanel;
    private JButton saveButton;
    private JButton backButton;
    private JTextArea customNoteTextArea;
    private JLabel titleLabel;
    private DB data;
    private static CustomNoteView customNoteView;
    private ItemsListView itemsListView;

    public CustomNoteView(ItemsListView itemsListView) {
    	customNoteView = this;
        this.data = HomeView.data;
        this.itemsListView = itemsListView;
        
        setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout()); // Use BorderLayout
        topPanel.setBackground(new Color(253, 241, 203));

        titleLabel = new JLabel("Custom Notes");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backButton = new JButton("Back");

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(backButton, BorderLayout.WEST); 

        tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Item", "Custom Note"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setBackground(new Color(253, 241, 203));

        inputPanel = new JPanel(new BorderLayout());
        customNoteTextArea = new JTextArea(3, 20);
        inputPanel.add(customNoteTextArea, BorderLayout.CENTER);
        saveButton = new JButton("Save Note");
        inputPanel.add(saveButton, BorderLayout.EAST);
        inputPanel.setBackground(new Color(253, 241, 203));

        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);       
    }
    public void addFonts() {
    	Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
        titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        tablePanel.setFont(f);
        table.setRowHeight(f.getSize() + 5);
        saveButton.setFont(f);
        backButton.setFont(f);
        table.setFont(f);
        customNoteTextArea.setFont(f);
    }

    public static CustomNoteView getCustomNoteView() {
        return customNoteView;
    }

    public void addRow(String itemName, String customNote) {
        tableModel.addRow(new Object[]{itemName, customNote});
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void populateTable(List<Item> items) {
        clearTable();
        for (Item item : items) {
            String itemName = item.getName();
            String note = data.getNote(itemName);
            addRow(itemName, note);
        }
        table.repaint();
    }

    public String getCustomNote() {
        return customNoteTextArea.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Get the selected row index
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex != -1) {
            	String itemName = (String) table.getModel().getValueAt(selectedRowIndex, 0);
            	Item selectedItem = data.getItem(itemsListView.getC(), itemName);
                // Get the custom note entered by the user
                String customNote = getCustomNote();
                if (customNote != null && !customNote.isEmpty()) {
                	addNote(itemName, selectedItem, customNote, selectedRowIndex);
                } else if (customNote != null && customNote.isEmpty()) {
                	removeNote(itemName, selectedItem, selectedRowIndex);
                }
            } else {
            	JOptionPane.showMessageDialog(this, "Please select a row to save the custom note.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource() ==  backButton) {
			HomeView.getHomeView().setHomeViewVisibility(true);
			setCustomNoteViewVisibility(false);
        }
    }

    public void setCustomNoteViewVisibility(boolean visible) {
        JFrame frame = HomeView.getFrame();
        if (visible) {
        	saveButton.addActionListener(this);
        	backButton.addActionListener(this);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(this);
            addFonts();
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        int r = table.rowAtPoint(e.getPoint());
                        if (r >= 0 && r < table.getRowCount()) {
                            table.setRowSelectionInterval(r, r);
                        } else {
                            table.clearSelection();
                        }

                        JPopupMenu popup = createPopupMenu();
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
        }
        else {
        	// Remove action listeners
            saveButton.removeActionListener(this);
            backButton.removeActionListener(this);

            // Hide the view
            frame.getContentPane().removeAll();
            HomeView.getHomeView().setHomeViewVisibility(true);
        }
        frame.revalidate();
        frame.repaint();
    }
    private JPopupMenu createPopupMenu() {
	    JPopupMenu popupMenu = new JPopupMenu();

	    // Create menu item
	    JMenuItem deleteItem = new JMenuItem("Delete Note");

	    // ActionListener for deleteItem
	    deleteItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	int selectedRowIndex = table.getSelectedRow();
	        	String itemName = (String) table.getModel().getValueAt(selectedRowIndex, 0);
            	Item selectedItem = data.getItem(itemsListView.getC(), itemName);
	            removeNote(itemName, selectedItem, selectedRowIndex); // Call removeItem method
	        }
	    });

	    // Add menu items to the popup menu
	    popupMenu.add(deleteItem);

	    return popupMenu;
	}
    private void addNote(String itemName, Item item, String customNote, int selectedRowIndex) {
    	data.deleteNote(itemName);
        tableModel.setValueAt(customNote, selectedRowIndex, 1);
        item.setCustomNote(customNote);
        data.addNote(itemName, customNote);
        customNoteTextArea.setText("");
    }
    private void removeNote(String itemName, Item item, int selectedRowIndex) {
    	data.deleteNote(itemName);
    	item.setCustomNote("");
        tableModel.setValueAt("", selectedRowIndex, 1);
    }
}
