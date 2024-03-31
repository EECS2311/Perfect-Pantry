package gui.add_items;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import database.DB;
import domain.logic.container.Container;
import domain.logic.customTag.CustomTag;
import domain.logic.item.FoodFreshness;
import domain.logic.item.FoodGroup;
import domain.logic.item.GenericTag;
import domain.logic.item.Item;
import domain.logic.item.ItemUtility;
import gui.home.HomeView;
/**
 * Represents a panel that displays a list of items within a container. It
 * provides functionalities to add and remove items, and update item properties
 * directly from the table. It also supports color coding of items based on freshness
 * or food group, and includes a right-click menu for item management.
 */
public class ItemsListView extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	private JPopupMenu popup;
	private JMenuItem removeItem;
	private JMenuItem editQty;
	private JMenuItem generateTip;
	private JMenuItem customTag;

	private TableRowSorter<TableModel> sorter;

	private boolean colourCodingEnabled = true;
	private ColorCodingMode colorCodingMode = ColorCodingMode.BY_FRESHNESS;

	private HomeView home;

	private DB data;
	private Container container;
	private CustomTag customTagHandler;

	/**
	 * Constructs an ItemsListView panel associated with a specific container.
	 *
	 * @param home      The HomeView instance, providing a reference to the main application frame for interaction.
	 * @param container The Container instance whose items are to be displayed and managed in this panel.
	 */
	public ItemsListView(HomeView home, Container container) {
		this.home = home;
		this.data = home.data;
		this.container = container;
		this.customTagHandler = new CustomTag(data);
		
		setLayout(new BorderLayout());

		tableModel = new CustomTableModel();

		table = new CustomColorCodedTable(tableModel);


		// Define table columns
		tableModel.addColumn("Name");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Expiry Date (yyyy-mm-dd)");
		tableModel.addColumn("Food Group");
		tableModel.addColumn("Food Freshness");
		tableModel.addColumn("Custom Tag");

		tableModel.addTableModelListener(e -> {
			if (e.getType() == TableModelEvent.UPDATE) {
				int row = e.getFirstRow();
				int column = e.getColumn();
				if (column == CustomTableModel.FOOD_GROUP_COLUMN) {
					updateItemFromTable(row, column);
				}
			}
		});

		table.getColumnModel().getColumn(CustomTableModel.FOOD_GROUP_COLUMN).setCellEditor(new EnumComboBoxEditor(FoodGroup.values()));
		table.getTableHeader().setReorderingAllowed(false);

		add(new JScrollPane(getTable()), BorderLayout.CENTER);

		ItemUtility.assignFoodFreshness(this.getC());
		ItemUtility.initItems(container, tableModel);

		popup = new JPopupMenu();
		removeItem = new JMenuItem("Delete Item");
		editQty = new JMenuItem("Update Quantity");
		generateTip = new JMenuItem("Storage Tip");
		customTag = new JMenuItem("Add Custom Tag (Enter Blank String to Remove)");

		popup.add(removeItem);
		popup.add(editQty);
		popup.add(generateTip);
		popup.add(customTag);

		getTable().setComponentPopupMenu(popup);
		getTable().addMouseListener(new MouseAdapter() {

			// Code Adapted from codejava.net
			public void mouseClicked(MouseEvent e) {
				Boolean b = SwingUtilities.isRightMouseButton(e);
				if (b) {
					Point p = e.getPoint();
					int row = getTable().rowAtPoint(p);
					getTable().setRowSelectionInterval(row, row);
				}
			}

		});

		removeItem.addActionListener(e -> {

			int row = getTable().getSelectedRow();

			if (row != CustomTableModel.NOT_VALID_COLUMN) {
				String name = table.getValueAt(row, CustomTableModel.NAME_COLUMN).toString();
				if (ItemUtility.verifyDeleteItem(name, this.getC())) {
					this.removeItem(name);
				}

			}

		});
		generateTip.addActionListener(e -> {

			int row = getTable().getSelectedRow();

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

		});
		editQty.addActionListener(e -> {

			String val = JOptionPane.showInputDialog(HomeView.getFrame(), "Edit Quantity", "Enter a new Value", 3);
			int row = table.getSelectedRow();
			if (row != CustomTableModel.NOT_VALID_COLUMN) {
				String name = table.getValueAt(row, CustomTableModel.NAME_COLUMN).toString();
				ItemUtility.verifyEditQuantity(val, data, this.getC(), name, (errorMsg) -> JOptionPane
						.showMessageDialog(this, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE), () -> {
							table.setValueAt(val, row, CustomTableModel.QUANTITY_COLUMN);
						});
				ItemUtility.initItems(this.getC(), tableModel);
			}
		});
		
		// ActionListener for the "Add Custom Tag" menu item
		customTag.addActionListener(e -> {
		    int row = getTable().getSelectedRow();
		    if (row != -1) {
		        String name = getTable().getValueAt(row, 0).toString();
		        Item item = data.getItem(this.getC(),name);
		        if (item != null) {
		        	customTagHandler.handleCustomTag(item, this);
		        }
		    }
		});

		sorter = new TableRowSorter<TableModel>(getTable().getModel());
		getTable().setRowSorter(sorter);

	}
	
	
	/**
	 * Adds fonts to components
	 */
	public void addFonts() {
		Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
		getTable().setFont(f);
		getTable().setRowHeight(getTable().getRowHeight()+10);
	}

	// Code adapted from docs.oracle.com for SwingUI table component
	/**
	 * Updates the table sorter with the string provided from the filter text box
	 * 
	 * @param filter The string from the filter text box to filter the items by.
	 */
	public void filterTable(List<String> filter) {
		RowFilter<TableModel, Object> rf;
		List<RowFilter<TableModel, Object>> filters = new ArrayList<RowFilter<TableModel, Object>>();
		try {
			filters.add(RowFilter.regexFilter("(?i)" + filter.get(0), 0));
			filters.add(RowFilter.regexFilter("(?i)" + filter.get(1), 3));
			filters.add(RowFilter.regexFilter("(?i)" + filter.get(2), 4));

		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		
		rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);

	}

	/**
	 * Adds an item to the table and updates the underlying container.
	 * 
	 * @param item The item to be added.
	 */
	public void addItem(Item item) {
		String customTags = "";
		tableModel.addRow(
				new Object[] { item.getName(), item.getQuantity(), item.getExpiryDate().toString(), null, null, customTags});
		ItemUtility.assignFoodFreshness(this.getC());
		ItemUtility.initItems(this.getC(), tableModel);
	}

	/**
	 * Removes an item from the table based on its name.
	 * 
	 * @param itemName The name of the item to be removed.
	 */
	public void removeItem(String itemName) {
		// Iterate through the table to find the row with the given item name
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (itemName.equals(tableModel.getValueAt(i, CustomTableModel.NAME_COLUMN))) {

				tableModel.removeRow(i);

				break;
			}
		}
	}

	/**
	 * Updates an item's properties based on changes made within the table.
	 * 
	 * @param row    The row index of the item in the table.
	 * @param column The column index of the property that was changed.
	 */
	private void updateItemFromTable(int row, int column) {
		String itemName = (String) table.getModel().getValueAt(row, CustomTableModel.NAME_COLUMN);
		Object newValue = table.getModel().getValueAt(row, column);

		ItemUtility.updateItemFoodGroupTag(getC(), itemName, newValue, column);
		ItemUtility.assignFoodFreshness(this.getC());
		ItemUtility.initItems(this.getC(), tableModel);
	}

	/**
	 * Toggles the color coding feature on or off in the table view. When color
	 * coding is enabled, rows will be colored based on the 'Food Freshness' status.
	 * When disabled, the default background color is used for all rows. The table
	 * is repainted after toggling to reflect the change immediately.
	 */
	public void toggleColourCoding() {
		switch (colorCodingMode) {
			case OFF:
				colorCodingMode = ColorCodingMode.BY_FRESHNESS;
				break;
			case BY_FRESHNESS:
				colorCodingMode = ColorCodingMode.BY_FOOD_GROUP;
				break;
			case BY_FOOD_GROUP:
				colorCodingMode = ColorCodingMode.OFF;
				break;
		}
		if (table instanceof CustomColorCodedTable) {
			((CustomColorCodedTable) table).setColorCodingMode(colorCodingMode);
		}
	}

	public Container getC() {
		return container;
	}

	public JTable getTable() {
		return table;
	}
}
