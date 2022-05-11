package test.ControllerTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.ControllerFactory;
import controller.ControllerImplementation.CRUDPurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CRUDPurchaseOrderController;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import controller.ControllerInterfaces.SearchProductInterface;
import controller.ControllerInterfaces.SearchProviderInterface;
import db_access.DaoFactory;
import model.LineItem;
import model.ModelFactory;
import model.Product;
import model.Product.WeightCategory;
import model.Provider;
import model.PurchaseOrder;

public class TestCRUDPurchaseOrderImplementation {

	private static CRUDPurchaseOrderController controller;
	private static SearchProductInterface productSearchControllerPart;
	private static SearchProviderInterface providerSearchControllerPart;
	
	private static PurchaseOrder purchaseOrder;
	private static PurchaseOrder purchaseOrderToDelete;
	private static Provider provider;
	private static Provider provider2;
	private static LineItem lineItem;
	private static Product product;
	
	@BeforeClass
	public static void setUp() throws SQLException {
		controller = ControllerFactory.getCRUDPurchaseOrderController();
		productSearchControllerPart = (SearchProductInterface) controller;
		providerSearchControllerPart = (SearchProviderInterface) controller;
		
		provider = ModelFactory.getProviderModel("test", "test", "test", "test");
		DaoFactory.getProviderDao().createProvider(provider);
		provider2 = ModelFactory.getProviderModel("test2", "test2", "test2", "test2");
		DaoFactory.getProviderDao().createProvider(provider2);
		
		purchaseOrder = ModelFactory.getPurchaseOrderModel(provider);
		DaoFactory.getOrderDao().createOrder(purchaseOrder);
		DaoFactory.getPurchaseOrderDao().createPurchaseOrder(purchaseOrder);
		
		purchaseOrderToDelete = ModelFactory.getPurchaseOrderModel(provider);
		DaoFactory.getOrderDao().createOrder(purchaseOrderToDelete);
		DaoFactory.getPurchaseOrderDao().createPurchaseOrder(purchaseOrderToDelete);
		
		product = ModelFactory.getProductModel("test", 0, 0, WeightCategory.FIVE, Unit.KG);
		DaoFactory.getProductDao().createProduct(product);
		
		lineItem = ModelFactory.getLineItemModel(3, product, purchaseOrder);
		DaoFactory.getLineItemDao().createLineItem(lineItem);
	}
	
	@Test
	public void testUpdatePurchaseOrder() throws SQLException, Exception { //Integration test
		//Arrange
		
		
		//Act
		purchaseOrder.setProvider(provider2);
		controller.updatePurchaseOrder(purchaseOrder);
		PurchaseOrder updatedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrder.getId(), true, false);
		
		//Assert
		assertFalse("Should not have 'provider' has linked provider", updatedPurchaseOrder.getProvider().getId() == provider.getId());
		assertTrue("Should have 'provider2' has linked provider", updatedPurchaseOrder.getProvider().getId() == provider2.getId());
	}
	
	@Test
	public void testDeletePurchaseOrder() throws SQLException, Exception { //Integration test
		//Arrange
		
		
		//Act
		controller.deletePurchaseOrder(purchaseOrderToDelete);
		PurchaseOrder deletedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrderToDelete.getId(), false, false);
		
		//Assert
		assertNull("Should not have provider has linked provider", deletedPurchaseOrder);
	}
	
	@Test
	public void testFindAllPurchaseOrder() { //Integration test
		//Arrange
		
		
		//Act
		List<PurchaseOrder> purchaseOrderList = controller.findAllPurchaseOrder();
		
		//Assert
		assertNotNull("Should retrieve a list", purchaseOrderList);
		
	}
	
	@Test
	public void testRetrieveTableData() { //Integration test
		//Arrange
		
		
		//Act
		String[][] data = controller.retrieveTableData();
		
		//Assert
		for(int i=0 ; i < data.length ; i++) {
			for(int j=0 ; j< data[i].length ; j++) {
				assertFalse("Should not be empty", data[i][j].equals(""));
			}
		}
		
	}

	@Test
	public void testRetrieveIdRelatedToPurchaseOrderHashMap() { //Integration test
		//Arrange
		
		
		//Act
		HashMap<Integer, PurchaseOrder> hashmap = controller.retrieveIdRelatedToPurchaseOrderHashMap();
		
		//Assert
		assertNotNull("The hashmap shouldn't be null", hashmap);
		
	}
	
	@Test
	public void testFindAllLineItemRelatedToThisPurchaseOrder() {
		//Arrange
		
		//Act
		List<LineItem> lineItemList = controller.findAllLineItemRelatedToThisPurchaseOrder(purchaseOrder);
		
		//Assert
		assertFalse("Should retrieve a non-empty list", lineItemList.isEmpty());
	}
	
	@Test
	public void testDeleteLineItemFromPurchaseOrder() {
		//Arrange
		CRUDPurchaseOrderControllerImplementation controllerImplementation = (CRUDPurchaseOrderControllerImplementation) controller;
		LineItem lineItem = ModelFactory.getLineItemModel(0, product, purchaseOrder);
		controllerImplementation.getProductAlreadyPresentMapForTestReasonOnly().put(product, 0);
		
		//Act
		controller.deleteLineItemFromPurchaseOrder(lineItem);
		
		//Assert
		assertFalse("Shouldn't contain the product", controllerImplementation.getProductAlreadyPresentMapForTestReasonOnly().containsKey(product));
		assertTrue("Should contain the lineItem", controllerImplementation.getDeleteListForTestReasonOnly().contains(lineItem));
	}
	
	@Test
	public void testAddProductToPurchaseOrder() {
		//Arrange
		CRUDPurchaseOrderControllerImplementation controllerImplementation = (CRUDPurchaseOrderControllerImplementation) controller;
		
		//Act
		controller.addProductToPurchaseOrder(product, 2);
		
		//Assert
		assertTrue("Should contain the product", controllerImplementation.getProductAlreadyPresentMapForTestReasonOnly().containsKey(product));
		assertTrue("Should contain the lineItem", controllerImplementation.getProductToAddListForTestReasonOnly().contains(product));
	}
	
	@After
	public  void reset() {
		CRUDPurchaseOrderControllerImplementation controllerImplementation = (CRUDPurchaseOrderControllerImplementation) controller;
		controllerImplementation.getDeleteListForTestReasonOnly().clear();
		controllerImplementation.getProductAlreadyPresentMapForTestReasonOnly().clear();
		controllerImplementation.getProductToAddListForTestReasonOnly().clear();
	}
	
	@AfterClass
	public static void cleanUp() throws Exception {
		DaoFactory.getLineItemDao().deleteLineItem(lineItem);
		
		DaoFactory.getProductDao().deleteProduct(product);
		
		DaoFactory.getPurchaseOrderDao().deletePurchaseOrder(purchaseOrder);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrder);
		
		DaoFactory.getPurchaseOrderDao().deletePurchaseOrder(purchaseOrderToDelete);
		DaoFactory.getOrderDao().deleteOrder(purchaseOrderToDelete);
		
		DaoFactory.getProviderDao().deleteProvider(provider);
		DaoFactory.getProviderDao().deleteProvider(provider2);
		
		
		
		
		
	}
	
}
