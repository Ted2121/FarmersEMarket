package test.DaoTests;

import db_access.*;
import db_access.DaoInterfaces.ContainSubsetDao;
import db_access.DaoInterfaces.ProviderDao;
import model.*;

import static org.junit.Assert.*;

import java.sql.*;
import org.junit.*;
import java.util.List;

public class TestProviderDaoImplementation {
	//TODO this class needs to be checked
	static ProviderDao providerDao;
	static ContainSubsetDao<Provider> providerSubsetDaoPart;
	static int generatedId;
	static Provider providerToUpdate;
	static Provider providerToDelete;
	
	@BeforeClass
	public static void setUp() throws SQLException, Exception {
		providerDao = DaoFactory.getProviderDao();
		providerSubsetDaoPart = (ContainSubsetDao<Provider>) providerDao;
		providerToUpdate = ModelFactory.getProviderModel("Michel", "Michel", "Michel", "Michel");
		providerDao.createProvider(providerToUpdate);
		
		providerToDelete = ModelFactory.getProviderModel("Michel", "Michel", "Michel", "Michel");
		providerDao.createProvider(providerToDelete);
		
	}
	
	@Test
	public void testFindProviderByIdWithoutAssociation() throws SQLException, Exception{
		Provider result = providerDao.findProviderById(1, false);
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNull("The provider PurchaseOrder list shouldn't be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindProviderByIdWithPurchaseOrderAssociation() throws SQLException, Exception{
		//Arrange
		
		//Act
		Provider result = providerDao.findProviderById(1, true);
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNotNull("The provider PurchaseOrder list should be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindAllProviderWithoutAssociation() throws SQLException, Exception {
		//Arrange
		
		//Act
		List<Provider> results = providerDao.findAllProviders(false);
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNull("The provider PurchaseOrder list shouldn't be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testFindAllProviderWithPurchaseOrderAssociation() throws SQLException, Exception {
		//Arrange
		
		//Act
		List<Provider> results = providerDao.findAllProviders(true);
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNotNull("The provider PurchaseOrder list should be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testFindProviderByFullNameWithoutAssociation() throws SQLException, Exception{
		//Arrange
		
		//Act
		Provider result = providerDao.findProviderByFullName("Cassandra Johnson", false);
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNull("The provider PurchaseOrder list shouldn't be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindProviderByFullNameWithPurchaseOrderAssociation() throws SQLException, Exception{
		Provider result = providerDao.findProviderByFullName("Cassandra Johnson", true);
		assertNotNull("The retrieved object shouldn't be null.", result);
		assertNotNull("The provider PurchaseOrder list should be set",result.getPurchaseOrders());
	}
	
	@Test
	public void testFindProviderByPartialNameWithoutAssociation() throws SQLException, Exception{
		//Arrange
		
		//Act
		List<Provider> results = providerDao.findProvidersByName("n", false);
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNull("The provider PurchaseOrder list should be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testFindProviderByPartialNameWithPurchaseOrderAssociation() throws SQLException, Exception{
		//Arrange
		
		//Act
		List<Provider> results = providerDao.findProvidersByName("n", true);
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", results);
		for(Provider provider : results) {
			assertNotNull("The provider PurchaseOrder list should be set",provider.getPurchaseOrders());
		}
	}
	
	@Test
	public void testCreateProvider() throws SQLException, Exception {
		//Arrange
		Provider testProvider = (Provider) ModelFactory.getProviderModel("TestFirstName", "TestLastName", "testCity", "testCountry");
		
		//Act
		providerDao.createProvider(testProvider);
		generatedId = testProvider.getId();
		
		//Assert
		assertNotNull("The retrieved object shouldn't be null.", providerDao.findProviderById(testProvider.getId(), false));
	}
	
	@Test
	public void testUpdateProvider() throws SQLException, Exception {
		//Arrange
		providerToUpdate.setFirstName("UpdatedFirstName");
		
		//Act
		providerDao.updateProvider(providerToUpdate);
		
		//Assert
		assertEquals("Should equal \"UpdatedFirstName\".", "UpdatedFirstName",providerDao.findProviderById(providerToUpdate.getId(), false).getFirstName() );
	}

	@Test
	public void testDeleteProvider() throws SQLException, Exception {
		//Arrange
		
		//Act
		providerDao.deleteProvider(providerToDelete);
		
		//Assert
		assertNull("Should return null.", providerDao.findProviderById(providerToDelete.getId(), false));
	}
	
	@Test
	public void testFindAllProviderSubset() throws SQLException, Exception{
		//Arrange
		
		//Act
		List<Provider> providerSubsetList = providerSubsetDaoPart.findAllSubset();
		
		//Assert
		assertTrue("Should retrieve a none empty list", providerSubsetList.size()>0);
		assertNull("Should retrieve products without selling price", providerSubsetList.get(0).getCountry());
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException, Exception {
		
		Provider createdProvider = ModelFactory.getProviderModel(generatedId, null, null, null, null);
		
		providerDao.deleteProvider(createdProvider);
		providerDao.deleteProvider(providerToUpdate);
		
	}
}
