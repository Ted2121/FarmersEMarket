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
	private ProductDao productDao;
	private ProviderDao providerDao;
	
	List<List<Product>> productContainingLetter;

	public CreatePurchaseOrderControllerImplementation() {
		super();
		this.productWithQuantity = new HashMap<Product, Integer>();
		connection = DBConnection.getInstance().getDBCon();
		productDao = DaoFactory.getProductDao();
		providerDao = DaoFactory.getProviderDao();
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
		List<Product> matchingProducts = new ArrayList<Product>();
		List<Product> productMatchingTheLetters = sortLetterMatchingProducts(productName);
		if(productMatchingTheLetters != null)
			for(Product product : productMatchingTheLetters) {
				if(product.getProductName().contains(productName)) {
					matchingProducts.add(product);
				}
			}
		
		return matchingProducts;
	}

	private List<Product> sortLetterMatchingProducts(String productName) {
		String lowerCaseProductNameWithoutSpaceCharacter = productName.toLowerCase().replaceAll(" ", "");
		List<List<Product>> listOfListOfProductContainingTheLetterOfThisName = new ArrayList<List<Product>>();
		
		//Retrieving all the list of products containing the specified characteres of each list
		for(int i=0 ; i<lowerCaseProductNameWithoutSpaceCharacter.length();i++) {
			char character = lowerCaseProductNameWithoutSpaceCharacter.charAt(i);
			
			int characterListNumber = 0;
			try {
				characterListNumber = settingTheCharacterToAnHandledOne(character);
				if(!listOfListOfProductContainingTheLetterOfThisName.contains(productContainingLetter.get(characterListNumber)))
					listOfListOfProductContainingTheLetterOfThisName.add(productContainingLetter.get(characterListNumber));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}
		
		//Making an intersection of all lists to retrieve only matching products
		List<Product> matchingProducts = null;
		if(listOfListOfProductContainingTheLetterOfThisName.size()>0)
			matchingProducts = listOfListOfProductContainingTheLetterOfThisName.get(0);
		for(List<Product> productList :  listOfListOfProductContainingTheLetterOfThisName) {
			matchingProducts.retainAll(productList);
		}
		
		return matchingProducts;
	}

	private int settingTheCharacterToAnHandledOne(char character) throws Exception {
		switch(character) {
			case 235 -> character = 101;
			case 234 -> character = 101;
			case 233 -> character = 101;
			case 232 -> character = 101;
		}
		int numberOfTheListOfThisCharacter = character-97;
		if(numberOfTheListOfThisCharacter < 0 || numberOfTheListOfThisCharacter >= 26) {
			throw new Exception("Character unhandled by the application: " + character);
		}
		return numberOfTheListOfThisCharacter;
	}

	@Override
	public List<Product> retrieveAllProductSubset() {
		List<Product> productSubsetList =null;
		productContainingLetter = new ArrayList<List<Product>>();
		
		for(int i=0;i<26;i++) {
			productContainingLetter.add(new ArrayList<Product>());
		}
		
		
		try {
			productSubsetList = productDao.findAllProductSubset();
			for(Product product : productSubsetList) {
				String lowerCaseProductNameWithoutSpaceCharacter = product.getProductName().toLowerCase().replaceAll(" ", "");
				for(int i=0 ; i<lowerCaseProductNameWithoutSpaceCharacter.length();i++) {
					char character = lowerCaseProductNameWithoutSpaceCharacter.charAt(i);

					int characterListNumber = 0;
					try {
						characterListNumber = settingTheCharacterToAnHandledOne(character);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

					List<Product> letterList = productContainingLetter.get(characterListNumber);
					if(!letterList.contains(product)) {
						letterList.add(product);
					}
				}
				
				
			}
		
		
		} catch (SQLException e) {
			System.out.println("Cannot retrieve products subset from database");
		} catch (Exception e) {
			System.out.println("Cannot retrieve products subset");
		}
		
		if(productSubsetList.size() < 31) {
			return productSubsetList;
		}
		
		return productSubsetList.subList(0, 30);
	}

		
	
}
