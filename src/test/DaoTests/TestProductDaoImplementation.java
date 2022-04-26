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
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.ProductInformationDao;
import model.LineItem;
import model.ModelFactory;
import model.Product;
import model.ProductInformation;
import model.Product.Unit;
import model.Product.WeightCategory;

public class TestProductDaoImplementation {
	private static ProductDao productDao;
	private static Product productToUpdate;
	private static Product productToDelete;
	private static Product productToCreate;
	private static LineItem lineItem;
	private static ProductInformation productInformation;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		productDao = DaoFactory.getProductDao();
		productToCreate = ModelFactory.getProductModelWithoutId("TestCreate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productToUpdate = ModelFactory.getProductModelWithoutId("TestUpdate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productToDelete = ModelFactory.getProductModelWithoutId("TestDelete", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productDao.createProduct(productToUpdate);
		productDao.createProduct(productToDelete);
		lineItem = ModelFactory.getLineItemModel(5, productToUpdate, null);
		productInformation = ModelFactory.getProductInformationModel(156, 14, productToCreate.getId());
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
		}
		assertTrue(count>0);
	}
	@Test
	public void testFindAllWithProductInformationAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(false, true);
		int count = 0;
		for(Product p : list) {
			if(p.equals(productToUpdate)) {
				assertTrue(p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
			}
			else {
				assertTrue(p.getRelatedProductInformation() == null);
			}
		}
		
	}
	
	@Test
	public void testFindAllWithLineItemAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(true, false);
		int count = 0;
		for(Product p : list) {
			if(p.equals(productToUpdate)) {
				assertTrue(p.getRelatedLineItems().equals(productToUpdate.getRelatedLineItems()));
			}
			else {
				assertTrue(p.getRelatedProductInformation() == null);
			}
		}
		
	}
	
	public void testFindAllWithLineItemAndProductInformationAssociation() throws SQLException, Exception {
		List<Product> list =  productDao.findAllProducts(true, true);
		int count = 0;
		for(Product p : list) {
			if(p.equals(productToUpdate)) {
				assertTrue(p.getRelatedLineItems().equals(productToUpdate.getRelatedLineItems()));
				assertTrue(p.getRelatedProductInformation().getQuantity() == productInformation.getQuantity());
			}
			else {
				assertTrue(p.getRelatedProductInformation() == null);
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
	public static void cleanUp() throws SQLException {
		productDao.deleteProduct(productToCreate);
		productDao.deleteProduct(productToUpdate);
	}
}
