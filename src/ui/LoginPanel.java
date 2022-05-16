package ui;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	
	
	public LoginPanel() {
		ProgramFrame.getFrame().setSize(new Dimension(400,200));
		ProgramFrame.getFrame().setResizable(false);
		setLayout(null);
		setBackground(ProgramFrame.getBgcolor());
		JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		usernameLabel.setBounds(50,35,70,30);
		passwordLabel.setBounds(50,75,70,30);
		usernameLabel.setHorizontalAlignment(JLabel.RIGHT);
		passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(usernameLabel);
		add(passwordLabel);
		JTextField usernameField = new JTextField();
		JTextField passwordField = new JPasswordField();
		usernameField.setBounds(125,35,200,30);
		passwordField.setBounds(125,75,200,30);
		add(usernameField);
		add(passwordField);
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(255,115,70,30);
		add(loginButton);
		loginButton.addActionListener(e -> {
			ProgramFrame.changePanel(new HomePanel());
			ProgramFrame.getFrame().setSize(new Dimension(800,600));
			ProgramFrame.getFrame().setLocationRelativeTo(null);
		});
	}
}
