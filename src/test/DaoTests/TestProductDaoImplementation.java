package test.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.LineItemDao;
import db_access.DaoInterfaces.OrderDao;
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.ProductInformationDao;
import model.LineItem;
import model.ModelFactory;
import model.Order;
import model.Product;
import model.ProductInformation;
import test.testingClass.TestingSaleOrder;
import model.Product.Unit;
import model.Product.WeightCategory;

public class TestProductDaoImplementation {
	private static ProductDao productDao;
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
		lineItemDao = DaoFactory.getLineItemDao();
		productInformationDao = DaoFactory.getProductInformationDao();
		productToCreate = ModelFactory.getProductModelWithoutId("TestCreate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productToUpdate = ModelFactory.getProductModelWithoutId("TestUpdate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productToDelete = ModelFactory.getProductModelWithoutId("TestDelete", 15d, 20d, WeightCategory.FIVE, Unit.KG);
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
		productDao.createProduct(productToCreate);
		assertNotNull(productDao.findProductById(productToCreate.getId(), false, false));
	}
	
	@Test
	public void testUpdate() throws SQLException, Exception {
		productToUpdate.setSellingPrice(50d);
		productDao.updateProduct(productToUpdate);
		assertTrue("Should return 50d", productDao.findProductById(productToUpdate.getId(),false, false).getSellingPrice() == 50d);
	}
	
	@Test
	public void testDelete() throws SQLException, Exception {
		productDao.deleteProduct(productToDelete);
		assertNull(productDao.findProductById(productToDelete.getId(), false, false));
	}
	
	@Test
	public void testFindAllWithoutAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(false, false);
		int count = 0;
		for(Product p : list) {
			count++;
			assertTrue(p.getRelatedProductInformation() == null);
			assertTrue(p.getRelatedLineItems() == null);
		}
		assertTrue(count>0);
	}
	@Test
	public void testFindAllWithProductInformationAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(false, true);
		for(Product p : list) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue(p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
				assertTrue(p.getRelatedLineItems() == null);
			}
			else {
				assertTrue(p.getRelatedProductInformation() == null);
				assertTrue(p.getRelatedLineItems() == null);
			}
		}
		
	}
	
	@Test
	public void testFindAllWithLineItemAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(true, false);
		for(Product p : list) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue(!p.getRelatedLineItems().isEmpty());
				assertTrue(p.getRelatedProductInformation() == null);
			}
			else {
				assertTrue(p.getRelatedLineItems().isEmpty());
				assertTrue(p.getRelatedProductInformation() == null);
			}
		}
		
	}
	
	@Test
	public void testFindAllWithLineItemAndProductInformationAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(true, true);
		for(Product p : list) {
			if(p.getId() == productToUpdate.getId()) {
				assertTrue(!p.getRelatedLineItems().isEmpty());
				assertTrue(p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
			}
			else {
				assertTrue(p.getRelatedLineItems().isEmpty());
				assertTrue(p.getRelatedProductInformation() == null);
			}
		}
		
	}
	
	@Test
	public void testFindByProductName() throws SQLException, Exception {
		List<Product> results = productDao.findProductByProductName("TestUpdate", false, false);
		assertNotNull("Should return a list with something inside",results);
	}
	
	@Test
	public void testFindById() throws SQLException, Exception{
		Product result = productDao.findProductById(productToUpdate.getId(), false, false);
		assertEquals(productToUpdate.getProductName(), result.getProductName());
		assertEquals(productToUpdate.getSellingPrice(), result.getSellingPrice(), productToUpdate.getSellingPrice());
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
