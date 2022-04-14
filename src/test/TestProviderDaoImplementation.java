package test;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.ProviderDao;
import model.ModelFactory;
import model.Provider;
import model.PurchaseOrder;

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
		Provider result = providerDao.findProviderById(1);
		assertNotNull("The retrieved object shouldn't be null.", result);
	}
	
	@Test
	public void testFindAllPersons() throws SQLException {
		List<Provider> results = providerDao.findAllProviders();
		assertNotNull("The retrieved object shouldn't be null.", results);
	}
	
	@Test
	public void testCreatePerson() throws SQLException {
		Provider testProvider = (Provider) ModelFactory.getProviderModel("TestFirstName", "TestLastName", "testCity", "testCountry");
		generatedId = providerDao.createProvider(testProvider);
		assertNotNull("The retrieved object shouldn't be null.", providerDao.findProviderById(generatedId));
	}
	
	@Test
	public void testUpdatePerson() throws SQLException {
		Provider testProvider = providerDao.findProviderById(generatedId);
		testProvider.setFirstName("UpdatedFirstName");
		providerDao.updateProvider(testProvider);
		assertEquals("Should equal \"UpdatedFirstName\".", providerDao.findProviderById(generatedId).getFirstName());
	}

	@Test
	public void testDeleteProvider() throws SQLException {
		Provider testProvider = providerDao.findProviderById(generatedId);
		providerDao.deleteProvider(testProvider);
		assertNull("Should return null.", providerDao.findProviderById(generatedId));
	}
}
