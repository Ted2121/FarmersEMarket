package test.DaoTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.*;

import db_access.DaoFactory;
import db_access.DaoInterfaces.LineItemDao;
import model.*;
import model.Product.*;
import test.testingClass.TestingSaleOrder;

public class TestLineItemDaoImplementation {
	static LineItemDao lineItemDao;
	static LineItem generatedLineItem;
	static LineItem objectToDelete;
	static LineItem objectToUpdate;
	
	@BeforeClass
	public static void creatingTheTupleToDelete () throws Exception {
		lineItemDao = DaoFactory.getLineItemDao();
		objectToDelete = ModelFactory.getLineItemModel(10);
		objectToUpdate = ModelFactory.getLineItemModel(5);
//		lineItemDao.createLineItem(objectToDelete);
		objectToUpdate.setOrder(DaoFactory.getSaleOrderDao().findSaleOrderById(2, false, false));
		objectToUpdate.setProduct(DaoFactory.getProductDao().findProductById(3, false, false));
		
		objectToDelete.setOrder(DaoFactory.getSaleOrderDao().findSaleOrderById(3, false, false));
		objectToDelete.setProduct(DaoFactory.getProductDao().findProductById(1, false, false));
		
		lineItemDao.createLineItem(objectToUpdate);
		lineItemDao.createLineItem(objectToDelete);
	}
	
