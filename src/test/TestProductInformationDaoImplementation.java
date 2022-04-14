package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.ProductInformationDao;
import model.ModelFactory;
import model.Product;
import model.Product.Unit;
import model.Product.WeightCategory;
import model.ProductInformation;

public class TestProductInformationDaoImplementation {
	private ProductInformationDao productInformationDao;
	private ProductInformation productInformationToUpdate;
	private ProductInformation productInformationToDelete;
	private Product product1, product2, product3;
	
	@BeforeClass
	private void setUp() throws SQLException {
		productInformationDao = DaoFactory.getProductInformationDao();
		product1 = ModelFactory.getProductModelWithoutId("Test1", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product2 = ModelFactory.getProductModelWithoutId("Test2", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		product3 = ModelFactory.getProductModelWithoutId("Test3", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		productInformationToUpdate = ModelFactory.getProductInformationModel(900, 14, product1.getId());
		productInformationToDelete = ModelFactory.getProductInformationModel(900, 14, product2.getId());
		productInformationDao.createProductInformation(productInformationToUpdate);
		productInformationDao.createProductInformation(productInformationToDelete);
	}
	
	@Test
	private void testCreateProductInformation() throws SQLException {
		ProductInformation productInformation = ModelFactory.getProductInformationModel(900, 14, product3.getId());
		productInformationDao.createProductInformation(productInformation);
		assertNotNull(productInformationDao.findProductInformationByProductId(product3.getId()));
	}
	
	@Test
	private void testUpdate() throws SQLException {
		productInformationToUpdate.setQuantity(50);
		productInformationDao.updateProductInformation(productInformationToUpdate);
		assertEquals(50, productInformationDao.findProductInformationByProduct(product1).getQuantity());
	}
	
	@Test
	private void testDelete() throws SQLException {
		productInformationDao.deleteProductInformation(productInformationToDelete);
		assertNull(productInformationDao.findProductInformationByProductId(product2.getId()));
	}
	
	@Test
	private void testFindAll() throws SQLException {
		List<ProductInformation> list =  productInformationDao.findAllProductInformationEntries();
		int count = 0;
		for(ProductInformation p : list) {
			count++;
		}
		assertEquals(2, count);
	}
	
	@Test
	private void testFindByProduct() throws SQLException {
		ProductInformation result = productInformationDao.findProductInformationByProduct(product2);
		assertEquals(result, ModelFactory.getProductInformationModel(900, 14, product2.getId()));
	}
	
	@Test
	private void testFindByProductName() throws SQLException {
		ProductInformation result = productInformationDao.findProductInformationByProductName(product2.getProductName());
		assertEquals(result, ModelFactory.getProductInformationModel(900, 14, product2.getId()));
	}
	
	@Test
	private void testFindByProductId() throws SQLException {
		ProductInformation result = productInformationDao.findProductInformationByProductId(product2.getId());
		assertEquals(result, ModelFactory.getProductInformationModel(900, 14, product2.getId()));
	}
	
	@AfterClass
	private void cleanUp() throws SQLException {
		productInformationDao.deleteProductInformation(ModelFactory.getProductInformationModel(900, 14, product3.getId()));
		productInformationDao.deleteProductInformation(productInformationToUpdate);
	}
}
