package test.DaoTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.*;
import model.*;
import test.testingClass.TestingSaleOrder;
import model.Product.*;

public class TestProductDaoImplementation {
	private static ProductDao productDao;
	private static ContainSubsetDao<Product> productSubsetDaoPart;
	private static Product productToUpdate;
	private static Product productToDelete;
	private static Product productToCreate;
	private static LineItem lineItem;
	private static ProductInformation productInformation;
	private static LineItemDao lineItemDao;
	private static ProductInformationDao productInformationDao;
	private static int idOrder;
	private static TestingSaleOrder order;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		productDao = DaoFactory.getProductDao();
		productSubsetDaoPart = (ContainSubsetDao<Product>) productDao;
		lineItemDao = DaoFactory.getLineItemDao();
		productInformationDao = DaoFactory.getProductInformationDao();
		productToCreate = ModelFactory.getProductModel("TestCreate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productToUpdate = ModelFactory.getProductModel("TestUpdate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productToDelete = ModelFactory.getProductModel("TestDelete", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productDao.createProduct(productToUpdate);
		productDao.createProduct(productToDelete);
		idOrder = DaoFactory.getOrderDao().createEmptyOrder();
		order = new TestingSaleOrder(idOrder);
		lineItem = ModelFactory.getLineItemModel(5, productToUpdate, order);
		lineItemDao.createLineItem(lineItem);
		productInformation = ModelFactory.getProductInformationModel(156, 14, productToUpdate.getId());
		productInformationDao.createProductInformation(productInformation);
		productToUpdate.setRelatedProductInformation(productInformation);
		List<LineItem> lineItems = new ArrayList<LineItem>();
		lineItems.add(lineItem);
		productToUpdate.setRelatedLineItems(lineItems);
	}
	
	@Test
	public void testCreateProductInformation() throws SQLException, Exception {
		//Arrange
		
		//Act
		productDao.createProduct(productToCreate);
		//Assert
		assertNotNull("The product is in the database",productDao.findProductById(productToCreate.getId(), false, false));
	}
	
	@Test
	public void testUpdate() throws SQLException, Exception {
		//Arrange
		
		//Act
		productToUpdate.setSellingPrice(50d);
		productDao.updateProduct(productToUpdate);		
		//Assert
		assertTrue("Should return 50d", productDao.findProductById(productToUpdate.getId(),false, false).getSellingPrice() == 50d);
	}
	
	@Test
	public void testDelete() throws SQLException, Exception {
		//Arrange
		
		//Act
		productDao.deleteProduct(productToDelete);		
		//Assert
		assertNull("The product isn't in the database", productDao.findProductById(productToDelete.getId(), false, false));
	}
	
	@Test
	public void testFindAllWithoutAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> list=null;
		int count = 0;
		
		//Act
		list = productDao.findAllProducts(false, false);
		
		//Assert
		for(Product p : list) {
			count++;
			assertNull(p.getRelatedProductInformation());
			assertNull(p.getRelatedLineItems());
		}
		assertTrue("The list ins't empty", count>0);
	}
	
	@Test
	public void testFindAllWithProductInformationAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> list = null;
		
		//Act
		list = productDao.findAllProducts(false, true);
		
