package gui;

import domain.logic.Tag;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class EnumComboBoxEditor extends DefaultCellEditor {
    private Map<String, Object> displayNameToEnumMap = new HashMap<>();

    public <E extends Enum<E> & Tag> EnumComboBoxEditor(E[] values) {
        super(new JComboBox<>());
        JComboBox<String> comboBox = (JComboBox<String>) editorComponent;
        for (E value : values) {
            String displayName = value.getDisplayName();
            comboBox.addItem(displayName);
            displayNameToEnumMap.put(displayName, value);
        }
    }

    @Override
    public Object getCellEditorValue() {
        String displayName = (String) super.getCellEditorValue();
        return displayNameToEnumMap.get(displayName);
    }
}
