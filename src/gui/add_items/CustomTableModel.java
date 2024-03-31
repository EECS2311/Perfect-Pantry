package gui.add_items;
import domain.logic.item.FoodFreshness;
import domain.logic.item.FoodGroup;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {
    public static final int NOT_VALID_COLUMN = -1;
    public static final int NAME_COLUMN = 0;
    public static final int QUANTITY_COLUMN = 1;
    public static final int EXPIRY_DATE_COLUMN = 2;
    public static final int FOOD_GROUP_COLUMN = 3;
    public static final int FOOD_FRESHNESS_COLUMN = 4;

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != NAME_COLUMN && column != QUANTITY_COLUMN && column != EXPIRY_DATE_COLUMN && column != FOOD_FRESHNESS_COLUMN;
    }

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
