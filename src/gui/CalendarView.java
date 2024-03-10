package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.logic.Container;
import domain.logic.Item;

public class CalendarView {
	/**
	 * frame of the gui
	 */
	private JFrame frame = HomeView.getFrame();

	/**
	 * mainPanel that holds all other components for this gui
	 */
	private JPanel mainPanel = new JPanel();

	/**
	 * Button that exits the Calendar gui
	 */
	private JButton Exit = new JButton("Exit");

	/**
	 * Current Container the GUI is making a Calendar of
	 */
	private Container container;

	/**
	 * actual Current date
	 */
	private static Calendar actualCurrentDate;


	/**
	 * Holds JLabel Month, JButton Previous, JButton Current, JButton Next, JButton Exit
	 */
	private JPanel topBar = new JPanel();

	/**
	 * Month and Year
	 */
	private JLabel month;

	/**
	 * Holds JLabel for days of the week
	 */
	private JPanel weekdayBar = new JPanel();

	/**
	 * Holds dates Panels
	 */
	private JPanel daysOfMonthPanel = new JPanel();

	/**
	 * Days of the week
	 */
	private String [] daysOfTheWeek = {"     Sunday", "     Monday", "    Tuesday", "   Wednesday", "    Thursday", "       Friday", "    Saturday"};

	/**
	 * Months of the year
	 */
	private String [] monthsOfTheYear = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	/**
	 * Font for month
	 */
	private Font monthFont = new Font("Lucida Grande", Font.BOLD, 30);

	/**
	 * Button to see next month of calendar
	 */
	private JButton nextMonth = new JButton ("Next Month");
	/**
	 * Button to see Todays date calendar
	 */
	private JButton actualCurrentMonth = new JButton ("Today");

	/**
	 * Button to see previous month
	 */
	private JButton previousMonth = new JButton ("Previous Month");

	/**
	 * Shows calendar view for the month given
	 */
	private Calendar nextCal;




	public CalendarView(Container container, ContainerView c, HomeView h) {
		this.container = container;
		actualCurrentDate = Calendar.getInstance();
		nextCal = Calendar.getInstance();

		setupCalendarViewGUI(nextCal);
		addActionListenersToButtons(c, h);

	}

	public void setupCalendarViewGUI(Calendar current){
		frame.add(mainPanel);
		mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		mainPanel.setLayout(null);

		topBar.setLayout(new FlowLayout());
		topBar.setBounds(0, 0, frame.getWidth(), 50);
		mainPanel.add(topBar);

		month = new JLabel(getMonthAndYear(current));
		month.setFont(monthFont);
		topBar.add(month);
		
		topBar.add(Exit);
		topBar.add(previousMonth);
		topBar.add(actualCurrentMonth);
		topBar.add(nextMonth);

		weekdayBar.setBounds(0, 50, frame.getWidth(), 30);
		weekdayBar.setLayout(new GridLayout(0, 7));
		mainPanel.add(weekdayBar);
		addWeekdaysToPanel(weekdayBar);
		
		daysOfMonthPanel.setBounds(0, 80, frame.getWidth(), frame.getHeight()-100);
		mainPanel.add(daysOfMonthPanel);
		
		addMonthDays(current);
		

	}

	public String getMonthAndYear(Calendar current) {
		String monthString = monthsOfTheYear[current.get(Calendar.MONTH)];
		int year = current.get(Calendar.YEAR);

		return monthString + " " + year;
	}

	public void addWeekdaysToPanel(JPanel panel) {
		for (int i = 0; i < daysOfTheWeek.length; i++) {
			JLabel weekday = new JLabel(daysOfTheWeek[i]);
			panel.add(weekday);
			weekday.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}
	}
	