	@Test
	public void testFindLineItemsByOrderWithoutProductAssociation() throws SQLException, Exception {
		//Arrange
		TestingSaleOrder testSaleOrder = new TestingSaleOrder(1);
		
		//Act
		List<LineItem> retrievedLineItems = lineItemDao.findLineItemsByOrder(testSaleOrder, false);
		
		//Assert
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNull("Should not have related product", lineItem.getProduct());
		}
	}
	
	@Test
	public void testFindLineItemsByOrderWithProductAssociation() throws SQLException, Exception {
		//Arrange
		TestingSaleOrder testSaleOrder = new TestingSaleOrder(1);
		
		//Act
		List<LineItem> retrievedLineItems = lineItemDao.findLineItemsByOrder(testSaleOrder, true);
		
		//Assert
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNotNull("Should have related product set", lineItem.getProduct());
		}
	}
	
	@Test
	public void testFindLineItemsByProductWithoutOrderAssociation() throws SQLException, Exception {
		Product testProduct = ModelFactory.getProductModel(1,"TestCreate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		
		List<LineItem> retrievedLineItems = lineItemDao.findLineItemsByProduct(testProduct, false);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNull("Should not have related order", lineItem.getOrder());
		}
	}
	
	@Test
	public void testFindLineItemsByProductWithOrderAssociation() throws SQLException, Exception {
		Product testProduct = ModelFactory.getProductModel(1,"TestCreate", 15d, 20d, WeightCategory.FIVE, Unit.KG);
		
		List<LineItem> retrievedLineItem = lineItemDao.findLineItemsByProduct(testProduct, true);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItem);
		for(LineItem lineItem : retrievedLineItem) {
			assertNotNull("Should have related order set", lineItem.getOrder());
		}
	}
	
	@Test
	public void testFindLineItemByOrderAndProductIdWithoutAssociations() throws SQLException, Exception {
		
		LineItem retrievedLineItem = lineItemDao.findLineItemByOrderAndProductId(1, 1, false, false);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItem);
		assertTrue("Should retrieve a quantity of 4", retrievedLineItem.getQuantity()==4);
		assertNull("Should not have related product", retrievedLineItem.getProduct());
		assertNull("Should not have related order", retrievedLineItem.getOrder());
	}
	
	@Test
	public void testFindLineItemByOrderAndProductIdWithOrderAssociations() throws SQLException, Exception {
		
		LineItem retrievedLineItem = lineItemDao.findLineItemByOrderAndProductId(1, 1, true, false);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItem);
		assertTrue("Should retrieve a quantity of 4", retrievedLineItem.getQuantity()==4);
		assertNull("Should not have related product", retrievedLineItem.getProduct());
		assertNotNull("Should have related order set", retrievedLineItem.getOrder());
	}
	
	@Test
	public void testFindLineItemByOrderAndProductIdWithProductAssociations() throws SQLException, Exception {
		
		LineItem retrievedLineItem = lineItemDao.findLineItemByOrderAndProductId(1, 1, false, true);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItem);
		assertTrue("Should retrieve a quantity of 4", retrievedLineItem.getQuantity()==4);
		assertNotNull("Should  have related product", retrievedLineItem.getProduct());
		assertNull("Should have related order set", retrievedLineItem.getOrder());
	}
	
	@Test
	public void testFindLineItemByOrderAndProductIdWithAllAssociations() throws SQLException, Exception {
		
		LineItem retrievedLineItem = lineItemDao.findLineItemByOrderAndProductId(1, 1, true, true);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItem);
		assertTrue("Should retrieve a quantity of 4", retrievedLineItem.getQuantity()==4);
		assertNotNull("Should have related product set", retrievedLineItem.getProduct());
		assertNotNull("Should have related order set", retrievedLineItem.getOrder());
	}
	
	@Test
	public void testFindAllLineItemsWithoutAssociations() throws SQLException, Exception {
		
		List<LineItem> retrievedLineItems = lineItemDao.findAllLineItems(false, false);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNull("Should not have related product", lineItem.getProduct());
			assertNull("Should not have related order", lineItem.getOrder());
		}
		
	}
	
	@Test
	public void testFindAllLineItemsWithOrderAssociations() throws SQLException, Exception {
		
		List<LineItem> retrievedLineItems = lineItemDao.findAllLineItems(true, false);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNull("Should not have related product", lineItem.getProduct());
			assertNotNull("Should have related order set", lineItem.getOrder());
		}
		
	}
	
	@Test
	public void testFindAllLineItemsWithProductAssociations() throws SQLException, Exception {
		
		List<LineItem> retrievedLineItems = lineItemDao.findAllLineItems(false, true);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNotNull("Should have related product set", lineItem.getProduct());
			assertNull("Should not have related order", lineItem.getOrder());
		}
		
	}
	
	@Test
	public void testFindAllLineItemsWithAllAssociations() throws SQLException, Exception {
		
		List<LineItem> retrievedLineItems = lineItemDao.findAllLineItems(true, true);
		
		assertNotNull("Should retrieve LineItems", retrievedLineItems);
		for(LineItem lineItem : retrievedLineItems) {
			assertNotNull("Should have related product set", lineItem.getProduct());
			assertNotNull("Should have related order set", lineItem.getOrder());
		}
		
	}
	
	@Test
	public void testCreateLinetem() throws SQLException, Exception{
		Product product = DaoFactory.getProductDao().findProductById(2, false, false);
		Order order = DaoFactory.getSaleOrderDao().findSaleOrderById(2, false, false);
		generatedLineItem = new LineItem(2, product, order);
		
		lineItemDao.createLineItem(generatedLineItem);
		LineItem retrievedLineItem = lineItemDao.findLineItemByOrderAndProductId(order.getId(), product.getId(), false, false);
		
		assertNotNull("Should retrieve the inserted item", retrievedLineItem);
	}
	
	@Test
	public void testUpdateLinetem() throws SQLException, Exception{
		int oldQuantity = objectToUpdate.getQuantity();
		objectToUpdate.setQuantity(12);
		
		lineItemDao.updateLineItem(objectToUpdate);
		objectToUpdate = lineItemDao.findLineItemByOrderAndProductId(objectToUpdate.getOrder().getId(), objectToUpdate.getProduct().getId(), true, true);
		
		assertTrue("Shouldn't be the same quantity", oldQuantity != objectToUpdate.getQuantity());
	}
	
	@Test
	public void testDeleteLinetem() throws SQLException, Exception{
		
		lineItemDao.deleteLineItem(objectToDelete);
		objectToDelete = lineItemDao.findLineItemByOrderAndProductId(objectToDelete.getOrder().getId(), objectToDelete.getProduct().getId(), false, false);
		
		assertNull("Shouldn't retrieve objects", objectToDelete );
	}
	
	
	@AfterClass
	public static void cleanUp() throws Exception {
		while(lineItemDao.findLineItemByOrderAndProductId(generatedLineItem.getOrder().getId(), generatedLineItem.getProduct().getId(), true, true) != null) {
			lineItemDao.deleteLineItem(generatedLineItem);
			
		}
		lineItemDao.deleteLineItem(objectToUpdate);
	}
}
