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
	static ProviderDao providerDao;
	static int generatedId;
	static Provider providerToUpdate;
	static Provider providerToDelete;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		providerDao = DaoFactory.getProviderDao();
		providerToUpdate = ModelFactory.getProviderModel("Michel", "Michel", "Michel", "Michel");
		providerDao.createProvider(providerToUpdate);
		
		providerToDelete = ModelFactory.getProviderModel("Michel", "Michel", "Michel", "Michel");
		providerDao.createProvider(providerToDelete);
		
	}
	
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
		providerToUpdate.setFirstName("UpdatedFirstName");
		providerDao.updateProvider(providerToUpdate);
		assertEquals("Should equal \"UpdatedFirstName\".", "UpdatedFirstName",providerDao.findProviderById(providerToUpdate.getId()).getFirstName() );
	}

	@Test
	public void testDeleteProvider() throws SQLException {
		providerDao.deleteProvider(providerToDelete);
		assertNull("Should return null.", providerDao.findProviderById(providerToDelete.getId()));
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException {
		
		Provider createdProvider = ModelFactory.getProviderModel(generatedId, null, null, null, null);
		
		providerDao.deleteProvider(createdProvider);
		providerDao.deleteProvider(providerToUpdate);
		
	}
}
