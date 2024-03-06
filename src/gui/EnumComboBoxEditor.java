package gui;

import domain.logic.Tag;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A custom cell editor for editing enum values within a JComboBox in a JTable cell.
 * It maps the display names of enum values to their corresponding enum constants,
 * allowing the combo box to show human-readable names instead of the enum constant names.
 *
 * @param <E> The enum type that implements the Tag interface, which provides a getDisplayName method.
 */
public class EnumComboBoxEditor extends DefaultCellEditor {
    private Map<String, Object> displayNameToEnumMap = new HashMap<>();

    /**
     * Constructs a new EnumComboBoxEditor for a given array of enum values.
     * The enum values are added to the combo box and to a map that associates display names with enums.
     * The display names are obtained via the {@code getDisplayName} method defined in the {@link Tag} interface,
     * which the enum class must implement.
     *
     * @param values The array of enum values to be included in the combo box. It assumes that the enum implements Tag.
     */
    public <E extends Enum<E> & Tag> EnumComboBoxEditor(E[] values) {
        super(new JComboBox<>());
        JComboBox<String> comboBox = (JComboBox<String>) editorComponent;
        for (E value : values) {
            String displayName = value.getDisplayName();
            comboBox.addItem(displayName);
            displayNameToEnumMap.put(displayName, value);
        }
    }

    /**
     * Returns the actual enum value corresponding to the selected display name in the combo box.
     * The association between display names and enum values is handled internally by a map.
     *
     * @return The enum constant that corresponds to the selected display name.
     */
    @Override
    public Object getCellEditorValue() {
        String displayName = (String) super.getCellEditorValue();
        return displayNameToEnumMap.get(displayName);
    }
}