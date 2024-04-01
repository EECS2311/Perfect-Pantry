package gui.add_items;

import domain.logic.item.FoodFreshness;
import domain.logic.item.FoodGroup;

import javax.swing.table.DefaultTableModel;

/**
 * CustomTableModel extends DefaultTableModel to specify the types of objects
 * each column in the table model contains, and to control the editability of cells.
 */
public class CustomTableModel extends DefaultTableModel {
    // Constants defining column indices for easy reference
    public static final int NOT_VALID_COLUMN = -1;
    public static final int NAME_COLUMN = 0;
    public static final int QUANTITY_COLUMN = 1;
    public static final int EXPIRY_DATE_COLUMN = 2;
    public static final int FOOD_GROUP_COLUMN = 3;
    public static final int FOOD_FRESHNESS_COLUMN = 4;

    /**
     * Determines whether a cell is editable. Only the FOOD_GROUP_COLUMN cells
     * are editable.
     *
     * @param row    the row index of the cell
     * @param column the column index of the cell
     * @return true if the cell is editable, false otherwise
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return column != NAME_COLUMN && column != QUANTITY_COLUMN && column != EXPIRY_DATE_COLUMN && column != FOOD_FRESHNESS_COLUMN;
    }

    /**
     * Returns the class of the objects contained within the specified column.
     *
     * @param columnIndex the index of the column
     * @return the Class representing the type of objects in this column
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case NAME_COLUMN:
            case EXPIRY_DATE_COLUMN:
                return String.class;
            case QUANTITY_COLUMN:
                return Integer.class;
            case FOOD_GROUP_COLUMN:
                return FoodGroup.class;
            case FOOD_FRESHNESS_COLUMN:
                return FoodFreshness.class;
            default:
                return Object.class;
        }
    }
}
