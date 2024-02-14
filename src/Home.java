import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Home implements ActionListener, ComponentListener{
	
	/**
	 * Frame window of the application
	 */
	private static JFrame frame;
	
	/**
	 * home panel of the application, will hold all components pertaining to home screen
	 */
	private JPanel homePanel;
	
	/**
	 * Title part of home screen
	 */
	private JPanel titlePanel;
	
	/**
	 * Title name
	 */
	private JLabel titleLabel;
	
	/**
	 * Hold all buttons pertaining to the home screen
	 */
	private JPanel homeButtonsPanel;
	
	/**
	 * Button that will add new container, apart of homeButtonsPanel
	 */
	private JButton addNewContainerButton;
	
	/**
	 * Hold buttons pertaining to its containers
	 */
	private JPanel containerButtonsPanel;
	
	/**
	 * Map of button and its corresponding Container object
	 */
	private HashMap<JButton, Container> containerMap;

	
	public static void main(String[] args) {
		Home m = new Home(); //calls constructer
	}
	
	public Home(){
		//Initialize frame
		frame = new JFrame();
		frame.setTitle("Perfect Pantry");
		frame.setDefaultCloseOperation(3); //Close on exit
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(800, 800)); //resizable with minimum size
		frame.getContentPane().setBackground(new Color(245, 223, 162));
		frame.addComponentListener(this);
		initializeHomeGUI();
		
	}
	
	/**
	 * Initializes the GUI of the home screen
	 */
	public void initializeHomeGUI() {
		//Initialse mainMenuPanel
		homePanel = new JPanel(); //will hold all the components of mainMenuGUI
		homePanel.setLayout(null);
		frame.getContentPane().add(homePanel); 
		
		//Initialize TitlePanel 
		titlePanel = new JPanel();
		titlePanel.setSize(800, 90);
		titlePanel.setLocation(0, 0);
		titlePanel.setBackground(new Color (255, 223, 162));
		homePanel.add(titlePanel);
		
		//Initialize titleLabel
		titleLabel = new JLabel("PERFECT PANTRY");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		titlePanel.add(titleLabel);
		
		//initialize mainMenuButtonsPanel 
		homeButtonsPanel = new JPanel();
		homeButtonsPanel.setBackground(new Color(192, 237, 203));
		homeButtonsPanel.setBounds(0, 680, 800, 90);
		homePanel.add(homeButtonsPanel);
		
		//Components of mainMenuButtonsPanel
		//Initilize containerMap
		containerMap = new HashMap<JButton, Container>();

		//initialize addNewContainerButton
		addNewContainerButton = new JButton("Add New Container");
		addNewContainerButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		addNewContainerButton.addActionListener(this);
		homeButtonsPanel.add(addNewContainerButton);
		
		//initialize containerButtonsPanel
		containerButtonsPanel = new JPanel();
		containerButtonsPanel.setBounds(0, 90, frame.getWidth(), frame.getHeight() - 120);
		containerButtonsPanel.setBackground(new Color(245, 213, 152));
		containerButtonsPanel.setLayout(new FlowLayout());
		addContainerButtons();
		
		homePanel.add(containerButtonsPanel);
		homePanel.setVisible(true);
		
	}
	
	/**
	 * Sets up home gui
	 */
	public void setupHomeGUI() {
		homePanel.setVisible(true);
	}
	
	/**
	 * Adds a new container object and a new button associated with it
	 */
	private void addNewContainer() {
		 String nameOfContainer = JOptionPane.showInputDialog("Please enter a name for your Container:");
		 if(nameOfContainer != null) {
			 JButton b = new JButton(nameOfContainer);
			 containerMap.put(b, new Container(nameOfContainer));
			 
			 b.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
			 addContainerButtons();

		 }
	}
	
	/**
	 * Adds container objects as buttons on the home GUI
	 */
	private void addContainerButtons() {
		containerButtonsPanel.removeAll();
		for(JButton b : containerMap.keySet()) {
			b.addActionListener(this);
			containerButtonsPanel.add(b);
		}
		
		containerButtonsPanel.revalidate(); //refresh panel

	}


	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		titlePanel.setSize(frame.getWidth(), 90);
		homeButtonsPanel.setBounds(0, frame.getHeight() - 120, frame.getWidth(), 120);
		containerButtonsPanel.setBounds(0, 90, frame.getWidth(), frame.getHeight()-120);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addNewContainerButton) {
			addNewContainer();
		}
		
		for(JButton b : containerMap.keySet()) {
			if (e.getSource() == b) {
				Container c = containerMap.get(b);
				c.getGUI();
			}
		}
		
	}

}
