package test.DaoTests;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.OrderDao;
import model.Customer;
import model.SaleOrder;
import test.testingClass.TestingSaleOrder;

public class TestOrderDao {
	
	static OrderDao orderDao = DaoFactory.getOrderDao();
	static int generatedOrderId;
	static int emptyOrderGeneratedId;
	static SaleOrder orderToUpdate;
	static SaleOrder orderToDelete;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		Customer testCustomer = new Customer("test", "test", "Aalborg","Denmark", "new adress", 5000);
		orderToUpdate = new SaleOrder(0, testCustomer);
		orderToUpdate.setOrderPrice(10);
		orderDao.createOrder(orderToUpdate);
		
		orderToDelete = new SaleOrder(testCustomer);
		orderDao.createOrder(orderToDelete);
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
		
		generatedOrderId = orderDao.createOrder(testSaleOrder);
		assertTrue("Should return a generated id > 0", generatedOrderId>0);
	}
	
	@Test
	public void testUpdateOrder() throws SQLException {
		
		double lastPrice = orderToUpdate.getOrderPrice();
		orderToUpdate.setOrderPrice(28);
		
		orderDao.updateOrder(orderToUpdate);
		//TODO Test the updateOrder methods once SaleOrder or PurchaseOrder is done
		//Hard verified using MSSQL
		
//		assertTrue("Should return a generated id > 0", generatedId>0);
	}
	
	@Test
	public void testDeleteOrder() throws SQLException {
		
		orderDao.deleteOrder(orderToDelete);
		//TODO Test the updateOrder methods once SaleOrder or PurchaseOrder is done
		//Hard verified using MSSQL
		
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException {
		
		TestingSaleOrder createdOrder = new TestingSaleOrder(generatedOrderId);
		TestingSaleOrder createdEmptyOrder = new TestingSaleOrder(emptyOrderGeneratedId);
		
		orderDao.deleteOrder(orderToUpdate);
		orderDao.deleteOrder(createdEmptyOrder);
		orderDao.deleteOrder(createdOrder);
	}
}
