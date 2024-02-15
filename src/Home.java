import java.awt.Button;
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

public class Home implements ActionListener{

	/**
	 * Frame window of the application
	 */
	static JFrame frame;

	/**
	 * home panel of the application, will hold all components pertaining to home screen
	 */
	private static JPanel homePanel;

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

	private JButton editContainerNameButton;

	/**
	 * Hold buttons pertaining to its containers
	 */
	private JPanel containerButtonsPanel;

	/**
	 * Map of button and its corresponding Container object
	 */
	private HashMap<JButton, Container> containerMap;

	/**
	 * Stages of the home screen
	 * 0 = home
	 * 1 = edit screen
	 */
	private int stage; 
	
	/**
	 * main components of edit view
	 */
	private JPanel editPanel;
	
	/**
	 * Hold components of editView
	 */
	private JPanel editNameOfContainerPanel;
	
	/**
	 * Title panel of edit view
	 */
	private JPanel editNameOfContainerTitlePanel;
	
	/**
	 * Text of edit view
	 */
	private JLabel editNameLabel;
	
	/**
	 * Holds buttons of edit view
	 */
	private JPanel editGUIButtonsPanel;
	
	/**
	 * Button to go back from edit screen to home screen
	 */
	private JButton editBackToHome;
	
	/**
	 * Panel to hold container buttons
	 */
	private JPanel editContainerButtonsPanel = new JPanel();


	public static void main(String[] args) {
		Home m = new Home(); //calls constructer
	}

	public Home(){
		stage = 0;
		//Initialize frame
		frame = new JFrame();
		frame.setTitle("Perfect Pantry");
		frame.setDefaultCloseOperation(3); //Close on exit
		frame.setVisible(true);
		frame.setResizable(false); //stop resize
		frame.setMinimumSize(new Dimension(800, 800));
		frame.getContentPane().setBackground(new Color(245, 223, 162));

		initializeHomeGUI();

	}

	/**
	 * Initializes the GUI of the home screen
	 */
	public void initializeHomeGUI() {
		//Initialse mainMenuPanel
		homePanel = new JPanel(); //will hold all the components of mainMenuGUI
		homePanel.setLayout(null);
		getFrame().getContentPane().add(homePanel); 

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

		//Initialize editContainerNameButton
		editContainerNameButton = new JButton("Edit Container Name");
		editContainerNameButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		editContainerNameButton.addActionListener(this);
		homeButtonsPanel.add(editContainerNameButton);

		//initialize containerButtonsPanel
		containerButtonsPanel = new JPanel();
		containerButtonsPanel.setBounds(0, 90, getFrame().getWidth(), getFrame().getHeight() - 120);
		containerButtonsPanel.setBackground(new Color(245, 213, 152));
		containerButtonsPanel.setLayout(new FlowLayout());
		addContainerButtons(containerButtonsPanel);

		homePanel.add(containerButtonsPanel);
		homePanel.setVisible(true);

	}


