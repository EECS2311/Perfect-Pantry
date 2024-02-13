import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class mainMenu {
	
	static JFrame frame;
	private JPanel mainMenuPanel;
	
	
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
		
		initializeMainMenuGUI();
		
	}
	
	public void initializeMainMenuGUI() {
		//Initialse mainMenuPanel
		mainMenuPanel = new JPanel(); //will hold all the components of mainMenuGUI
		mainMenuPanel.setLayout(null);
		frame.getContentPane().add(mainMenuPanel);
		
		
		
		
		
		
	}

}
