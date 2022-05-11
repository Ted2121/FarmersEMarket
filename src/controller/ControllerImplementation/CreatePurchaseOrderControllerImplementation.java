package controller.ControllerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.FastSearchFactory;
import controller.FastSearchHelperClass;
import controller.HelperClass;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import controller.ControllerInterfaces.SearchProductInterface;
import controller.ControllerInterfaces.SearchProviderInterface;
import db_access.DBConnection;
import db_access.DaoFactory;
import model.*;

public class CreatePurchaseOrderControllerImplementation implements CreatePurchaseOrderController, SearchProductInterface, SearchProviderInterface {
	
	private Connection connection; 
	private HashMap<Product, Integer> productWithQuantity;
	private PurchaseOrder purchaseOrder;
	private FastSearchHelperClass<Product> productFastSearch;
	private FastSearchHelperClass<Provider> providerFastSearch;
	

	public CreatePurchaseOrderControllerImplementation() {
		super();
		connection = DBConnection.getInstance().getDBCon();
		productFastSearch = FastSearchFactory.getProductFastSearch();
		providerFastSearch = FastSearchFactory.getProviderFastSearch();
		//This hashmap will records all the selected products and their quantity
		this.productWithQuantity = new HashMap<Product, Integer>();
	}

	@Override
	public void addProductToPurchaseOrder(Product product, int quantity) {
		
		//If we already have this product in the list, we remove it
		if(productWithQuantity.containsKey(product))
			productWithQuantity.remove(product);
			
		//We add the product in the hashmap with its specific quantity
		productWithQuantity.put(product, quantity);
		
	}
	
	@Override
	public void deleteProductFromPurchaseOrder(Product product) {
		
		try {
		//If we try to delete a product that isn't contained in the hashmap, we throw an exception
		if(!productWithQuantity.containsKey(product))
			throw new Exception("Cannot delete a product that is not contained in the list");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		//We remove the product from the hashmap
		productWithQuantity.remove(product);
		
	}

	@Override
	public void createPurchaseOrder(Provider provider) {
		
		//First, we retrieve a new PurchaseOrder from the model factory by specifying its provider
		purchaseOrder = ModelFactory.getPurchaseOrderModel(provider);
		
		try {
			//We retrieve the wholes product to have its selling price
			HelperClass.exchangeProductSubsetWithQuantityHashMapToCompleteProducts(productWithQuantity);
		} catch (SQLException e) {
			System.out.println("Cannot retrieve the full product object due to database error");
		} catch (Exception e) {
			System.out.println("Cannot retrieve the full product object due to software");
		}
		
		
		//Then we calculate the total price of the order and set it (will return 0 if we don't have its sellig price)
		purchaseOrder.setOrderPrice(HelperClass.calculateTotalPrice(productWithQuantity));
		
		//We set a list of LineItems wich contains every generated LineItems from the hashmap
		List<LineItem> generatedLineItems = (ArrayList<LineItem>) generateLineItems(); 
		
		//We may start the transaction
		try {
			//First we tell the database not to commit what command we will send
			connection.setAutoCommit(false);
			
			//Then we start to write our commands, using the Dao
			//Creating the Order in the database and setting the id to the purchaseOrder
			DaoFactory.getOrderDao().createOrder(purchaseOrder);
			//Creating the PurchaseOrder in the database
			DaoFactory.getPurchaseOrderDao().createPurchaseOrder(purchaseOrder);
			//For each LineItems
			for(LineItem lineItem : generatedLineItems) {
				//Creating LineItems in the database
				DaoFactory.getLineItemDao().createLineItem(lineItem);
				//Adding the quantity to productInformation in the database
				DaoFactory.getProductInformationDao().addQuantityToProduct(lineItem.getProduct(), lineItem.getQuantity());
			}
			
			//Once the order, purchaseOrder and LineItems have been added to the database, we commit all our changes
			connection.commit();
			
			
		}catch(SQLException e ) {
			
			//If only one of the previous database methods doesn't work, we print this line
			System.out.println("Error during insertion of Order, PurchaseOrder or LineItems in the database");
			try {
				//And we try to rollback as if we never tried to start the transaction
				connection.rollback();
				System.out.println("Connection rolled back");
			} catch (SQLException e1) {
				//If the rollback cannot be done, we print this line
				System.out.println("System cannot rollback the database instruction");
			}
		}catch(Exception e) {
			//If there is an error due to the association of each class (Order linked to PurchaseOrder etc...) we print this line
			System.out.println("Cannot set association, not a database error");
			try {
				//And we try to rollback as if we never tried to start the transaction
				connection.rollback();
				System.out.println("Connection rolled back");
			}catch (SQLException e1) {
				//If the rollback cannot be done, we print this line
				System.out.println("System cannot rollback the database instruction");
			}
			
		}finally {
			//If the transaction have been complete or not, we ask the database to autocommit again
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				//If the autocommit cannot be done, we print this line
				System.out.println("System can't autocommit");
			}
		}
		
	}

	private List<LineItem> generateLineItems() {
		//First we instanciate a list of LineItems
		ArrayList<LineItem> generatedLineItems = new ArrayList<LineItem>();
		
		//For each product as a key in the hashmap
		for(Product key : productWithQuantity.keySet()) {
			//We create a LineItem using the ModelFactory
			LineItem lineItem = ModelFactory.getLineItemModel(productWithQuantity.get(key), key, purchaseOrder);
			//And add it to the list
			generatedLineItems.add(lineItem);
		}
		//Once every LineItems have been created, we return the list
		return generatedLineItems;
	}

	//Created for test reasons
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	@Override
	public boolean isProductAlreadyInThePurchaseOrder(Product product) {
		//We return true if the product is already in the hashmap
		return productWithQuantity.containsKey(product);
	}

	
	@Override
	public List<Provider> searchProviderUsingThisName(String providerName) {
		return providerFastSearch.searchUsingThisName(providerName);
	}
	
	@Override
	public List<Product> searchProductUsingThisName(String productName) {
		return productFastSearch.searchUsingThisName(productName);
	}

	@Override
	public void providerSearchRefreshData() {
		providerFastSearch.refreshData();
	}

	@Override
	public void productSearchRefreshData() {
		productFastSearch.refreshData();
	}

		
	
}
