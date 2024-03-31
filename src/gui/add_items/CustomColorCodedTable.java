package gui.add_items;

import domain.logic.item.FoodFreshness;
import domain.logic.item.FoodGroup;
import domain.logic.item.GenericTag;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * CustomColorCodedTable extends JTable to implement color coding of rows based
 * on the freshness or food group of the items displayed.
 */
public class CustomColorCodedTable extends JTable {
    private ColorCodingMode colorCodingMode = ColorCodingMode.BY_FRESHNESS;

    /**
     * Constructs a CustomColorCodedTable with the specified table model.
     *
     * @param model the table model to be used by this table
     */
    public CustomColorCodedTable(DefaultTableModel model) {
        super(model);
    }


    /**
     * Prepares the renderer for each cell in the table. Depending on the
     * colorCodingMode, cells will be color-coded based on their freshness or
     * food group value.
     *
     * @param renderer the TableCellRenderer to prepare
     * @param row      the row of the cell to render, where 0 is the first row
     * @param column   the column of the cell to render, where 0 is the first column
     * @return the Component under the given rendering
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
            switch (colorCodingMode) {
                case BY_FRESHNESS:
                    int freshnessCol = this.getColumnModel().getColumnIndex("Food Freshness");
                    Object freshnessValue = getValueAt(row, freshnessCol);
                    String freshness = "";

                    if (freshnessValue instanceof GenericTag) {
                        freshness = ((GenericTag<FoodFreshness>) freshnessValue).toString();
                    } else if (freshnessValue != null) {
                        freshness = freshnessValue.toString();
                    }

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
                            c.setBackground(Color.WHITE);
                            break;
                    }
                    break;
                case BY_FOOD_GROUP:
                    int foodGroupCol = this.getColumnModel().getColumnIndex("Food Group");
                    Object foodGroupValue = getValueAt(row, foodGroupCol);
                    String foodGroup = "";

                    if (foodGroupValue instanceof GenericTag) {
                        foodGroup = ((GenericTag<FoodGroup>) foodGroupValue).toString();
                    } else if (foodGroupValue != null) {
                        foodGroup = foodGroupValue.toString();
                    }

                    switch (foodGroup) {
                        case "Grain":
                            c.setBackground(new Color(255, 235, 156));
                            break;
                        case "Protein":
                            c.setBackground(new Color(252, 156, 156));
                            break;
                        case "Vegetable":
                            c.setBackground(new Color(193,219,155));
                            break;
                        case "Fruit":
                            c.setBackground(new Color(195, 177, 225));
                            break;
                        case "Dairy":
                            c.setBackground(new Color(207,219,231));
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                            break;
                    }
                    break;

                case OFF:
                    c.setBackground(Color.WHITE);
                    break;
            }
        } else {
            if (!isRowSelected(row)) {
                c.setBackground(Color.WHITE);
            }
        }
        return c;
    }

    /**
     * Sets the color coding mode of the table to either color by freshness,
     * food group, or turn off color coding.
     *
     * @param mode the ColorCodingMode to set
     */
    public void setColorCodingMode(ColorCodingMode mode) {
        this.colorCodingMode = mode;
        this.repaint();
    }

    /**
     * Gets the current color coding mode of the table.
     *
     * @return the current ColorCodingMode
     */
    public ColorCodingMode getColorCodingMode() {
        return this.colorCodingMode;
    }
}
