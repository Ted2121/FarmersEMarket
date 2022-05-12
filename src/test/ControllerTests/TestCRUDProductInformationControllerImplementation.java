package test.ControllerTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDProductInformationController;
import db_access.DaoFactory;
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.ProductInformationDao;
import model.*;

public class TestCRUDProductInformationControllerImplementation {
	private static Product productToCreate, productToUpdate, productToDelete;
	private static ProductInformation productInformationToCreate, productInformationToUpdate, productInformationToDelete;
	private static ProductDao productDao;
	private static ProductInformationDao productInformationDao;
	private static CRUDProductInformationController crudProductInformationController;
	
	@BeforeClass
	public static void setUp() {
		crudProductInformationController = ControllerFactory.getCRUDProductInformationController();
		productDao = DaoFactory.getProductDao();
		productInformationDao = DaoFactory.getProductInformationDao();
		crudProductInformationController.createProductInformationAndProduct("ProductToUpdate", 15d, 20d, "KG", 5, 455, 10);
		crudProductInformationController.createProductInformationAndProduct("ProductToDelete", 15d, 20d, "KG", 5, 455, 10);
		try {
			List<Product> list = productDao.findProductByProductName("ProductToUpdate", false, true);
			productToUpdate = list.get(0);
			productInformationToUpdate = productInformationDao.findProductInformationByProductId(productToUpdate.getId(), false);
			list = productDao.findProductByProductName("ProductToDelete", false, true);
			productToDelete = list.get(0);
			productInformationToDelete = productInformationDao.findProductInformationByProductId(productToDelete.getId(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchAllProductAndProductInformation() {
		//Arrange
		
		//Act
		
		//Assert
		assertTrue("The method retrieve a list with objects", !crudProductInformationController.searchAllProductAndProductInformation().isEmpty());
	}
	
	@Test
	public void testCreateProductInformationAndProduct() {
		//Arrange
		List<Product> list = null;
		
		//Act
		crudProductInformationController.createProductInformationAndProduct("ProductToCreate", 15d, 20d, "KG", 5, 455, 10);
		try {
			list = productDao.findProductByProductName("ProductToCreate", false, true);
			productToCreate = list.get(0);
			productInformationToCreate = productInformationDao.findProductInformationByProductId(productToCreate.getId(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		assertNotNull("The product is in the database", productToCreate);
		assertNotNull("The productInformation is in the database", productToCreate.getRelatedProductInformation());
	}
	
	@Test
	public void testUpdateProductInformationAndProduct() throws SQLException, Exception {
		//Arrange
		
		//Act
		crudProductInformationController.updateProductInformationAndProduct(productToUpdate, 455, 20);
		productInformationToUpdate.setQuantity(20);
		
		//Assert
		assertEquals("The product and productInformation are updated", productInformationDao.findProductInformationByProduct(productToUpdate, false).getQuantity(), 20);
	}
	
	@Test
	public void testDeleteProductInformationAndProduct() throws SQLException, Exception {
		//Arrange
		
		//Act
		crudProductInformationController.deleteProductInformationAndProduct(productToDelete);
		
		//Assert
		assertTrue("The productInformation is deleted", productInformationDao.findProductInformationByProductName("ProductToDelete", false).isEmpty());
		assertTrue("The product is deleted", productDao.findProductByProductName("ProductToDelete", false, false).isEmpty());
	}
	
	@Test
	public void testRetrieveTableData() {
		//Arrange
		String [][] datas = null;
		
		//Act
		datas = crudProductInformationController.retrieveTableData();
		
		//Assert
		assertNotNull("The method retrieve datas well", datas);
	}
	
	@Test
	public void testSearchById() {
		//Arrange
		Product product = null;
		
		//Act
		product = crudProductInformationController.searchAProductById(productToUpdate.getId());
		
		//Assert
		assertTrue("The product name is the same", product.getProductName().equals(productToUpdate.getProductName()));
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException {
		productInformationDao.deleteProductInformation(productInformationToCreate);
		productDao.deleteProduct(productToCreate);
		productInformationDao.deleteProductInformation(productInformationToUpdate);
		productDao.deleteProduct(productToUpdate);
	}
}
