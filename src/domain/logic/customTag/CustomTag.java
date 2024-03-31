package domain.logic.customTag;

import javax.swing.JOptionPane;

import database.DB;
import domain.logic.item.Item;
import gui.add_items.ItemsListView;

public class CustomTag {

    private DB data;
    
    /**
     * Initializes the class.
     * @param data
     */
    public CustomTag(DB data) {
        this.data = data;
    }
    
    /**
     * Handles the addition or removal of custom tags for an item based on user input.
     * 
     * @param item          The item for which the custom tag operation is performed.
     * @param itemsListView The view containing the table of items.
     */
    public void handleCustomTag(Item item, ItemsListView itemsListView) {
        int row = itemsListView.getTable().getSelectedRow();
        if (row != -1) {
            String name = itemsListView.getTable().getValueAt(row, 0).toString();
            Item selectedItem = data.getItem(itemsListView.getC(), name);
            if (selectedItem != null) {
                String newTag = JOptionPane.showInputDialog("Enter new tag:");
                if (newTag != null && !newTag.isEmpty()) {
                    data.removeItemTag(selectedItem.getName());
                    selectedItem.addCustomTag(newTag);
                    itemsListView.getTable().setValueAt(newTag, row, 5);
                    data.insertItemTag(selectedItem.getName(), newTag);
                } else if (newTag != null && newTag.isEmpty()) {
                	data.removeItemTag(selectedItem.getName());
	            	selectedItem.removeCustomTag();
                    itemsListView.getTable().setValueAt("", row, 5);
                }
            }
        }
    }
}