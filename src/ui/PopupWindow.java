package ui;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class PopupWindow extends JFrame{
	
	private JPanel panel;
	private JButton saveButton;
	
	public PopupWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(ProgramFrame.getFrame());
		setSize(new Dimension(300, 450));
		setResizable(false);
		setVisible(true);
		panel = new JPanel();
		add(panel);
		panel.setBackground(ProgramFrame.getBgcolor());
		panel.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(ProgramFrame.getBgcolor());
		panel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout());
		saveButton = new JButton("Save & Close");
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(saveButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		buttonPanel.add(DatabaseConnectionIndicator.getInstance(), BorderLayout.CENTER);
		
		cancelButton.addActionListener(e -> {
			dispose();
		});
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