	public void addActionListenersToButtons(ContainerView c, HomeView h){
		Exit.addActionListener(e -> {
			new ContainerView(h, this.container);
			c.setupContainerViewGUI(true);

		});

		nextMonth.addActionListener(e -> {
			if(nextCal.get(Calendar.MONTH )== 11) {
				nextCal.roll(Calendar.YEAR, true);
			}
			nextCal.roll(Calendar.MONTH, true);
			addMonthDays(nextCal);
		});

		actualCurrentMonth.addActionListener(e -> {
			nextCal = Calendar.getInstance();
			addMonthDays(actualCurrentDate);
		});

		previousMonth.addActionListener(e -> {
			if(nextCal.get(Calendar.MONTH )== 1) {
				nextCal.roll(Calendar.YEAR, false);
			}
			nextCal.roll(Calendar.MONTH, false);
			addMonthDays(nextCal);
		});
	}

	public void addMonthDays(Calendar current) {
		GridLayout g;
		daysOfMonthPanel.removeAll();
		Calendar startOfMonth = Calendar.getInstance(); //Start at the first of the calendar month given
		int dayOfWeekStart; //What day of the week the first of the month is on
		JPanel[] listOfPanels;
		int maxDays = current.getActualMaximum(Calendar.DAY_OF_MONTH);

		//Month Label
		month.setText(monthsOfTheYear[current.get(Calendar.MONTH)] + " " + current.get(Calendar.YEAR));
		month.setFont(monthFont);





		startOfMonth.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), 1);
		//How many rows? Depends on if month starts fri/sat or not
		dayOfWeekStart = startOfMonth.get(Calendar.DAY_OF_WEEK) -1;
		if((dayOfWeekStart == 5 && maxDays == 31) || //Thursday + 31 days
				dayOfWeekStart == 6 && maxDays >=30) {  //Friday + 30+ days
			g = new GridLayout(6, 7); //needs extra row
			listOfPanels = new JPanel[42];
		}
		else {
			g = new GridLayout(5, 7);
			listOfPanels = new JPanel[35];
		}
		daysOfMonthPanel.setLayout(g);


		//Get list of items that expires to this month
		List<Item> items = HomeView.data.retrieveItems(container);
		HashMap<Integer, ArrayList<Item>> itemDate = new HashMap<Integer, ArrayList<Item>>();
		List<Item> currentMonthItems = new ArrayList<Item>();
		for (Item m : items) {
			//Convert Date to Calendar
			Calendar cal = Calendar.getInstance();
			cal.setTime(m.getExpiryDate());

			if (cal.get(Calendar.MONTH) == current.get(Calendar.MONTH) && cal.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
				currentMonthItems.add(m);

				if(!itemDate.containsKey(cal.get(Calendar.DATE))) {
					itemDate.put(cal.get(Calendar.DATE), new ArrayList<Item>());
				}

				itemDate.get(cal.get(Calendar.DATE)).add(m);

			}
		}

		//Add JPanels to daysOfMonthPanel
		boolean firstDay = false;
		JLabel date;
		BorderLayout bl = new BorderLayout();
		int day = 1;

		JPanel datePanel;
		JPanel numberPanel;
		JPanel ItemPanel;


		for(int i = 0; i<(listOfPanels.length); i++) {
			JPanel p = new JPanel();
			//			p.setLayout(new BorderLayout());
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.setBorder(BorderFactory.createLineBorder(Color.black));


			if(!firstDay) { //if first day hasnt been placed or last day has been placed
				if(i == dayOfWeekStart) { //if month started
					date = new JLabel("" + day);
					p.add(date);
					if(itemDate.containsKey(day)) {
						for (Item item : itemDate.get(day)) {
							p.add(new JLabel(item.getName()));
						}
					}

					day++;
					firstDay = true;
				}
				else {
					p.setBackground(Color.gray);
				}
				daysOfMonthPanel.add(p);
			}
			else {
				date = new JLabel("" + day);
				p.add(date);
				if(itemDate.containsKey(day)) {
					for (Item item : itemDate.get(day)) {
						p.add(new JLabel(item.getName()));
					}
				}

				day++;

				if(day > maxDays) {
					firstDay = false;
				}	
			}
			listOfPanels[i] = p;
			daysOfMonthPanel.add(new JScrollPane(p));	
		}
		daysOfMonthPanel.repaint();
		daysOfMonthPanel.revalidate();



	}




}
