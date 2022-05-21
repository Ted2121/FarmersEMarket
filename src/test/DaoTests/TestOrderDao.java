package test.DaoTests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.*;

import db_access.DaoFactory;
import db_access.DaoInterfaces.OrderDao;
import model.*;
import test.testingClass.TestingSaleOrder;

public class TestOrderDao {
	
	static OrderDao orderDao = DaoFactory.getOrderDao();
	static int generatedOrderId;
	static int emptyOrderGeneratedId;
	static PurchaseOrder orderToUpdate;
	static PurchaseOrder orderToDelete;
	
	@BeforeClass
	public static void setUp() throws Exception {
		Provider testProvider = DaoFactory.getProviderDao().findAllProviders(false).get(0);
		orderToUpdate = new PurchaseOrder(testProvider);
		orderToUpdate.setOrderPrice(10);
		
		orderDao.createOrder(orderToUpdate);
		DaoFactory.getPurchaseOrderDao().createPurchaseOrder(orderToUpdate);
		
		orderToDelete = new PurchaseOrder(testProvider);
		orderDao.createOrder(orderToDelete);
		DaoFactory.getPurchaseOrderDao().createPurchaseOrder(orderToDelete);
	}
	
	@Test
	public void testCreateEmptyOrder() throws SQLException {
		
		emptyOrderGeneratedId = orderDao.createEmptyOrder();
		
		assertTrue("Should return a generated id > 0", emptyOrderGeneratedId>0);
	}
	
	@Test
	public void testCreateNotEmptyOrder() throws SQLException {
		orderDao = DaoFactory.getOrderDao();
		Customer testCustomer = new Customer("test", "test", "Aalborg","Denmark", "new adress", 5000);
		SaleOrder testSaleOrder = new SaleOrder(testCustomer);
		
		orderDao.createOrder(testSaleOrder);
		generatedOrderId = testSaleOrder.getId();
		
		assertTrue("Should return a generated id > 0", generatedOrderId>0);
	}
	
	@Test
	public void testUpdateOrder() throws Exception {
		
		double lastPrice = orderToUpdate.getOrderPrice();
		orderToUpdate.setOrderPrice(28);
		
		orderDao.updateOrder(orderToUpdate);
		PurchaseOrder retrievedOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(orderToUpdate.getId(), false, false);
		
		assertTrue("Should return a generated id > 0", retrievedOrder.getOrderPrice() != lastPrice);
	}
	
	@Test
	public void testDeleteOrder() throws Exception {
		
		orderDao.deleteOrder(orderToDelete);
		PurchaseOrder retrievedOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(orderToDelete.getId(), false, false);
		
		assertNull("Shouldn't retrieve objects", retrievedOrder );
		
	}
	
	@AfterClass
	public static void cleanUp() throws Exception {
		
		TestingSaleOrder createdOrder = new TestingSaleOrder(generatedOrderId);
		TestingSaleOrder createdEmptyOrder = new TestingSaleOrder(emptyOrderGeneratedId);
		
		orderDao.deleteOrder(orderToUpdate);
		orderDao.deleteOrder(createdEmptyOrder);
		orderDao.deleteOrder(createdOrder);
	}
}
