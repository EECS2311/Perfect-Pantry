package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import domain.logic.Container;
import domain.logic.FoodFreshness;
import domain.logic.FoodGroup;
import domain.logic.GenericTag;
import domain.logic.Item;
import domain.logic.ItemUtility;

/**
 * Represents a panel that displays a list of items within a container. It
 * provides functionalities to add and remove items, and update item properties
 * directly from the table.
 */
public class ItemsListView extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	private JPopupMenu popup;
	private JMenuItem removeItem;
	private JMenuItem editQty;
	private JMenuItem generateTip;

	private TableRowSorter<TableModel> sorter;

	private boolean colourCodingEnabled = true;

	private HomeView home;

	private DB data;
	private Container container;

	/**
	 * Constructs an ItemsListView panel associated with a specific container.
	 *
	 * @param home      The reference to the Home GUI, allowing for interaction with
	 *                  the main application frame.
	 * @param container The container whose items are to be displayed and managed.
	 */
	public ItemsListView(HomeView home, Container container) {
		this.home = home;
		this.data = home.data;
		this.container = container;
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Make columns 0, 1, 2, and 4 not editable
				return column != 0 && column != 1 && column != 2 && column != 4;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0:
					return String.class;
				case 1:
					return Integer.class;
				case 2:
					return String.class;
				case 3:
					return FoodGroup.class;
				case 4:
					return FoodFreshness.class;
				default:
					return Object.class;
				}
			}
		};

		table = new JTable(tableModel) {

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row) && colourCodingEnabled) {
					// Dynamically find the index of the 'Food Freshness' column
					int freshnessCol = table.getColumnModel().getColumnIndex("Food Freshness");

					// Retrieve the value from the correct column, regardless of its position
					Object freshnessValue = getValueAt(row, freshnessCol);
					String freshness = "";

					// Check the type of the freshness value and convert it to String appropriately
					if (freshnessValue instanceof GenericTag) {
						freshness = ((GenericTag<FoodFreshness>) freshnessValue).toString();
					} else if (freshnessValue != null) {
						freshness = freshnessValue.toString();
					}

					// Apply background color based on the freshness string
					switch (freshness) {
					case "Expired":
						c.setBackground(new Color(252, 156, 156));
						break;
					case "Near_Expiry":
						c.setBackground(new Color(236, 236, 127));
						break;
					case "Fresh":
						c.setBackground(new Color(145, 252, 145));
						break;
					default:
						c.setBackground(Color.WHITE); // Default background
						break;
					}
				} else {
					// If row is selected, use default selection background
				}
				return c;
			}
		};

		// Define table columns
		tableModel.addColumn("Name");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Expiry Date (yyyy-mm-dd)");
		tableModel.addColumn("Food Group");
		tableModel.addColumn("Food Freshness");

		tableModel.addTableModelListener(e -> {
			if (e.getType() == TableModelEvent.UPDATE) {
				int row = e.getFirstRow();
				int column = e.getColumn();
				if (column == 3) { // Include Food Freshness column from manual updates
					updateItemFromTable(row, column);
				}
			}
		});

		table.getColumnModel().getColumn(3).setCellEditor(new EnumComboBoxEditor(FoodGroup.values()));

		add(new JScrollPane(table), BorderLayout.CENTER);

		// Initialize items and assign food freshness
		ItemUtility.assignFoodFreshness(this.getC());
		ItemUtility.initItems(container, tableModel);

		// Init the right click popup menu
		popup = new JPopupMenu();
		removeItem = new JMenuItem("Delete Item");
		editQty = new JMenuItem("Update Quantity");
		generateTip = new JMenuItem("Storage Tip");

		popup.add(removeItem);
		popup.add(editQty);
		popup.add(generateTip);

		table.setComponentPopupMenu(popup);
		table.addMouseListener(new MouseAdapter() {

			// Code Adapted from codejava.net
			public void mouseClicked(MouseEvent e) {
				Boolean b = SwingUtilities.isRightMouseButton(e);
				if (b) {
					Point p = e.getPoint();
					int row = table.rowAtPoint(p);
					table.setRowSelectionInterval(row, row);
				}
			}

		});

		removeItem.addActionListener(e -> {

			int row = table.getSelectedRow();

			if (row != -1) {
				String name = tableModel.getValueAt(row, 0).toString();
				if (ItemUtility.verifyDeleteItem(name, this.getC())) {
					this.removeItem(name);
				}

			}

		});
		generateTip.addActionListener(e -> {

			int row = table.getSelectedRow();

			if (row != -1) {
				String name = tableModel.getValueAt(row, 0).toString();
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
			if (row != -1) {
				String name = tableModel.getValueAt(row, 0).toString();
				ItemUtility.verifyEditQuantity(val, data, this.getC(), name, (errorMsg) -> JOptionPane
						.showMessageDialog(this, errorMsg, "Input Error", JOptionPane.ERROR_MESSAGE), () -> {
							tableModel.setValueAt(val, row, 1);
						});
			}
		});

		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

	}

	// Code adapted from docs.oracle.com for SwingUI table component
	/**
	 * Updates the table sorter with the string provided from the filter text box
	 * 
	 * @param str The string from the filter text box to filter the items by.
	 */
	public void filterTable(String str) {
		RowFilter<TableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter(str);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}

		sorter.setRowFilter(rf);

	}

	/**
	 * Adds an item to the table and updates the underlying container.
	 * 
	 * @param item The item to be added.
	 */
	public void addItem(Item item) {
		tableModel.addRow(
				new Object[] { item.getName(), item.getQuantity(), item.getExpiryDate().toString(), null, null });
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
			if (itemName.equals(tableModel.getValueAt(i, 0))) {

				// Remove the row from the table model
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
		String itemName = (String) table.getModel().getValueAt(row, 0);
		Object newValue = table.getModel().getValueAt(row, column);

		// Call the new ItemUtility update method
		ItemUtility.updateItem(getC(), itemName, newValue, column);
		ItemUtility.assignFoodFreshness(this.getC());
		ItemUtility.initItems(this.getC(), tableModel);
	}

	/**
	 * Toggles the color coding feature on or off in the table view. When color
	 * coding is enabled, rows will be colored based on the 'Food Freshness' status.
	 * When disabled, the default background color is used for all rows. The table
	 * is repainted after toggling to reflect the change immediately.
	 */
	public void toggleColorCoding() {
		colourCodingEnabled = !colourCodingEnabled;
		table.repaint(); // Repaint the table to reflect the change
	}

	public Container getC() {
		return container;
	}
}
