import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class mainMenu {
	
	static JFrame frame;
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
		
	}

}
