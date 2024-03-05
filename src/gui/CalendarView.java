package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.logic.Container;

public class CalendarView {
	private JFrame frame = HomeView.getFrame();
	
	private JPanel mainPanel = new JPanel();
	
	private JButton Exit = new JButton("Exit");
	
	private Container container;
	
	private static Calendar currentDate;
	
	
	/**
	 * Holds JLabel Month, JButton Previous, JButton Current, JButton Next, JButton Exit
	 */
	private JPanel topBar = new JPanel();
	
	private JLabel month;
	
	/**
	 * Holds JLabel for days of the week
	 */
	private JPanel weekdayBar = new JPanel();
	
	/**
	 * Holds day Panels
	 */
	private JPanel daysOfMonthPanel = new JPanel();
	
	private String [] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	private String [] monthsOfTheYear = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private Font monthFont = new Font("Lucida Grande", Font.BOLD, 30);
	
	
	public CalendarView(Container container, ContainerView c, HomeView h) {
		this.container = container;
		currentDate = Calendar.getInstance();
		

		
		
		
		

		frame.add(mainPanel);
		mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		mainPanel.setBackground(Color.pink);
		
		mainPanel.setLayout(null);
		
		topBar.setLayout(new FlowLayout());
		topBar.setBounds(0, 0, frame.getWidth(), 50);
		mainPanel.add(topBar);
		
		topBar.add(Exit);
		
		month = new JLabel(monthsOfTheYear[currentDate.get(Calendar.MONTH)]);
		month.setFont(monthFont);
		topBar.add(month);
		
		weekdayBar.setBounds(0, 50, frame.getWidth(), 30);
		weekdayBar.setLayout(new GridLayout(0, 7));
		mainPanel.add(weekdayBar);
		
		for (int i = 0; i < daysOfTheWeek.length; i++) {
			JLabel weekday = new JLabel("   " +daysOfTheWeek[i]);
			weekdayBar.add(weekday);
			weekday.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Exit.addActionListener(e -> {
			new ContainerView(h, this.container);
			c.setupContainerViewGUI(true);
			
		});
		
		
	}
	
	



}
