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

public class mainMenu implements ActionListener, ComponentListener{
	
	static JFrame frame;
	private JPanel mainMenuPanel;
	private JPanel titlePanel;
	private JLabel titleLabel;
	private JPanel mainMenuButtonsPanel;
	private JButton addNewContainerButton;
	private JPanel containerButtonsPanel;
	
	//private ArrayList<perfectPantryContainer> listOfContainers;
	//private ArrayList<JButton> listOfContainerButtons;
	
	//private HashMap<perfectPantryContainer, JButton> containerMap;
	private HashMap<JButton, perfectPantryContainer> containerMap;

	
	public static void main(String[] args) {
		mainMenu m = new mainMenu(); //calls constructer
	}
	
	public mainMenu(){
		frame = new JFrame();
		frame.setTitle("Perfect Pantry");
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(800, 800));
		frame.getContentPane().setBackground(new Color(245, 223, 162));
		frame.addComponentListener(this);
		initializeMainMenuGUI();
		
	}
	
	public void initializeMainMenuGUI() {
		//Initialse mainMenuPanel
		mainMenuPanel = new JPanel(); //will hold all the components of mainMenuGUI
		mainMenuPanel.setLayout(null);
		frame.getContentPane().add(mainMenuPanel);
		
		//Initialize TitlePanel 
		titlePanel = new JPanel();
		titlePanel.setSize(800, 90);
		titlePanel.setLocation(0, 0);
		titlePanel.setBackground(new Color (255, 223, 162));
		mainMenuPanel.add(titlePanel);
		
		//Initialize titleLabel
		titleLabel = new JLabel("PERFECT PANTRY");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		titlePanel.add(titleLabel);
		
		//initialize mainMenuButtonsPanel 
		mainMenuButtonsPanel = new JPanel();
		mainMenuButtonsPanel.setBackground(new Color(192, 237, 203));
		mainMenuButtonsPanel.setBounds(0, 680, 800, 90);
		mainMenuPanel.add(mainMenuButtonsPanel);
		
		//Components of mainMenuButtonsPanel
		//Initilize containerMap
		containerMap = new HashMap<JButton, perfectPantryContainer>();

		//initialize addNewContainerButton
		addNewContainerButton = new JButton("Add New Container");
		addNewContainerButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		addNewContainerButton.addActionListener(this);
		mainMenuButtonsPanel.add(addNewContainerButton);
		
		//initialize containerButtonsPanel
		containerButtonsPanel = new JPanel();
		containerButtonsPanel.setBounds(0, 90, frame.getWidth(), frame.getHeight() - 120);
		containerButtonsPanel.setBackground(new Color(245, 213, 152));
		containerButtonsPanel.setLayout(new FlowLayout());
		
		addContainerButtons();
		
		mainMenuPanel.add(containerButtonsPanel);
		
		
		mainMenuPanel.setVisible(true);
		
	}
	
	private void addNewContainer() {
		// TODO Auto-generated method stub
		 String nameOfContainer = JOptionPane.showInputDialog("Please enter a name for your Container:");
		 if(nameOfContainer != null) {
			 JButton b = new JButton(nameOfContainer);
			 containerMap.put(b, new perfectPantryContainer(nameOfContainer));
			 
			 b.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
			 addContainerButtons();

		 }
	}
	
	private void addContainerButtons() {
		containerButtonsPanel.removeAll();
		for(JButton b : containerMap.keySet()) {
			b.addActionListener(this);
			containerButtonsPanel.add(b);
		}
		

		containerButtonsPanel.revalidate();

	}


	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		titlePanel.setSize(frame.getWidth(), 90);
		mainMenuButtonsPanel.setBounds(0, frame.getHeight() - 120, frame.getWidth(), 120);
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
		// TODO Auto-generated method stub
		if (e.getSource() == addNewContainerButton) {
			addNewContainer();
		}
		
		for(JButton b : containerMap.keySet()) {
			if (e.getSource() == b) {
				
			}
		}
		
	}

}
