package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class StatsView extends JPanel {
	
	static JFrame frame = new JFrame("Perfect Pantry");
	static JPanel p = new StatsView();
	


	public static void main(String[] args) {

		


		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Close on exit
		frame.setVisible(true);
		frame.setResizable(false); // stop resize
		frame.setMinimumSize(new Dimension(800, 600));
		frame.getContentPane().setBackground(new Color(245, 223, 162));
		frame.getContentPane().add(new StatsView());
		frame.setLocationRelativeTo(null);
		frame.add(p);

	

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.fillRect(100, 150, 550,50);
		//g.drawRect(100, 200, 500, 200);
		
	}
	
	
	public StatsView() {
		

		ArrayList<String>total = HomeView.data.getTotalCount();
	
		
		System.out.print(total.size());
		setBackground(new Color(245, 223, 162));
		setLayout(null);
		JComboBox b = new JComboBox();
		JLabel title = new JLabel("Pantry Composition: ");
		JLabel protein = new JLabel("<html>" + "<font color='Brown'>Protein: </font>" + getTotal("Protein", total)+ "</html>");
		JLabel vegetable = new JLabel("<html> <font color='Green'>Vegetables: </font>" + getTotal("Vegetable", total) + "</html>");
		JLabel grain = new JLabel("<html><font color='Orange'>Grain: </font>" + getTotal("Grain", total) +"</html>");
		JLabel fruit = new JLabel("<html><font color='Red'>Fruit: </font>" + getTotal("Fruit", total) + "</html>");
		JLabel dairy = new JLabel("<html><font color='White'>Dairy: </font>" + getTotal("Dairy", total) +"</html>");

		
		JPanel foodGroups = new JPanel (new GridLayout(1, 5, 3,0 ));
		foodGroups.add(protein);
		foodGroups.add(vegetable);
		foodGroups.add(grain);
		foodGroups.add(fruit);
		foodGroups.add(dairy);
		
		add(foodGroups);
		foodGroups.setBounds(100,220,500,50);
		foodGroups.setBackground(null);
		
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		title.setBounds(100, 50, 500, 100);
		JButton createContainer = new JButton("Create");
		b.setBounds(100, 250, 300, 100);
		
		add(title);
		add(createContainer);
		add(b);
		setVisible(true);
		
		
		
	}
	
	public int getTotal(String group, ArrayList<String> s) {
		
			
		
		return s.stream().filter(x -> x!= null && x.equals(group)).toList().size();
		
		
	}
	
	

}


