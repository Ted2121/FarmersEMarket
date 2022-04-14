package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.ProductInformationDao;
import model.ModelFactory;
import model.Product;
import model.Product.Unit;
import model.Product.WeightCategory;
import model.ProductInformation;

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
		product1 = ModelFactory.getProductModelWithoutId("Test1", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product2 = ModelFactory.getProductModelWithoutId("Test2", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product3 = ModelFactory.getProductModelWithoutId("Test3", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product1.setId(productDao.findProductByProductName("Test1").getId());
		product2.setId(productDao.findProductByProductName("Test2").getId());
		product3.setId(productDao.findProductByProductName("Test3").getId());
		productDao.createProduct(product1);
		productDao.createProduct(product2);
		productDao.createProduct(product3);
		productInformationToUpdate = ModelFactory.getProductInformationModel(900, 14, product1.getId());
		productInformationToDelete = ModelFactory.getProductInformationModel(900, 14, product2.getId());
		productInformationToCreate = ModelFactory.getProductInformationModel(900, 14, product3.getId());
		productInformationDao.createProductInformation(productInformationToUpdate);
		productInformationDao.createProductInformation(productInformationToDelete);
	}
	
	@Test
	public void testCreateProductInformation() throws SQLException {
		productInformationDao.createProductInformation(productInformationToCreate);
		assertNotNull(productInformationDao.findProductInformationByProductId(product3.getId()));
	}
	
	@Test
	public void testUpdate() throws SQLException {
		productInformationToUpdate.setQuantity(50);
		productInformationDao.updateProductInformation(productInformationToUpdate);
		assertEquals(50, productInformationDao.findProductInformationByProduct(product1).getQuantity());
	}
	
	@Test
	public void testDelete() throws SQLException {
		productInformationDao.deleteProductInformation(productInformationToDelete);
		assertNull(productInformationDao.findProductInformationByProductId(product2.getId()));
	}
	
	@Test
	public void testFindAll() throws SQLException {
		List<ProductInformation> list =  productInformationDao.findAllProductInformationEntries();
		int count = 0;
		for(ProductInformation p : list) {
			count++;
		}
		assertTrue(count>0);
	}
	
	@Test
	public void testFindByProduct() throws SQLException {
		ProductInformation result = productInformationDao.findProductInformationByProduct(product1);
		assertEquals(result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals(result.getLocationCode(), productInformationToUpdate.getLocationCode());
	}
	
	@Test
	public void testFindByProductName() throws SQLException {
		ProductInformation result = productInformationDao.findProductInformationByProductName(product1.getProductName());
		assertEquals(result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals(result.getLocationCode(), productInformationToUpdate.getLocationCode());
	}
	
	@Test
	public void testFindByProductId() throws SQLException {
		ProductInformation result = productInformationDao.findProductInformationByProductId(product1.getId());
		assertEquals(result.getQuantity(), productInformationToUpdate.getQuantity());
		assertEquals(result.getLocationCode(), productInformationToUpdate.getLocationCode());
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
