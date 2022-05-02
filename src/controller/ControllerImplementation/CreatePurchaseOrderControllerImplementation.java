package controller.ControllerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.ControllerInterfaces.CreatePurchaseOrderController;
import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.ProviderDao;
import model.LineItem;
import model.ModelFactory;
import model.Product;
import model.Provider;
import model.PurchaseOrder;

public class CreatePurchaseOrderControllerImplementation implements CreatePurchaseOrderController{
	private HashMap<Product, Integer> productWithQuantity;
	private PurchaseOrder purchaseOrder;
	private Connection connection;
	
	

	public CreatePurchaseOrderControllerImplementation() {
		super();
		this.productWithQuantity = new HashMap<Product, Integer>();
		connection = DBConnection.getInstance().getDBCon();
	}

	@Override
	public void addProductToPurchaseOrder(Product product, int quantity) {
		if(productWithQuantity.containsKey(product)) {
			int oldQuantity = productWithQuantity.get(product);
			productWithQuantity.replace(product, oldQuantity+quantity);
		}else {
			productWithQuantity.put(product, quantity);
		}
		
	}

	@Override
	public void createPurchaseOrder(Provider provider) {
		purchaseOrder = ModelFactory.getPurchaseOrderModel(provider);
		purchaseOrder.setOrderPrice(calculateTotalPrice());
		ArrayList<LineItem> generatedLineItems = (ArrayList<LineItem>) generateLineItems(); 
		
		//Transaction
		try {
			connection.setAutoCommit(false);
			
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
			
			connection.commit();
			
			
		}catch(SQLException e ) {
			System.out.println("Error during insertion of Order, PurchaseOrder or LineItems in the database");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("System cannot rollback the database instruction");
			}
		}catch(Exception e) {
			System.out.println("Cannot set association, not a database error");
			try {
				connection.rollback();
			}catch (SQLException e1) {
				System.out.println("System cannot rollback the database instruction");
			}
			
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("System can't autocommit");
			}
		}
		
	}

	private List<LineItem> generateLineItems() {
		ArrayList<LineItem> generatedLineItems = new ArrayList<LineItem>();
		for(Product key : productWithQuantity.keySet()) {

			LineItem lineItem = ModelFactory.getLineItemModel(productWithQuantity.get(key), key, purchaseOrder);
			generatedLineItems.add(lineItem);
		}
		return generatedLineItems;
	}

	private double calculateTotalPrice() {
		double totalPrice = 0;
		for(Product key : productWithQuantity.keySet()) {
			totalPrice += key.getPurchasingPrice() * productWithQuantity.get(key);
		}
		return totalPrice;
	}

	
	//Created for test reasons
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	@Override
	public List<Provider> searchProviderUsingThisName(String providerName) {
		ProviderDao providerDao = DaoFactory.getProviderDao();
		List<Provider> providerListMatchingToTheSearch = null;
		try {
			providerListMatchingToTheSearch = providerDao.findProvidersByName(providerName, false);
		} catch (SQLException e) {
			System.out.println("Cannot retrieve provider in the Database");
		} catch (Exception e) {
			System.out.println("Error during provider search process");
		}
		return providerListMatchingToTheSearch;
	}

	@Override
	public List<Product> searchProductUsingThisName(String productName) {
		ProductDao productDao = DaoFactory.getProductDao();
		List<Product> productListMatchingToTheSearch = null;
		try {
			productListMatchingToTheSearch = productDao.findProductsByPartialName(productName, false, false);
		} catch (SQLException e) {
			System.out.println("Cannot retrieve provider in the Database");
		} catch (Exception e) {
			System.out.println("Error during provider search process");
		}
		return productListMatchingToTheSearch;
	}

		
	
}
