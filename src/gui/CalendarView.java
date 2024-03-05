package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.logic.Container;

public class CalendarView {
	private JFrame frame = HomeView.getFrame();
	
	private JPanel mainPanel = new JPanel();
	
	private JButton Exit = new JButton("Exit");
	
	private Container container;
	
	
	public CalendarView(Container container, ContainerView c, HomeView h) {
		this.container = container;
		//frame.getContentPane().setVisible(false);

		frame.add(mainPanel);
		mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		mainPanel.setBackground(Color.pink);
		
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(Exit);
		
		
		Exit.addActionListener(e -> {
			new ContainerView(h, this.container);
			c.setupContainerViewGUI(true);
			
		});
		
		
	}



}
