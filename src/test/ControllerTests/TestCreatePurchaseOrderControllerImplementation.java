package test.ControllerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
	private static int quantityOfEachLineItem;
	
	@BeforeClass
	public static void setUp() {
		controller = ControllerFactory.getCreatePurchaseOrderController();
		quantityOfEachLineItem = 3;
	}
	
	@Test
	public void testCreatePurchaseOrderTransaction() throws Exception {
		int numberOfPurchaseOrderBeforeTest = DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(false, false).size();
		
		for(Product product : DaoFactory.getProductDao().findAllProducts(false, false)) {
			controller.addProductToPurchaseOrder(product, quantityOfEachLineItem);
		}
		Provider provider = DaoFactory.getProviderDao().findAllProviders(false).get(0);
		
		controller.createPurchaseOrder(provider);
		int numberOfPurchaseOrderAfterTest = DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(false, false).size();
		
		assertEquals("Should retrieve 1 more PurchaseOrder", numberOfPurchaseOrderBeforeTest+1, numberOfPurchaseOrderAfterTest);
	}
	
	@AfterClass
	public static void cleanUp() throws Exception {
		CreatePurchaseOrderControllerImplementation controllerImplementation = (CreatePurchaseOrderControllerImplementation) controller;
		PurchaseOrder purchaseOrder = controllerImplementation.getPurchaseOrder();
		for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(purchaseOrder, true)) {
			lineItem.setOrder(purchaseOrder);
			DaoFactory.getLineItemDao().deleteLineItem(lineItem);
			DaoFactory.getProductInformationDao().removeQuantityToProduct(lineItem.getProduct(), quantityOfEachLineItem);
		}
		
		DaoFactory.getPurchaseOrderDao().deletePurchaseOrder(purchaseOrder);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrder);
		
	}
}
