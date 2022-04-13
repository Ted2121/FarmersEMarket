package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

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
	
	@Before
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
		assertEquals(50, productInformationToUpdate.getQuantity());
	}
	
	@Test
	private void testDelete() throws SQLException {
		productInformationDao.deleteProductInformation(productInformationToDelete);
		assertNull(productInformationDao.findProductInformationByProductId(product2.getId()));
	}
	
	@Test
	private void testFindId() {
		
	}
}
