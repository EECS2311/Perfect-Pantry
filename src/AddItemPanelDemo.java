import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AddItemPanelDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Items Management");
        List<Item> items = new ArrayList<>();

        JTextArea displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Pass the items list and the display area to the ui.AddItemPanel
        AddItemPanel addItemPanel = new AddItemPanel(items, displayArea);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.add(addItemPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
