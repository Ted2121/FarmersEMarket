package test;

import static org.junit.Assert.*;

import org.junit.*;

import db_access.DBConnection;


public class TestDatabaseAccess {
	
	
	DBConnection con = null;

	@Before
	public void setUp() {
		con = DBConnection.getInstance();
	}
	
	
	@Test
	public void wasConnected() {
		assertNotNull("Connected - connection cannot be null", con);
		
		con = DBConnection.getInstance();
		assertNotNull("Connected - connection cannot be null", con);		
	}


}
