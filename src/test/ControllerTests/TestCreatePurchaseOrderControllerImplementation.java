package test.ControllerTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
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
import model.Product.Unit;
import model.Product.WeightCategory;

public class TestCreatePurchaseOrderControllerImplementation {
	private static CreatePurchaseOrderController controller;
	private static SearchProductInterface productSearchControllerPart;
	private static SearchProviderInterface providerSearchControllerPart;
	
	private static Provider newProvider;
	private static Product newProduct;
	
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
		controller = ControllerFactory.getCreatePurchaseOrderController();
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
		
		productSearchControllerPart.productSearchRefreshData();
		List<Product> productsUsingTheName = productSearchControllerPart.searchProductUsingThisName(name);
		
		assertTrue("Should return a list with more than 0 results", productsUsingTheName.size()>0);
	}
	
	@Test
	public void testAddDeleteProductFromPurchaseOrder() {
		//We did 2 tests in one to be sure the second one follow the first one
		
		//Arrange
		Product product = new Product();
		//Act
		controller.addProductToPurchaseOrder(product, 2);
		//Assert
		assertTrue("The product should have been added to the PurchaseOrder", controller.isProductAlreadyInThePurchaseOrder(product));
		
		//Act
		controller.deleteProductFromPurchaseOrder(product);
		//Assert
		assertFalse("The product should have been removed from the PurchaseOrder", controller.isProductAlreadyInThePurchaseOrder(product));

	}
	
	@Test
	public void testProviderSearchRefreshData() throws SQLException {
		//Arrange
		newProvider= ModelFactory.getProviderModel("curiousName", "last name", "test", "test");
		DaoFactory.getProviderDao().createProvider(newProvider);
		
		//Act
		List<Provider> providerListBeforeRefresh = providerSearchControllerPart.searchProviderUsingThisName("curiousName");
		providerSearchControllerPart.providerSearchRefreshData();
		List<Provider> providerListAfterRefresh = providerSearchControllerPart.searchProviderUsingThisName("curiousName");
		
		//Assert
		assertTrue("Should be an empty list",providerListBeforeRefresh.isEmpty());
		assertFalse("Shouldn't be an empty list",providerListAfterRefresh.isEmpty());
		
	}
	
	@Test
	public void testProductSearchRefreshData() throws SQLException{
		//Arrange
		newProduct = ModelFactory.getProductModel("curiousName", 0, 0, WeightCategory.ONE, Unit.KG);
		DaoFactory.getProductDao().createProduct(newProduct);
		
		//Act
		List<Product> productListBeforeRefresh = productSearchControllerPart.searchProductUsingThisName("curiousName");
		productSearchControllerPart.productSearchRefreshData();
		List<Product> productListAfterRefresh = productSearchControllerPart.searchProductUsingThisName("curiousName");
		
		//Assert
		assertTrue("Should be an empty list",productListBeforeRefresh.isEmpty());
		assertFalse("Shouldn't be an empty list",productListAfterRefresh.isEmpty());
	}
	
	@AfterClass
	public static void cleanUp() throws Exception {
		CreatePurchaseOrderControllerImplementation controllerImplementation = (CreatePurchaseOrderControllerImplementation) controller;
		PurchaseOrder purchaseOrder = controllerImplementation.getPurchaseOrder();
		
		while(!DaoFactory.getLineItemDao().findLineItemsByOrder(purchaseOrder, true).isEmpty()) {
			for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(purchaseOrder, true)) {
				lineItem.setOrder(purchaseOrder);
				DaoFactory.getLineItemDao().deleteLineItem(lineItem);
				DaoFactory.getProductInformationDao().removeQuantityToProduct(lineItem.getProduct(), quantityOfEachLineItem);
			}
		}
		
		while(DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(purchaseOrder.getId(), false, false) != null) {
			DaoFactory.getPurchaseOrderDao().deletePurchaseOrder(purchaseOrder);
			DaoFactory.getOrderDao().deleteOrder(purchaseOrder);
		}
		
			
		while(DaoFactory.getProviderDao().findProviderById(newProvider.getId(), false) != null) {
			DaoFactory.getProviderDao().deleteProvider(newProvider);
		}
		
		while(DaoFactory.getProductDao().findProductById(newProduct.getId(), false, false) != null) {
			DaoFactory.getProductDao().deleteProduct(newProduct);
		}
			
		List<Product> productListToDelete = productSearchControllerPart.searchProductUsingThisName("curiousName");
		while(!productListToDelete.isEmpty()) {
	        for(Product product : productListToDelete) {
	        	DaoFactory.getProductDao().deleteProduct(product);
	        }
	        productSearchControllerPart.productSearchRefreshData();
	        productListToDelete = productSearchControllerPart.searchProductUsingThisName("curiousName");
		}
		
		List<Provider> providerListToDelete = providerSearchControllerPart.searchProviderUsingThisName("curiousName");
		while(!providerListToDelete.isEmpty()) {
	        for(Provider provider : providerListToDelete) {
	        	DaoFactory.getProviderDao().deleteProvider(provider);
	        }
	        providerSearchControllerPart.providerSearchRefreshData();
	        providerListToDelete = providerSearchControllerPart.searchProviderUsingThisName("curiousName");
		}
	}
}