		//Assert
		for(Product p : list) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The product Update has a relatedProductInformation", p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
				assertNull("The product Update has not relatedLineItems", p.getRelatedLineItems());
			}
			else {
				assertNotNull(p.getRelatedProductInformation());
				assertNull(p.getRelatedLineItems());
			}
		}
		
	}
	
	@Test
	public void testFindAllWithLineItemAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> list = null;
		
		//Act
		list = productDao.findAllProducts(true, false);
		
		//Assert
		for(Product p : list) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The product has relatedLineItems", !p.getRelatedLineItems().isEmpty());
				assertNull("The product has not a relatedProductInformation", p.getRelatedProductInformation());
			}
			else {
				assertNotNull(p.getRelatedLineItems());
				assertNull(p.getRelatedProductInformation());
			}
		}
		
	}
	
	@Test
	public void testFindAllWithLineItemAndProductInformationAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> list = null;
		
		//Act
		list = productDao.findAllProducts(true, true);
		
		//Assert
		for(Product p : list) {
			if(p.getId() == productToUpdate.getId()) {
				assertFalse("The product has relatedLineItems", p.getRelatedLineItems().isEmpty());
				assertTrue("The product has a relatedProductInformation", p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
			}
			else {
				assertNotNull(p.getRelatedLineItems());
				assertNotNull(p.getRelatedProductInformation());
			}
		}
		
	}
	
	@Test
	public void testFindByProductNameWithoutAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductByProductName("TestUpdate", false, false);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			assertNull(p.getRelatedProductInformation());
			assertNull(p.getRelatedLineItems());
		}
	}
	
	@Test
	public void testFindByProductNameWithProductInformationAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductByProductName("TestUpdate", false, true);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The result has a relatedProductInformation", p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
				assertNull("The result has relatedLineItems", p.getRelatedLineItems());
			}
			else {
				assertTrue(p.getRelatedLineItems().isEmpty());
				assertNull(p.getRelatedLineItems());
			}
		}
	}
	
	@Test
	public void testFindByProductNameWithLineItemAssociation() throws SQLException, Exception {
		//Arange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductByProductName("TestUpdate", true, false);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The result has relatedLineItems", !p.getRelatedLineItems().isEmpty());
				assertNull("The result has not a relatedProductInformation", p.getRelatedProductInformation());
			}
			else {
				assertTrue(p.getRelatedLineItems().isEmpty());
				assertNull(p.getRelatedProductInformation());
			}
		}
	}
	
	@Test
	public void testFindByProductNameAllAssociations() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductByProductName("TestUpdate", true, true);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The result has relatedLineItems", !p.getRelatedLineItems().isEmpty());
				assertTrue("The result has a relatedProductInformation", p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
			}
			else {
				assertTrue(p.getRelatedLineItems().isEmpty());
				assertNull(p.getRelatedProductInformation());
			}
		}
	}
	
	@Test
	public void testFindByIdWitoutAssociation() throws SQLException, Exception{
		//Arrange
		Product result = null;
				
		//Act
		result = productDao.findProductById(productToUpdate.getId(), false, false);
		
		//Assert
		assertEquals("The ProductName is good", productToUpdate.getProductName(), result.getProductName());
		assertEquals("The selling price is good", productToUpdate.getSellingPrice(), result.getSellingPrice(), productToUpdate.getSellingPrice());
		
		assertNull("The product has not a relatedProductInformation", result.getRelatedProductInformation());
		assertNull("The product has not relatedLineItems", result.getRelatedLineItems());
	}
	
	@Test
	public void testFindByIdWithProductInformationAssociation() throws SQLException, Exception{
		//Arrange
		Product result = null;
		
		//Act
		result = productDao.findProductById(productToUpdate.getId(), false, true);
		
		//Assert
		assertEquals("The product name is good", productToUpdate.getProductName(), result.getProductName());
		assertEquals("The selling price is good", productToUpdate.getSellingPrice(), result.getSellingPrice(), productToUpdate.getSellingPrice());
		
		assertTrue("The product has a relatedProductInformation", result.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
		assertNull("The product has not relatedLineItems", result.getRelatedLineItems());            
	}
	
	@Test
	public void testFindByIdWithLineItemAssociation() throws SQLException, Exception{
		//Arrange
		Product result = null;
		
		//Act
		result = productDao.findProductById(productToUpdate.getId(), true, false);
		
		//Assert
		assertEquals("The product name is good", productToUpdate.getProductName(), result.getProductName());
		assertEquals("The selling price is good", productToUpdate.getSellingPrice(), result.getSellingPrice(), productToUpdate.getSellingPrice());
		
		assertTrue("The product has relatedLineItems", !result.getRelatedLineItems().isEmpty());
		assertNull("The product has not a relatedProductInformation", result.getRelatedProductInformation());
	}
	
	@Test
	public void testFindByIdWithAllAssociations() throws SQLException, Exception{
		//Arrange
		Product result = null;
		
		//Act
		result = productDao.findProductById(productToUpdate.getId(), true, true);
		
		//Assert
		assertEquals("The product name is good", productToUpdate.getProductName(), result.getProductName());
		assertEquals("The selling price is good", productToUpdate.getSellingPrice(), result.getSellingPrice(), productToUpdate.getSellingPrice());
		
		assertTrue("The product has relatedLineItems", !result.getRelatedLineItems().isEmpty());
		assertTrue("The product has a relatedProductInforation", result.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
	}
	
	@Test
	public void testFindByPartialNameWithoutAssociations() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductsByPartialName("Up", false, false);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			assertNull(p.getRelatedProductInformation());
			assertNull(p.getRelatedLineItems());
		}
	}
	
	@Test
	public void testFindByPartialNameWithProductInformationAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductsByPartialName("Up", false, true);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The product has a relatedProductInformation", p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
				assertNull("The product has not relatedLineItems", p.getRelatedLineItems());
			}
			else {
				assertNotNull(p.getRelatedProductInformation());
				assertNull(p.getRelatedLineItems());
			}
		}
	}
	
	@Test
	public void testFindByPartialNameWithLineItemAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductsByPartialName("Up", true, false);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The product has relatedLineItems", !p.getRelatedLineItems().isEmpty());
				assertNull("The product has not a relatedProductInformation", p.getRelatedProductInformation());
			}
			else {
				assertNotNull(p.getRelatedLineItems());
				assertNull(p.getRelatedProductInformation());
			}
		}
	}
	
	@Test
	public void testFindByPartialNameWithAllAssociation() throws SQLException, Exception {
		//Arrange
		List<Product> results = null;
		
		//Act
		results = productDao.findProductsByPartialName("Up", true, true);
		
		//Assert
		assertNotNull("Should return a list with something inside",results);
		for(Product p : results) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue("The product has relatedLineItems", !p.getRelatedLineItems().isEmpty());
				assertTrue("The product has a relatedProductInformation", p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
			}
			else {
				assertNotNull(p.getRelatedLineItems());
				assertNotNull(p.getRelatedProductInformation());
			}
		}
	}
	
	@Test
	public void testFindAllProductSubset() throws SQLException, Exception{
		//Arrange
		List<Product> productSubsetList = null;
		
		//Act
		productSubsetList = productSubsetDaoPart.findAllSubset();
		
		//Assert
		assertTrue("Should retrieve a none empty list", productSubsetList.size()>0);
		assertTrue("Should retrieve products without selling price", productSubsetList.get(0).getSellingPrice()==0);
	}
	
	@AfterClass
	public static void cleanUp() throws Exception {
		lineItemDao.deleteLineItem(lineItem);
		DaoFactory.getOrderDao().deleteOrder(order);
		productInformationDao.deleteProductInformation(productInformation);
		productDao.deleteProduct(productToCreate);
		productDao.deleteProduct(productToUpdate);
	}
}
