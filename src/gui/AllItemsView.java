package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import domain.logic.container.Container;
import domain.logic.item.Item;
import domain.logic.item.ItemUtility;
import gui.home.HomeView;

public class AllItemsView extends JPanel implements ActionListener {
	
	public static AllItemsView instance;
	
	private JButton backButton = new JButton("Back");
	
	private DefaultTableModel tableModel; 
		
	private JTable allItemsTable;
	
	private Font font;
	
	private static ConcurrentHashMap<JButton, Container> containerMap;
		
	private AllItemsView() {
		
		setLayout(new BorderLayout());
		setBackground(new Color(253, 241, 203));
		backButton.addActionListener(this);
		add(backButton, BorderLayout.NORTH);
		backButton.setFont(font);
		
		displayAllItems();
		allItemsTable.setFont(font);
		
		add(new JScrollPane(allItemsTable), BorderLayout.CENTER);

		allItemsTable.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				int row = allItemsTable.getSelectedRow();
				if (row != -1) {
					String name = tableModel.getValueAt(row, 5).toString();
					
					HomeView.getContainerMap().forEach((button, container) -> {
						if (name.equals(container.getName())) {
							container.getGUI();
						}
							
					});
				}
			}
		});
		
	}
	
	public static AllItemsView getInstance() {
		return new AllItemsView();
	}
	
	private void displayAllItems() {
		
		containerMap = HomeView.getContainerMap();
		String[] columnNames = {"Name", "Quantity", "Expiry Date", "Food Group", "Food Freshness", "Container"};
		
		tableModel = new DefaultTableModel(null, columnNames) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		
		containerMap.forEach((button, container) -> {
			ItemUtility.assignFoodFreshness(container);
			
			List<Item> items = HomeView.data.retrieveItems(container);
			
			for (Item item : items) {
				tableModel.addRow(new Object[] { item.getName(), item.getQuantity(), ItemUtility.dateFormat(item.getExpiryDate()),
						item.getFoodGroupTag(), item.getFoodFreshnessTag(), container.getName() });
			}
		});
		
		allItemsTable = new JTable(tableModel);
		addFonts();
	}
	
	public static void setAllItemsViewVisibility(boolean b) {
		JFrame frame = HomeView.getFrame();
		
		if (b) {
			frame.getContentPane().removeAll();
			AllItemsView allItemsView = AllItemsView.getInstance();
			HomeView.getFrame().add(allItemsView);
		
		} else {
			frame.getContentPane().removeAll();
			HomeView.getHomeView().setHomeViewVisibility(true);
		}
		
		frame.revalidate();
		frame.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			setAllItemsViewVisibility(false);
		}
		
	}
	
	public JTable getTable() {
		return this.allItemsTable;
	}
	
	public void addFonts() {
		Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
		getTable().setFont(f);
		getTable().setRowHeight(getTable().getRowHeight()+10);
	}
}
