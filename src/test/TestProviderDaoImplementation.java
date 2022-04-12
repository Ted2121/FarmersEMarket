package test;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.ProviderDao;
import model.ModelFactory;
import model.Provider;

import static org.junit.Assert.*;

import java.sql.*;
import org.junit.*;
import java.util.List;

public class TestProviderDaoImplementation {
	//TODO this class needs to be checked
	Connection dbCon = DBConnection.getInstance().getDBCon();
	static ProviderDao providerDao = DaoFactory.getProviderDao();
	static int generatedId;
	
	@Test
	public void testFindPersonById() throws SQLException{
		Provider result = providerDao.findPersonById(1);
		assertNotNull("The retrieved object shouldn't be null.", result);
	}
	
	@Test
	public void testFindAllPersons() throws SQLException {
		List<Provider> result = providerDao.findAllPersons();
		assertNotNull("The retrieved object shouldn't be null.", result);
	}
	
	@Test
	public void testCreatePerson() throws SQLException {
		Provider testProvider = (Provider) ModelFactory.getProviderModel("TestFirstName", "TestLastName", "testCity", "testCountry");
		generatedId = providerDao.createPerson(testProvider);
		assertNotNull("The retrieved object shouldn't be null.", providerDao.findPersonById(generatedId));
	}
	
	@Test
	public void testUpdatePerson() throws SQLException {
		Provider testProvider = providerDao.findPersonById(generatedId);
		testProvider.setFirstName("UpdatedFirstName");
		providerDao.updatePerson(testProvider);
		assertEquals("Should equal \"UpdatedFirstName\".", providerDao.findPersonById(generatedId).getFirstName());
	}
	
	public void testDeletePerson() throws SQLException {
		Provider testProvider = providerDao.findPersonById(generatedId);
		providerDao.deletePerson(testProvider);
		assertNull("Should return null.", providerDao.findPersonById(generatedId));
	}
}
