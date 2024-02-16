import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Home implements ActionListener {

    static JFrame frame;
    private static JPanel homePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel homeButtonsPanel;
    private JButton addNewContainerButton;
    private JButton editContainerNameButton;
    private JPanel containerButtonsPanel;
    private ConcurrentHashMap<JButton, Container> containerMap;
    private int stage;
    private JPanel editPanel;
    private JPanel editNameOfContainerPanel;
    private JPanel editNameOfContainerTitlePanel;
    private JLabel editNameLabel;
    private JPanel editGUIButtonsPanel;
    private JButton editBackToHome;
    private JPanel editContainerButtonsPanel = new JPanel();

    public static void main(String[] args) {
        Home m = new Home();
    }

    public Home() {
        stage = 0;
        frame = new JFrame("Perfect Pantry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(800, 800));
        frame.getContentPane().setBackground(new Color(245, 223, 162));
        containerMap = new ConcurrentHashMap<>();
        initializeHomeGUI();
    }

    public void initializeHomeGUI() {
        homePanel = new JPanel();
        homePanel.setLayout(null);
        frame.getContentPane().add(homePanel);

        titlePanel = new JPanel();
        titlePanel.setSize(800, 90);
        titlePanel.setLocation(0, 0);
        titlePanel.setBackground(new Color(255, 223, 162));
        homePanel.add(titlePanel);

        titleLabel = new JLabel("PERFECT PANTRY");
        titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
        titlePanel.add(titleLabel);

        homeButtonsPanel = new JPanel();
        homeButtonsPanel.setBackground(new Color(192, 237, 203));
        homeButtonsPanel.setBounds(0, 680, 800, 90);
        homePanel.add(homeButtonsPanel);

        addNewContainerButton = new JButton("Add New Container");
        addNewContainerButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        addNewContainerButton.addActionListener(this);
        homeButtonsPanel.add(addNewContainerButton);

        editContainerNameButton = new JButton("Edit Container Name");
        editContainerNameButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        editContainerNameButton.addActionListener(this);
        homeButtonsPanel.add(editContainerNameButton);

        containerButtonsPanel = new JPanel();
        containerButtonsPanel.setBounds(0, 90, frame.getWidth(), frame.getHeight() - 120);
        containerButtonsPanel.setBackground(new Color(245, 213, 152));
        containerButtonsPanel.setLayout(new FlowLayout());
        addContainerButtons(containerButtonsPanel);

        homePanel.add(containerButtonsPanel);
        homePanel.setVisible(true);
    }

    private void changeStageOfHome() {
        if (stage == 0) { // Home screen
            if (editPanel != null) {
                editPanel.setVisible(false);
            }
            homePanel.setVisible(true);
            addContainerButtons(containerButtonsPanel);
        } else if (stage == 1) { // Edit name of container screen
            homePanel.setVisible(false);
            editPanel = new JPanel();
            editPanel.setLayout(null);
            frame.getContentPane().add(editPanel);

            editNameOfContainerPanel = new JPanel();
            editNameOfContainerPanel.setSize(800, 90);
            editNameOfContainerPanel.setBackground(new Color(179, 245, 223));
            editPanel.add(editNameOfContainerPanel);

            editNameLabel = new JLabel("Click the Container Button you wish to rename");
            editNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
            editNameOfContainerPanel.add(editNameLabel);

            editGUIButtonsPanel = new JPanel();
            editGUIButtonsPanel.setBackground(new Color(179, 245, 223));
            editGUIButtonsPanel.setBounds(0, 680, 800, 90);
            editPanel.add(editGUIButtonsPanel);

            editBackToHome = new JButton("Back to Home");
            editBackToHome.addActionListener(this);
            editGUIButtonsPanel.add(editBackToHome);

            editContainerButtonsPanel = new JPanel();
            editContainerButtonsPanel.setBounds(0, 90, frame.getWidth(), frame.getHeight() - 120);
            editContainerButtonsPanel.setBackground(new Color(149, 245, 203));
            editContainerButtonsPanel.setLayout(new FlowLayout());
            addContainerButtons(editContainerButtonsPanel);

            editPanel.add(editContainerButtonsPanel);
            editPanel.setVisible(true);
        }
    }

    private void addNewContainer() {
        String nameOfContainer = JOptionPane.showInputDialog("Please enter a name for your Container:");
        if (nameOfContainer != null && !nameOfContainer.trim().isEmpty()) {
            // Pass 'this' to provide the current Home instance
            Container c = new Container(nameOfContainer, this);
            JButton b = new JButton(c.getName());
            b.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
            b.addActionListener(this);
            containerMap.put(b, c);
            addContainerButtons(containerButtonsPanel);
        }
    }

    private void addContainerButtons(JPanel p) {
        p.removeAll();
        containerMap.forEach((button, container) -> {
            p.add(button);
        });
        p.revalidate();
        p.repaint();
    }

    private void renameContainerButton(JButton b) {
        Container c = containerMap.get(b);
        String nameOfContainer = JOptionPane.showInputDialog("What would you like to rename container " + c.getName() + " to?");
        if (nameOfContainer != null && !nameOfContainer.trim().isEmpty()) {
            c.setName(nameOfContainer);
            b.setText(c.getName()); // Update the button text directly instead of replacing the button in the map
            stage = 0;
            changeStageOfHome();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (stage == 0) {
            if (source == addNewContainerButton) {
                addNewContainer();
            } else if (source == editContainerNameButton) {
                stage = 1;
                changeStageOfHome();
            } else {
                Container c = containerMap.get(source); // This will return null if the button is not found
                if (c != null) {
                    c.getGUI();
                }
            }
        } else if (stage == 1) {
            if (source == editBackToHome) {
                stage = 0;
                changeStageOfHome();
            } else {
                Container c = containerMap.get(source); // This will return null if the button is not found
                if (c != null) {
                    renameContainerButton((JButton) source);
                }
            }
        }
    }

    public static JFrame getFrame() {
        return frame;
    }
}
