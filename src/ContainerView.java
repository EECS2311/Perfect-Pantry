import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ContainerView implements ActionListener {
	private JPanel containerView;
	private JButton back;
	private boolean hasclicked;
	
	ContainerView(){
		hasclicked = false;
	}
	
	public void setupContainerViewGUI(boolean b) {
		if(hasclicked == false) {
			containerView = new JPanel();
			back = new JButton("Back");
			back.addActionListener(this);
			containerView.add(back);
			Home.frame.add(containerView);
			hasclicked = true;
		}
		
		containerView.setVisible(b);
		Home.getFrame().revalidate();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			setupContainerViewGUI(false);
			Home.setupHomeGUI(true);
			
		}
		
	}

}
