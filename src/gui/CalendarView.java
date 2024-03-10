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
	private JFrame frame = HomeView.getFrame();

	private JPanel mainPanel = new JPanel();

	private JButton Exit = new JButton("Exit");

	private Container container;

	private static Calendar actualCurrentDate;


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

	private String [] daysOfTheWeek = {"     Sunday", "     Monday", "    Tuesday", "   Wednesday", "    Thursday", "       Friday", "    Saturday"};

	private String [] monthsOfTheYear = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	private Font monthFont = new Font("Lucida Grande", Font.BOLD, 30);


	private JButton nextMonth = new JButton ("Next Month");
	private JButton actualCurrentMonth = new JButton ("Today");
	private JButton previousMonth = new JButton ("Previous Month");

	private Calendar nextCal;




	public CalendarView(Container container, ContainerView c, HomeView h) {
		this.container = container;
		actualCurrentDate = Calendar.getInstance();
		nextCal = Calendar.getInstance();

		frame.add(mainPanel);
		mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		mainPanel.setBackground(Color.pink);

		mainPanel.setLayout(null);

		topBar.setLayout(new FlowLayout());
		topBar.setBounds(0, 0, frame.getWidth(), 50);
		mainPanel.add(topBar);

		month = new JLabel(monthsOfTheYear[actualCurrentDate.get(Calendar.MONTH)] + " " + actualCurrentDate.get(Calendar.YEAR));
		month.setFont(monthFont);
		topBar.add(month);

		weekdayBar.setBounds(0, 50, frame.getWidth(), 30);
		weekdayBar.setLayout(new GridLayout(0, 7));
		mainPanel.add(weekdayBar);

		for (int i = 0; i < daysOfTheWeek.length; i++) {
			JLabel weekday = new JLabel(daysOfTheWeek[i]);
			weekdayBar.add(weekday);
			weekday.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}

		daysOfMonthPanel.setBounds(0, 80, frame.getWidth(), frame.getHeight()-100);
		mainPanel.add(daysOfMonthPanel);

		topBar.add(Exit);
		addMonthDays(actualCurrentDate);
		topBar.add(previousMonth);
		topBar.add(actualCurrentMonth);
		topBar.add(nextMonth);

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
			//addMonthDays()
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
