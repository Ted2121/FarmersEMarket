package test.DaoTests;

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
	public static void setUp() throws SQLException, Exception {
		providerDao = DaoFactory.getProviderDao();
		providerToUpdate = ModelFactory.getProviderModel("Michel", "Michel", "Michel", "Michel");
		providerDao.createProvider(providerToUpdate);
		
		providerToDelete = ModelFactory.getProviderModel("Michel", "Michel", "Michel", "Michel");
		providerDao.createProvider(providerToDelete);
		
	}
	
	@Test
	public void testFindPersonByIdWithoutAssociation() throws SQLException, Exception{
		Provider result = providerDao.findProviderById(1, false);
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNull("The provider PurchaseOrder list shouldn't be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindPersonByIdWithPurchaseOrderAssociation() throws SQLException, Exception{
		Provider result = providerDao.findProviderById(1, true);
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNotNull("The provider PurchaseOrder list should be set",result.getPurchaseOrders());
	}

	
	@Test
	public void testFindAllPersonsWithoutAssociation() throws SQLException, Exception {
		List<Provider> results = providerDao.findAllProviders(false);
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNull("The provider PurchaseOrder list shouldn't be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testFindAllPersonsWithPurchaseOrderAssociation() throws SQLException, Exception {
		List<Provider> results = providerDao.findAllProviders(true);
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNotNull("The provider PurchaseOrder list should be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testFindPersonByFullNameWithoutAssociation() throws SQLException, Exception{
		Provider result = providerDao.findProviderByFullName("Cassandra Johnson", false);
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNull("The provider PurchaseOrder list shouldn't be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindPersonByFullNameWithPurchaseOrderAssociation() throws SQLException, Exception{
		Provider result = providerDao.findProviderByFullName("Cassandra Johnson", true);
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNotNull("The provider PurchaseOrder list should be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindPersonByNameWithoutAssociation() throws SQLException, Exception{
		List<Provider> results = providerDao.findProvidersByName("n", false);
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNull("The provider PurchaseOrder list should be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testFindPersonByNameWithPurchaseOrderAssociation() throws SQLException, Exception{
		List<Provider> results = providerDao.findProvidersByName("n", true);
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNotNull("The provider PurchaseOrder list should be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testCreatePerson() throws SQLException, Exception {
		Provider testProvider = (Provider) ModelFactory.getProviderModel("TestFirstName", "TestLastName", "testCity", "testCountry");
		providerDao.createProvider(testProvider);
		generatedId = testProvider.getId();
		assertNotNull("The retrieved object shouldn't be null.", providerDao.findProviderById(testProvider.getId(), false));
	}
	
	@Test
	public void testUpdatePerson() throws SQLException, Exception {
		providerToUpdate.setFirstName("UpdatedFirstName");
		providerDao.updateProvider(providerToUpdate);
		assertEquals("Should equal \"UpdatedFirstName\".", "UpdatedFirstName",providerDao.findProviderById(providerToUpdate.getId(), false).getFirstName() );
	}

	@Test
	public void testDeleteProvider() throws SQLException, Exception {
		providerDao.deleteProvider(providerToDelete);
		assertNull("Should return null.", providerDao.findProviderById(providerToDelete.getId(), false));
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException, Exception {
		
		Provider createdProvider = ModelFactory.getProviderModel(generatedId, null, null, null, null);
		
		providerDao.deleteProvider(createdProvider);
		providerDao.deleteProvider(providerToUpdate);
		
	}
}
