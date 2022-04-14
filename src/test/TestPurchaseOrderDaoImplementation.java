package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.PurchaseOrderDao;
import model.LineItem;
import model.ModelFactory;
import model.Provider;
import model.PurchaseOrder;

public class TestPurchaseOrderDaoImplementation {

	static PurchaseOrderDao purchaseOrderDao = DaoFactory.getPurchaseOrderDao();
	static int generatedPurchaseOrderId;
	static int id = 0;
	static PurchaseOrder purchaseOrderToUpdate;
	static PurchaseOrder purchaseOrderToDelete;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		Provider testProvider = DaoFactory.getProviderDao().findAllProviders().get(0);
		
		id = DaoFactory.getOrderDao().createEmptyOrder();
		
		purchaseOrderToUpdate = ModelFactory.getPurchaseOrderModel(id, testProvider);
		purchaseOrderDao.createPurchaseOrder(purchaseOrderToUpdate);
		
		id = DaoFactory.getOrderDao().createEmptyOrder();
		purchaseOrderToDelete = ModelFactory.getPurchaseOrderModel(id, testProvider);
		purchaseOrderDao.createPurchaseOrder(purchaseOrderToDelete);
	}
	
	@Test
	public void testFindAllPurchaseOrdersWithoutAssociations() throws SQLException {
		ArrayList<PurchaseOrder> retrievedPurchaseOrders = (ArrayList<PurchaseOrder>) DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(false, false);
		assertTrue("Should return a list with more than 0 objects inside", retrievedPurchaseOrders.size()>0);
		
		for(PurchaseOrder purchaseOrder : retrievedPurchaseOrders ) {
			assertTrue("Shouldn't have a provider set", purchaseOrder.getProvider() == null);
		}
	}
	
	@Test
	public void testFindAllPurchaseOrdersWithProviderAssociations() throws SQLException {
		ArrayList<PurchaseOrder> retrievedPurchaseOrders = (ArrayList<PurchaseOrder>) DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(true, false);
		assertTrue("Should return a list with more than 0 objects inside", retrievedPurchaseOrders.size()>0);
		
		for(PurchaseOrder purchaseOrder : retrievedPurchaseOrders ) {
			assertNotNull("Should have a provider set", purchaseOrder.getProvider());
		}
		
	}
	
	@Test
	public void testFindAllPurchaseOrdersWithLineItemAssociations() throws SQLException {
		ArrayList<PurchaseOrder> retrievedPurchaseOrders = (ArrayList<PurchaseOrder>) DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(false, true);
		assertTrue("Should return a list with more than 0 objects inside", retrievedPurchaseOrders.size()>0);
		
		for(PurchaseOrder purchaseOrder : retrievedPurchaseOrders ) {
			ArrayList<LineItem> lineItemList = purchaseOrder.getLineItems();
			for(LineItem lineItem : lineItemList) {
				assertNotNull("Shouldn't be null", lineItem);
			}
		}
		
	}
	
	@Test
	public void testFindAllPurchaseOrdersWithAllAssociations() throws SQLException {
		ArrayList<PurchaseOrder> retrievedPurchaseOrders = (ArrayList<PurchaseOrder>) DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(true, true);
		assertTrue("Should return a list with more than 0 objects inside", retrievedPurchaseOrders.size()>0);
		
		for(PurchaseOrder purchaseOrder : retrievedPurchaseOrders ) {
			ArrayList<LineItem> lineItemList = purchaseOrder.getLineItems();
			for(LineItem lineItem : lineItemList) {
				assertNotNull("LineItem shouldn't be null", lineItem);
			}
		}
		
		for(PurchaseOrder purchaseOrder : retrievedPurchaseOrders ) {
			assertNotNull("Should have a provider set", purchaseOrder.getProvider());
		}
		
	}
	
	@Test
	public void testFindPurchaseOrderByIdWithoutAssociations() throws SQLException {
		PurchaseOrder retrievedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToUpdate.getId(), false, false);
		assertNotNull("Should return an object", retrievedPurchaseOrder);
	}
	
	@Test
	public void testFindPurchaseOrderByIdWithProviderAssociations() throws SQLException {
		PurchaseOrder retrievedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToUpdate.getId(), true, false);
		assertNotNull("Should return an object", retrievedPurchaseOrder);
		assertNotNull("Should have a provider set", retrievedPurchaseOrder.getProvider());
	}
	
	@Test
	public void testFindPurchaseOrderByIdWithLineItemAssociations() throws SQLException {
		PurchaseOrder retrievedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToUpdate.getId(), false, true);
		assertNotNull("Should return an object", retrievedPurchaseOrder);
		for(LineItem lineItem : retrievedPurchaseOrder.getLineItems() ) {
			assertNotNull("LineItem shouldn't be null", lineItem);
		}
	}
	
	@Test
	public void testFindPurchaseOrderByIdWithAllAssociations() throws SQLException {
		PurchaseOrder retrievedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToUpdate.getId(), true, true);
		assertNotNull("Should return an object", retrievedPurchaseOrder);
		assertNotNull("Should have a provider set", retrievedPurchaseOrder.getProvider());
		for(LineItem lineItem : retrievedPurchaseOrder.getLineItems() ) {
			assertNotNull("LineItem shouldn't be null", lineItem);
		}
	}
	
	@Test
	public void testCreatePurchaseOrder() throws SQLException {
		generatedPurchaseOrderId = DaoFactory.getOrderDao().createEmptyOrder();
		
		Provider testProvider = DaoFactory.getProviderDao().findAllProviders().get(0);
		PurchaseOrder testSaleOrder = ModelFactory.getPurchaseOrderModel(generatedPurchaseOrderId, testProvider);
		
		DaoFactory.getPurchaseOrderDao().createPurchaseOrder(testSaleOrder);
		PurchaseOrder retrievedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(generatedPurchaseOrderId, false, false);
		
		assertNotNull("Should not return a null object", retrievedPurchaseOrder);
	}

	@Test
	public void testUpdateOrder() throws SQLException {
		purchaseOrderToUpdate.setOrderPrice(200);
		DaoFactory.getPurchaseOrderDao().updatePurchaseOrder(purchaseOrderToUpdate);
		DaoFactory.getOrderDao().updateOrder(purchaseOrderToUpdate);
		PurchaseOrder retrievedUpdatedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToUpdate.getId(), false, false);
		
		assertTrue("Should return the same price" ,purchaseOrderToUpdate.getOrderPrice() == retrievedUpdatedPurchaseOrder.getOrderPrice());
	}
	
	@Test
	public void testDeleteOrder() throws SQLException {
		
		purchaseOrderDao.deletePurchaseOrder(purchaseOrderToDelete);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrderToDelete);
		PurchaseOrder retrievedDeletedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToDelete.getId(), false, false);
		assertNull("Should return a null object", retrievedDeletedPurchaseOrder );
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException {
		
		PurchaseOrder createdOrder = new PurchaseOrder(generatedPurchaseOrderId, null);
		
		purchaseOrderDao.deletePurchaseOrder(purchaseOrderToUpdate);
		purchaseOrderDao.deletePurchaseOrder(createdOrder);
		
		DaoFactory.getOrderDao().deleteOrder(createdOrder);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrderToDelete);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrderToUpdate);
		
//		purchaseOrderDao.deletePurchaseOrder(createdOrder);
	}
}
