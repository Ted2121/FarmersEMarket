package db_access;

import java.util.ArrayList;

import ui.DatabaseConnectionIndicator;

public class ConnectionCheckerThread extends Thread{
		private static ConnectionCheckerThread instance;
		private static boolean exit = false;
		private static ArrayList<DatabaseConnectionIndicator> indicators;
		
		private ConnectionCheckerThread() {
			indicators = new ArrayList<>();
			this.start();
		}
		
		public static ConnectionCheckerThread getInstance() {
			if(instance == null) instance = new ConnectionCheckerThread();
			return instance;
			
		}
		
		public void run() {
			while(!exit) {
				if(DBConnection.getInstance().getDBCon() != null) DatabaseConnectionIndicator.setState(DatabaseConnectionIndicator.GREEN);
				else DatabaseConnectionIndicator.setState(DatabaseConnectionIndicator.RED);
				notifyIndicators();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private static void notifyIndicators() {
			for(DatabaseConnectionIndicator i : indicators) i.refresh();
		}
		
		public static void addIndicator(DatabaseConnectionIndicator dbi) {
			indicators.add(dbi);
		}
		
		public static void removeIndicator(DatabaseConnectionIndicator dbi) {
			indicators.remove(dbi);
		}
	}