	public void changeStageOfHome() {
		containerButtonsPanel.removeAll(); //reset containerButtonsPanel
		editContainerButtonsPanel.removeAll();
		
		if(stage == 0) { //Home screen
			editPanel.setVisible(false);
			homePanel.setVisible(true);

			addContainerButtons(containerButtonsPanel);

			addNewContainerButton.addActionListener(this);
			editContainerNameButton.addActionListener(this);

			for(JButton b : containerMap.keySet()) {
				b.addActionListener(this);
			}



		}

		if (stage == 1) { //Edit name of container screen
			homePanel.setVisible(false);
			//Initialse editPanel
			editPanel = new JPanel(); //will hold all the components of mainMenuGUI
			editPanel.setLayout(null);
			getFrame().getContentPane().add(editPanel); 

			editNameOfContainerPanel = new JPanel();
			editNameOfContainerPanel.setSize(800, 90);
			editNameOfContainerPanel.setBackground(new Color (179, 245, 223));
			editPanel.add(editNameOfContainerPanel);

			//Initialize editNameOfContainerTitlePanel 
			editNameOfContainerTitlePanel = new JPanel();
			editNameOfContainerTitlePanel.setSize(800, 90);
			editNameOfContainerTitlePanel.setLocation(0, 0);
			editNameOfContainerTitlePanel.setBackground(new Color (179, 245, 223));
			editNameOfContainerPanel.add(editNameOfContainerTitlePanel);

			//Initialize titleLabel
			editNameLabel = new JLabel("Click the Container Button you wish to rename");
			editNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
			editNameOfContainerPanel.add(editNameLabel);

			//initialize editGUIButtonsPanel 
			editGUIButtonsPanel = new JPanel();
			editGUIButtonsPanel.setBackground(new Color(179, 245, 223));
			editGUIButtonsPanel.setBounds(0, 680, 800, 90);
			editPanel.add(editGUIButtonsPanel);

			//Component of editGUIButtonsPanel
			editBackToHome = new JButton("Back to Home");
			editBackToHome.addActionListener(this);
			editGUIButtonsPanel.add(editBackToHome);

			//initialize containerButtonsPanel
			editContainerButtonsPanel = new JPanel();
			editContainerButtonsPanel.setBounds(0, 90, getFrame().getWidth(), getFrame().getHeight() - 120);
			editContainerButtonsPanel.setBackground(new Color(149, 245, 203));
			editContainerButtonsPanel.setLayout(new FlowLayout());
			addContainerButtons(editContainerButtonsPanel);

			editPanel.add(editContainerButtonsPanel);
			editPanel.setVisible(true);




		}

	}

	/**
	 * Sets up home gui
	 */
	public static void setupHomeGUI(boolean b) {
		homePanel.setVisible(b);
		Home.getFrame().revalidate();
	}

	/**
	 * Adds a new container object and a new button associated with it
	 */
	private void addNewContainer() {
		String nameOfContainer = JOptionPane.showInputDialog("Please enter a name for your Container:");
		if(nameOfContainer != null) { //if not cancelled
			Container c = new Container(nameOfContainer);
			JButton b = new JButton(c.getName());
			containerMap.put(b, c);

			b.setFont(new Font("Lucida Grande", Font.PLAIN, 17));

			addContainerButtons(containerButtonsPanel);

		}
	}

	/**
	 * Adds container objects as buttons on the given panel
	 */
	private void addContainerButtons(JPanel p) {
		p.removeAll();
		for(JButton b : containerMap.keySet()) {
			b.addActionListener(this);
			p.add(b);

		}

		p.revalidate(); //refresh panel

	}

	public void renameContainerButton(JButton b) {
		Container c = containerMap.get(b);
		String nameOfContainer = JOptionPane.showInputDialog("What would you like to rename container " + c.getName() + " to?");
		if (!(nameOfContainer.equals(null))) {
			c.setName(nameOfContainer);

			//replace button in hashmap
			containerMap.remove(b);
			JButton temp = new JButton(c.getName());
			temp.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
			containerMap.put(temp, c);
			stage = 0;
			changeStageOfHome();
		}
	}


	public static JFrame getFrame() {
		return frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(stage == 0) { //home screen
			if (e.getSource() == addNewContainerButton) {
				addNewContainer();
			}
			else if (e.getSource() == editContainerNameButton) {
				stage = 1; 
				changeStageOfHome();
			}

			for(JButton b : containerMap.keySet()) {
				if (e.getSource() == b) {
					Container c = containerMap.get(b);

					c.getGUI();
				}
			}
		}


		else if(stage == 1) {
			if (e.getSource() == editBackToHome) {
				stage = 0;
				changeStageOfHome();
			}

			for(JButton b : containerMap.keySet()) {
				if (e.getSource() == b) {
					renameContainerButton(b);
				}
			}
		}

	}



}



