package ui;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import db_access.ConnectionCheckerThread;

public class DatabaseConnectionIndicator extends JLabel{
	public static final boolean GREEN = true;
	public static final boolean RED = false;
	private static boolean state;
	private Image red;
	private Image green;
	private ConnectionCheckerThread connectionCheckerThread;
	
	
	public DatabaseConnectionIndicator() {
		try {
		    Image img = ImageIO.read(getClass().getResource("/ui/assets/red.png"));
		    red = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		    img = ImageIO.read(getClass().getResource("/ui/assets/green.png"));
		    green = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		    setState(RED);
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		refresh();
		connectionCheckerThread = ConnectionCheckerThread.getInstance();
		ConnectionCheckerThread.addIndicator(this);
		
	}
	
	public static void setState(boolean newState) {
		state = newState;
	}
	
	public void refresh() {
		if(state) setIcon(new ImageIcon(green));
		else setIcon(new ImageIcon(red));
	}
	
	
}
