package ui;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DatabaseConnectionIndicator extends JLabel{
	public static final boolean GREEN = true;
	public static final boolean RED = false;
	private boolean exit;
	private Image red;
	private Image green;
	private static DatabaseConnectionIndicator instance;
	
	public static DatabaseConnectionIndicator getInstance() {
		if(instance == null) instance = new DatabaseConnectionIndicator();
		return instance;
	}
	
	private DatabaseConnectionIndicator() {
		try {
		    Image img = ImageIO.read(getClass().getResource("/ui/assets/red.png"));
		    red = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		    img = ImageIO.read(getClass().getResource("/ui/assets/green.png"));
		    green = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		    setState(RED);
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		exit = false;
		ConnectionCheckerThread connectionCheckerThread = new ConnectionCheckerThread();
		connectionCheckerThread.start();
		
	}
	
	public void setState(boolean state) throws NullPointerException{
		if(state) setIcon(new ImageIcon(green));
		else setIcon(new ImageIcon(red));
	}
	
	private class ConnectionCheckerThread extends Thread{
		public void run() {
			while(!exit) {
				if(db_access.DBConnection.getInstance().getDBCon() != null) setState(GREEN);
				else setState(RED);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
