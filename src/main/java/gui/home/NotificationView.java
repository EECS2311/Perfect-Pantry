package main.java.gui.home;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.database.DB; 

public class NotificationView extends JFrame {



    public NotificationView() {
   
        displayExpiringItemsNotification();
    }
  

    private void displayExpiringItemsNotification() {
        // Create an instance of our DB class
        DB db = HomeView.data;
                
        List<String> expiringItems = db.getExpiringItems(); // get expiring items 

        if (!expiringItems.isEmpty()) {
            
            StringBuilder message = new StringBuilder("The following items are expiring soon:\n");//message string to display
            for (String item : expiringItems) {
                message.append("- ").append(item).append("\n");
            }
            // Display the message in a JOptionPane dialog
            JOptionPane.showMessageDialog(HomeView.getFrame(), message.toString(), "Expiring Items Alert", JOptionPane.WARNING_MESSAGE);
        } else {
           
            JOptionPane.showMessageDialog(HomeView.getFrame(), "No expiring items found.", "Expiring Items Alert", JOptionPane.INFORMATION_MESSAGE);  // If there are no expiring item
        }
    }

   
}
