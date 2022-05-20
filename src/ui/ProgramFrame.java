package ui;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProgramFrame{
	private static JFrame frame;
	private static JPanel panel;
	private static Color bgcolor = new Color(0xdddddd);
	private static Dimension defaultSize = new Dimension(800,600);
	
	
	private ProgramFrame() {
		frame = new JFrame();
		frame.setTitle("Farmer's E-market Management Software");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		changePanel(new LoginPanel());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public static void changePanel(JPanel newPanel) {
		if(panel!=null) frame.remove(panel);
		panel = newPanel;
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ProgramFrame();
	}
	
	public static Color getBgcolor() {
		return bgcolor;
	}
	
	public static Dimension getDefaultSize() {
		return defaultSize;
	}
}
