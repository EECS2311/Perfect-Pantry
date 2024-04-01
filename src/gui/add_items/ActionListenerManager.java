package gui.add_items;

import database.DB;
import domain.logic.item.Item;
import domain.logic.item.ItemUtility;
import gui.home.HomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

/**
 * Manages the attachment of action listeners to menu items in the GUI. This class
 * encapsulates the logic required to interact with the items list, including
 * removal, editing, and displaying tips for items.
 */
public class ActionListenerManager {
    private JTable table;
    private DefaultTableModel tableModel;
    private DB data;
    private ItemsListView itemsListView;

    /**
     * Constructs an ActionListenerManager with the necessary components to interact
     * with the GUI and the database.
     *
     * @param table The table where items are displayed.
     * @param tableModel The table model used by the table.
     * @param data The database connection object.
     * @param itemsListView The view that displays the list of items.
     */
    public ActionListenerManager(JTable table, DefaultTableModel tableModel, DB data, ItemsListView itemsListView) {
        this.table = table;
        this.tableModel = tableModel;
        this.data = data;
        this.itemsListView = itemsListView;
    }

    /**
     * Attaches an action listener to the JMenuItem that removes an item from the list.
     * The removal is conditional upon verification.
     *
     * @param removeItem The menu item to which the listener will be attached.
     */
    public void attachRemoveItemListener(JMenuItem removeItem) {
        ActionListener listener = e -> {
            int row = table.getSelectedRow();
            if (row != CustomTableModel.NOT_VALID_COLUMN) {
                String name = table.getValueAt(row, CustomTableModel.NAME_COLUMN).toString();
                if (ItemUtility.verifyDeleteItem(name, itemsListView.getC())) {
                    itemsListView.removeItem(name);
                }
            }
        };
        removeItem.addActionListener(listener);
    }

    /**
     * Attaches an action listener to the JMenuItem that generates and displays
     * a storage tip for the selected item.
     *
     * @param generateTip The menu item to which the listener will be attached.
     */
    public void attachGenerateTipListener(JMenuItem generateTip) {
        ActionListener listener = e -> {
            int row = table.getSelectedRow();
            if (row != CustomTableModel.NOT_VALID_COLUMN) {
                String name = table.getValueAt(row, CustomTableModel.NAME_COLUMN).toString();
                String sTip = ItemUtility.retrieveStorageTip(name);
                if (sTip != null) {
                    JOptionPane.showMessageDialog(HomeView.getFrame(),
                            "<html><body><p style='width:300px;'>" + sTip + "</p></body></html>",
                            name + " - Storage Tip", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(HomeView.getFrame(), "No Storage Tips Available",
                            "NoStorageTipsError", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        generateTip.addActionListener(listener);
    }

    /**
     * Attaches an action listener to the JMenuItem that allows the quantity of
     * an item to be edited.
     *
     * @param editQty The menu item to which the listener will be attached.
     */
    public void attachEditQtyListener(JMenuItem editQty) {
        ActionListener listener = e -> {
            String val = JOptionPane.showInputDialog(HomeView.getFrame(), "Edit Quantity", "Enter a new Value", JOptionPane.QUESTION_MESSAGE);
            int row = table.getSelectedRow();
            if (row != CustomTableModel.NOT_VALID_COLUMN && val != null && !val.trim().isEmpty()) {
                String name = table.getValueAt(row, CustomTableModel.NAME_COLUMN).toString();
                ItemUtility.verifyEditQuantity(val, data, itemsListView.getC(), name,
                        (errorMsg) -> JOptionPane.showMessageDialog(itemsListView, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE),
                        () -> table.setValueAt(val, row, CustomTableModel.QUANTITY_COLUMN));
                ItemUtility.initItems(itemsListView.getC(), (CustomTableModel) tableModel);
            }
        };
        editQty.addActionListener(listener);
    }

    /**
     * Attaches an action listener to the JMenuItem that handles custom tagging
     * for an item.
     *
     * @param customTag The menu item to which the listener will be attached.
     */
    public void attachCustomTagListener(JMenuItem customTag) {
        ActionListener listener = e -> {
            int row = table.getSelectedRow();
            if (row != CustomTableModel.NOT_VALID_COLUMN) {
                String name = table.getValueAt(row, CustomTableModel.NAME_COLUMN).toString();
                Item item = data.getItem(itemsListView.getC(), name);
                if (item != null) {
                    domain.logic.customTag.CustomTag customTagHandler = new domain.logic.customTag.CustomTag(data);
                    customTagHandler.handleCustomTag(item, itemsListView);
                }
            }
        };
        customTag.addActionListener(listener);
    }
}
