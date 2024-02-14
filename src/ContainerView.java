import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContainerView implements ActionListener {
	private JPanel containerView;
	private JButton back;
	private Home home;

	public ContainerView(Home home) {
		this.home = home;
		containerView = new JPanel();
		back = new JButton("Back");
		back.addActionListener(this);
		containerView.add(back);
	}

	public void setupContainerViewGUI(boolean isVisible) {
		JFrame frame = Home.getFrame(); // Get the main frame
		if (isVisible) {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			frame.getContentPane().add(containerView); // Add this container view
		} else {
			frame.getContentPane().removeAll(); // Clear the frame's content pane
			home.initializeHomeGUI(); // Reinitialize home GUI components
			// No need to set the frame itself to invisible, just refresh its content
		}
		frame.revalidate();
		frame.repaint();
		// Instead of toggling JFrame visibility, ensure the content pane is correctly updated
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			setupContainerViewGUI(false); // Hide this view
		}
	}
}