package test.ControllerTests;

import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.ControllerFactory;
import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import db_access.DaoFactory;
import model.LineItem;
import model.Product;
import model.Provider;
import model.PurchaseOrder;

public class TestCreatePurchaseOrderControllerImplementation {
	private static CreatePurchaseOrderController controller;
	
	@BeforeClass
	public static void setUp() {
		controller = ControllerFactory.getCreatePurchaseOrderController();
	}
	
	@Test
	public void testCreatePurchaseOrderTransaction() throws Exception {
		int numberOfPurchaseOrderBeforeTest = DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(false, false).size();
		
		for(Product product : DaoFactory.getProductDao().findAllProducts(false, false)) {
			controller.addProductToPurchaseOrder(product, 3);
		}
		Provider provider = DaoFactory.getProviderDao().findAllProviders(false).get(0);
		
		controller.createPurchaseOrder(provider);
		int numberOfPurchaseOrderAfterTest = DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(false, false).size();
		
		assertNotEquals("Should retrieve 1 more PurchaseOrder", numberOfPurchaseOrderBeforeTest, numberOfPurchaseOrderAfterTest);
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException, Exception {
		CreatePurchaseOrderControllerImplementation controllerImplementation = (CreatePurchaseOrderControllerImplementation) controller;
		PurchaseOrder purchaseOrder = controllerImplementation.getPurchaseOrder();
		for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(purchaseOrder, true)) {
			lineItem.setOrder(purchaseOrder);
			DaoFactory.getLineItemDao().deleteLineItem(lineItem);
		}
		
		DaoFactory.getPurchaseOrderDao().deletePurchaseOrder(purchaseOrder);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrder);
		
	}
}
