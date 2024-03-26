package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class StatsView extends JPanel {
	
	static JFrame frame = new JFrame("Perfect Pantry");
	static JPanel p = new StatsView();
	
	public ArrayList<String> allItems = HomeView.data.getTotalCount();
	private int totalItemCount = allItems.size();
	
	private final int BAR_LENGTH = 550;
	private final int LEFT_EDGE = 100;
	


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
				
		double protein  = (double)getTotal("Protein", allItems)/totalItemCount ;
		double vegetable  = (double)getTotal("Vegetable", allItems)/totalItemCount ;
		double grain  = (double)getTotal("Grain", allItems)/totalItemCount ;
		double fruit  = (double)getTotal("Fruit", allItems)/totalItemCount ;
		double dairy  = (double)getTotal("Dairy", allItems)/totalItemCount ;
		
		g.setColor(Color.BLACK);
		
		g.fillRect(LEFT_EDGE, 150, (int)(BAR_LENGTH*protein), 50);
		
		g.setColor(Color.RED);
		g.fillRect(LEFT_EDGE + (int)(BAR_LENGTH*protein), 150, (int)(550*vegetable), 50);

		g.setColor(Color.ORANGE);
		g.fillRect(LEFT_EDGE + (int)(BAR_LENGTH*protein) + (int)(BAR_LENGTH*vegetable), 150, (int)(BAR_LENGTH*grain), 50);
		
		g.setColor(Color.RED);
		g.fillRect(LEFT_EDGE + (int)(BAR_LENGTH*protein) + (int)(BAR_LENGTH*vegetable) + (int)(BAR_LENGTH*grain), 150, (int)(BAR_LENGTH*fruit), 50);
		
		
		g.setColor(Color.WHITE);
		g.fillRect(LEFT_EDGE + (int)(BAR_LENGTH*protein) + (int)(BAR_LENGTH*vegetable) + (int)(BAR_LENGTH*grain) + (int)(BAR_LENGTH*fruit), 150, (int)(550*dairy), 50);
		
		
	}
	
	
	public StatsView() {
		
	
		setBackground(new Color(245, 223, 162));
		setLayout(null);
		JComboBox b = new JComboBox();
		JLabel title = new JLabel("Pantry Composition: ");
		JLabel protein = new JLabel("<html>" + "<font color='Brown'>Protein: </font>" + getTotal("Protein", allItems)+ "</html>");
		JLabel vegetable = new JLabel("<html> <font color='Green'>Vegetables: </font>" + getTotal("Vegetable", allItems) + "</html>");
		JLabel grain = new JLabel("<html><font color='Orange'>Grain: </font>" + getTotal("Grain", allItems) +"</html>");
		JLabel fruit = new JLabel("<html><font color='Red'>Fruit: </font>" + getTotal("Fruit", allItems) + "</html>");
		JLabel dairy = new JLabel("<html><font color='White'>Dairy: </font>" + getTotal("Dairy", allItems) +"</html>");

		
		JPanel foodGroups = new JPanel (new GridLayout(1, 5, 3,0 ));
		foodGroups.add(protein);
		foodGroups.add(vegetable);
		foodGroups.add(grain);
		foodGroups.add(fruit);
		foodGroups.add(dairy);
		
		add(foodGroups);
	
		foodGroups.setBounds(LEFT_EDGE,220,500,50);
		foodGroups.setBackground(null);
		
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		title.setBounds(LEFT_EDGE, 50, 500, 100);
	
		b.setBounds(LEFT_EDGE, 250, 300, 100);
		
		add(title);
		add(b);
		setVisible(true);
		
		
		
	}
	
	public int getTotal(String group, ArrayList<String> s) {
		
		return s.stream().filter(x -> x!= null && x.equals(group)).toList().size();
		
		
	}
	
	

}


