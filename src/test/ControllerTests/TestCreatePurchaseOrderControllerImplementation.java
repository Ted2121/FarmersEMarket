package test.ControllerTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import controller.ControllerFactory;
import controller.FastSearchFactory;
import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import controller.ControllerInterfaces.SearchProductInterface;
import controller.ControllerInterfaces.SearchProviderInterface;
import db_access.DaoFactory;
import model.*;

public class TestCreatePurchaseOrderControllerImplementation {
	private static CreatePurchaseOrderController controller;
	private static SearchProductInterface productSearchControllerPart;
	private static SearchProviderInterface providerSearchControllerPart;
	
	private static int quantityOfEachLineItem;
	
	@BeforeClass
	public static void setUp() {
		controller = ControllerFactory.getCreatePurchaseOrderController();
		productSearchControllerPart = (SearchProductInterface) controller;
		providerSearchControllerPart = (SearchProviderInterface) controller;
		
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
	
	@Test
	public void testSearchProviderUsingThisName() {
		String name = "Ca";
		providerSearchControllerPart.providerSearchRefreshData();
		List<Provider> providersUsingTheName = providerSearchControllerPart.searchProviderUsingThisName(name);
		assertTrue("Should return a list with more than 0 results", providersUsingTheName.size()>0);
	}
	
	@Test
	public void testSearchProductUsingThisName() {
		String name = "Ca";
		List<Product> productsUsingTheName = productSearchControllerPart.searchProductUsingThisName(name);
		assertTrue("Should return a list with more than 0 results", productsUsingTheName.size()>0);
	}

//	@Test
//	public void testRetrieveAllObjectsSubset() {
//		assertTrue("Should retrieve a list of product subsets with more than 0 items",productSearchControllerPart.retrieveAllObjectsSubset(Product.class).size()>0);
//		assertTrue("Should retrieve a list of provider subsets with more than 0 items",providerSearchControllerPart.retrieveAllObjectsSubset(Provider.class).size()>0);
//		assertNull("Shouldn't return anything because an exception occured and have been handled in the controller",controller.retrieveAllObjectsSubset(LineItem.class));
//	}
	
	@Test
	public void testAddDeleteProductFromPurchaseOrder() {
		Product product = new Product();
		controller.addProductToPurchaseOrder(product, 2);
		assertTrue("The product should have been added to the PurchaseOrder", controller.isProductAlreadyInThePurchaseOrder(product));
		controller.deleteProductFromPurchaseOrder(product);
		assertFalse("The product should have been removed from the PurchaseOrder", controller.isProductAlreadyInThePurchaseOrder(product));

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
