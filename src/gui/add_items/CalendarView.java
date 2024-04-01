package gui.add_items;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.logic.container.Container;
import domain.logic.home.Settings;
import domain.logic.item.Item;
import gui.home.HomeView;

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
	private JLabel month = new JLabel();

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
	private Font monthFont = new Font("Lucida Grande", Font.BOLD, 35);

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


	/**
	 * Launches CalendarView of a container
	 * @param container The container the Calendar is based off of
	 * @param c the containerView
	 * @param h the homeView
	 */
	public CalendarView(Container container, ContainerView c, HomeView h) {
		this.container = container;
		actualCurrentDate = Calendar.getInstance();
		nextCal = Calendar.getInstance();

		setupCalendarViewGUI(nextCal);
		addActionListenersToButtons(c, h);

	}

	/**
	 * Sets up the month of the Calendar gui using the current Calendar
	 * @param current The Calendar that will be used to create gui
	 */
	public void setupCalendarViewGUI(Calendar current){
		frame.add(mainPanel);
		mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		mainPanel.setLayout(null);

		topBar.setLayout(new FlowLayout());
		topBar.setBackground(new Color(253, 241, 203));
		JScrollPane scrollTopBar = new JScrollPane(topBar);
		scrollTopBar.setBounds(0, 0, frame.getWidth(), 50);
		
		mainPanel.add(scrollTopBar);

		month.setFont(monthFont);
		topBar.add(month);

		topBar.add(Exit);
		topBar.add(previousMonth);
		topBar.add(actualCurrentMonth);
		topBar.add(nextMonth);
		
		Font f = new Font("Lucida Grande", Font.PLAIN, HomeView.getSettings().getFontSize());
		Exit.setFont(f);
		previousMonth.setFont(f);
		actualCurrentMonth.setFont(f);
		nextMonth.setFont(f);
		

		weekdayBar.setBounds(0, 50, frame.getWidth(), 30);
		weekdayBar.setLayout(new GridLayout(0, 7));
		weekdayBar.setBackground(new Color(253, 241, 203));
		mainPanel.add(weekdayBar);
		addWeekdaysToPanel(weekdayBar);

		daysOfMonthPanel.setBounds(0, 80, frame.getWidth(), frame.getHeight()-100);
		mainPanel.add(daysOfMonthPanel);

		addMonthDays(current);


	}

	/**
	 * Formats String for month and Year of a Calendar
	 * @param current The Calendar that will be used to get month and year of
	 * @return The formatted string in form of "month year"
	 */
	public String getMonthAndYearFormatted(Calendar current) {
		String monthString = monthsOfTheYear[current.get(Calendar.MONTH)];
		int year = current.get(Calendar.YEAR);

		return monthString + " " + year;
	}

	/**
	 * Adds weekday panels to panel given
	 * @param panel the panel the weekday labels should be added to
	 */
	public void addWeekdaysToPanel(JPanel panel) {
		for (int i = 0; i < daysOfTheWeek.length; i++) {
			JLabel weekday = new JLabel(daysOfTheWeek[i]);
			panel.add(weekday);
			weekday.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}
	}

	/**
	 * Adds action listeners to buttons,
	 * Exit uses containerView and HomeView
	 * @param containerView The view of Container
	 * @param homeView The homeView the application uses
	 */
	public void addActionListenersToButtons(ContainerView containerView, HomeView homeView){
		Exit.addActionListener(e -> {
			new ContainerView(homeView, this.container);
			containerView.setupContainerViewGUI(true);

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
			if(nextCal.get(Calendar.MONTH )== 0) {
				nextCal.roll(Calendar.YEAR, false);
			}
			nextCal.roll(Calendar.MONTH, false);
			addMonthDays(nextCal);
		});
	}

	/**
	 * Add the dates and items to the month
	 * @param current The current month the date and items used
	 */
	public void addMonthDays(Calendar current) {
		month.setText(getMonthAndYearFormatted(current));

		daysOfMonthPanel.removeAll();
		Calendar startOfMonth = Calendar.getInstance(); //Start at the first of the calendar month given
		int dayOfWeekStart; //What day of the week the first of the month is on
		int maxDaysInMonth = current.getActualMaximum(Calendar.DAY_OF_MONTH);

		startOfMonth.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), 1);
		dayOfWeekStart = startOfMonth.get(Calendar.DAY_OF_WEEK) -1;

		int numofPanels = setupGridLayout(current, dayOfWeekStart, maxDaysInMonth);
		HashMap<Integer, ArrayList<Item>> itemDate = getItemThatExpireInMonth(current);

		boolean emptyBox;
		JLabel date;
		int day = 1;
		int fontSize = HomeView.getSettings().getFontSize();

		for(int i = 0; i<numofPanels; i++) {
			emptyBox = getEmptyBoxBoolean(day, dayOfWeekStart, maxDaysInMonth, i);
			
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.setBackground(Color.white);

			if(emptyBox) {
				panel.setBackground(new Color(253, 241, 203));
			}
			else {
				date = new JLabel("" + day);
				date.setFont(new Font("Lucida Grande", Font.BOLD, fontSize + 5));
				if(todayDate(current, day)) {
					date.setForeground(Color.RED);
				}
				else {
					date.setForeground(Color.black);
				}
				panel.add(date);
				if(itemDate.containsKey(day)) {
					for (Item item : itemDate.get(day)) {
						JLabel itemName = new JLabel (item.getName());
						itemName.setFont(new Font("Lucida Grande", Font.PLAIN, fontSize));
						panel.add(itemName);
					}
				}
				day++;
			}

			daysOfMonthPanel.add(new JScrollPane(panel));	
		}
		daysOfMonthPanel.repaint();
		daysOfMonthPanel.revalidate();
	}

	/**
	 * sets up the gridLayout of panel and finds how many panels will be used
	 * @param current The current Calendar the GUI is being made of
	 * @param dayOfWeekStart The day that the week starts
	 * @param maxDaysInMonth The amount of days in month
	 * @return The number of panels needed based on the gridlayout
	 */
	public int setupGridLayout(Calendar current, int dayOfWeekStart, int maxDaysInMonth) {
		GridLayout gridLayout;
		int numOfPanels = 0;

		if((dayOfWeekStart == 5 && maxDaysInMonth == 31) || //Thursday + 31 days
				dayOfWeekStart == 6 && maxDaysInMonth >=30) {  //Friday + 30+ days
			gridLayout = new GridLayout(6, 7); //needs extra row
			numOfPanels = 42;
		}
		else {
			gridLayout = new GridLayout(5, 7);
			numOfPanels = 35;
		}
		daysOfMonthPanel.setLayout(gridLayout);
		return numOfPanels;

	}

	/**
	 * Calculates the items that are apart of the Calendar month and returns a list of them
	 * @param current The calendar month the list should be about
	 * @return A hasmap of the integer date and arraylist of items that expire on that date
	 */
	public HashMap<Integer, ArrayList<Item>> getItemThatExpireInMonth(Calendar current){
		List<Item> allItems = HomeView.data.retrieveItems(container);
		HashMap<Integer, ArrayList<Item>> itemDate = new HashMap<Integer, ArrayList<Item>>();
		
		for (Item m : allItems) {
			//Convert Date to Calendar
			Calendar cal = Calendar.getInstance();
			cal.setTime(m.getExpiryDate());

			if (cal.get(Calendar.MONTH) == current.get(Calendar.MONTH) && cal.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
				if(!itemDate.containsKey(cal.get(Calendar.DATE))) {
					itemDate.put(cal.get(Calendar.DATE), new ArrayList<Item>());
				}
				itemDate.get(cal.get(Calendar.DATE)).add(m);
			}
		}
		return itemDate;
	}

	/**
	 * Gets boolean ffor if panel should be empty or not
	 * @param day The day the panel will be on
	 * @param dayOfWeekStart What day the week starts on
	 * @param maxDaysInMonth The amount of days the month has
	 * @param i the current iteration of the loop
	 * @return boolean to see if panel should be empty or not
	 */
	public boolean getEmptyBoxBoolean(int day, int dayOfWeekStart, int maxDaysInMonth, int i) {
		if(i < dayOfWeekStart || day > maxDaysInMonth) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if the day given and the calendar is the same as the actual current date
	 * @param current the month and year of a calendar to check
	 * @param day the day to check
	 * @return If what is given would be the actual current date
	 */
	public boolean todayDate(Calendar current, int day) {
		if (current.get(Calendar.MONTH) == actualCurrentDate.get(Calendar.MONTH) &&
				current.get(Calendar.YEAR) == actualCurrentDate.get(Calendar.YEAR) && 
				day == actualCurrentDate.get(Calendar.DATE)) {
			return true;
			
		}
		else return false;
	}




}
