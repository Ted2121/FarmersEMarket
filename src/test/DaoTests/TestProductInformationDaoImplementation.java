package test.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.*;

import db_access.DaoFactory;
import db_access.DaoInterfaces.*;
import model.*;
import model.Product.*;

public class TestProductInformationDaoImplementation {
	private static ProductInformationDao productInformationDao;
	private static ProductDao productDao;
	private static ProductInformation productInformationToUpdate;
	private static ProductInformation productInformationToDelete;
	private static ProductInformation productInformationToCreate;
	private static Product product1, product2, product3;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		productInformationDao = DaoFactory.getProductInformationDao();
		productDao = DaoFactory.getProductDao();
		product1 = ModelFactory.getProductModel("Test1", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product2 = ModelFactory.getProductModel("Test2", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product3 = ModelFactory.getProductModel("Test3", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productDao.createProduct(product1);
		productDao.createProduct(product2);
		productDao.createProduct(product3);
		productInformationToUpdate = ModelFactory.getProductInformationModel(900, 14, product1.getId());
		productInformationToUpdate.setRelatedProduct(product1);
		productInformationToDelete = ModelFactory.getProductInformationModel(900, 14, product2.getId());
		productInformationToCreate = ModelFactory.getProductInformationModel(900, 14, product3.getId());
		productInformationDao.createProductInformation(productInformationToUpdate);
		productInformationDao.createProductInformation(productInformationToDelete);
	}
	
	@Test
	public void testCreateProductInformation() throws SQLException, Exception {
		//Arrange
		
		//Act
		productInformationDao.createProductInformation(productInformationToCreate);
		
		//Assert
		assertNotNull("The product is in the database", productInformationDao.findProductInformationByProductId(product3.getId(), false));
	}
	
	@Test
	public void testUpdate() throws SQLException, Exception {
		//Arrange
		
		//Act
		productInformationToUpdate.setQuantity(50);
		productInformationDao.updateProductInformation(productInformationToUpdate);
		
		//Assert
		assertEquals("The product is updated in the database", 50, productInformationDao.findProductInformationByProduct(product1, false).getQuantity());
	}
	
	@Test
	public void testDelete() throws SQLException, Exception {
		//Arrange
		
		//Act
		productInformationDao.deleteProductInformation(productInformationToDelete);
		
		//Assert
		assertNull("The product isn't in the database", productInformationDao.findProductInformationByProductId(product2.getId(), false));
	}
	
	@Test
	public void testFindAllWithoutAssociation() throws SQLException, Exception {
		//Arrange
		List<ProductInformation> list =  null;
		int count = 0;
		
		//Act
		list = productInformationDao.findAllProductInformation(false);
		
		//Assert
		for(ProductInformation p : list) {
			count++;
			assertNull(p.getRelatedProduct());
		}
		assertTrue("The list isn't empty", count>0);
	}
	
	@Test
	public void testFindAllWithAssocation() throws SQLException, Exception {
		//Arrange
		List<ProductInformation> list =  null;
		int count = 0;
		
		//Act
		list = productInformationDao.findAllProductInformation(true);
		
		//Assert
		for(ProductInformation p : list) {
			count++;
			assertTrue(p.getRelatedProduct().getId()==p.getId());
		}
		assertTrue("The list isn't empty", count>0);
	}
	
	@Test
	public void testFindByProductWithoutAssociation() throws SQLException, Exception {
		//Arrange
		ProductInformation result = null;
		
		//Act
		result = productInformationDao.findProductInformationByProduct(product1, false);
		
		//Assert
		assertEquals("The quantity is the same", result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals("The location code is the same", result.getLocationCode(), productInformationToUpdate.getLocationCode());
		assertNull("The productInformation has not a relatedProduct", result.getRelatedProduct());
	}
	
	@Test
	public void testFindByProductWithAssociation() throws SQLException, Exception {
		//Arrange
		ProductInformation result = null;
		
		//Act
		result = productInformationDao.findProductInformationByProduct(product1, true);
		
		//Assert
		assertEquals("The quantity is the same", result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals("The location code is the same", result.getLocationCode(), productInformationToUpdate.getLocationCode());
		assertTrue("The productInformation has  a relatedProduct", result.getRelatedProduct().getId()==product1.getId());
	}
	
	@Test
	public void testFindByProductNameWithoutAssociation() throws SQLException, Exception {
		//Arrange
		List<ProductInformation> results = null;
		
		//Act
		results = productInformationDao.findProductInformationByProductName(product1.getProductName(), false);
		
		//Assert
		assertNotNull("Shouldn't return a null object", results);
		for(ProductInformation p : results) {
			assertNull(p.getRelatedProduct());
		}
	}
	
	@Test
	public void testFindByProductNameWithAssociation() throws SQLException, Exception {
		//Arrange
		List<ProductInformation> results = null;
		
		//Act
		results = productInformationDao.findProductInformationByProductName(product1.getProductName(), true);
		
		//Assert
		assertNotNull("Shouldn't return a null object", results);
		for(ProductInformation p : results) {
			assertTrue(p.getRelatedProduct().getId()==p.getId());
		}
	}
	
	@Test
	public void testFindByProductIdWithoutAssociation() throws SQLException, Exception {
		//Arrange
		ProductInformation result = null;
		
		//Act
		result = productInformationDao.findProductInformationByProductId(product1.getId(), false);
		
		//Assert
		assertEquals("The quantity is the same", result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals("The location code is the same", result.getLocationCode(), productInformationToUpdate.getLocationCode());
		assertNull("The productInformation has not a relatedProduct", result.getRelatedProduct());
	}
	
	@Test
	public void testFindByProductIdWithAssociation() throws SQLException, Exception {
		//Arrange
		ProductInformation result = null;
		
		//Act
		result = productInformationDao.findProductInformationByProductId(product1.getId(), true);
		
		//Assert
		assertEquals("The quantity is the same", result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals("The location code is the same", result.getLocationCode(), productInformationToUpdate.getLocationCode());
		assertTrue("The productInformation is the same", result.getRelatedProduct().getId()==product1.getId());
	}
	
	@Test
	public void testAddQuantityToAProduct() throws SQLException, Exception {
		//Arrange
		int quantityToAdd = 5;
		ProductInformation updatedProductInfo = null;
		
		//Act
		productInformationToUpdate.setQuantity(14);
		productInformationDao.updateProductInformation(productInformationToUpdate);
		productInformationDao.addQuantityToProduct(product1, quantityToAdd);
		updatedProductInfo = productInformationDao.findProductInformationByProduct(product1, false);
		productInformationToUpdate.setQuantity(19);
		
		//Assert
		assertTrue("Should return the value +"+quantityToAdd,updatedProductInfo.getQuantity() == productInformationToUpdate.getQuantity());
	}
	
	@Test
	public void testRemoveQuantityToAProduct() throws SQLException, Exception {
		//Arrange
		int quantityToRemove = 5;
		ProductInformation updatedProductInfo = null;
				
		//Act
		productInformationToUpdate.setQuantity(19);
		productInformationDao.updateProductInformation(productInformationToUpdate);
		productInformationDao.removeQuantityToProduct(product1, quantityToRemove);
		updatedProductInfo = productInformationDao.findProductInformationByProduct(product1, false);
		productInformationToUpdate.setQuantity(14);
		
		//Assert
		assertTrue("Should return the value +"+quantityToRemove,updatedProductInfo.getQuantity() == productInformationToUpdate.getQuantity());
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException {
		productInformationDao.deleteProductInformation(ModelFactory.getProductInformationModel(900, 14, product3.getId()));
		productInformationDao.deleteProductInformation(productInformationToUpdate);
		productDao.deleteProduct(product1);
		productDao.deleteProduct(product2);
		productDao.deleteProduct(product3);
	}
}
