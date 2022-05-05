package controller.ControllerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
	List<List<Provider>> providerContainingLetter;

	public CreatePurchaseOrderControllerImplementation() {
		super();
		this.productWithQuantity = new HashMap<Product, Integer>();
		connection = DBConnection.getInstance().getDBCon();
		productDao = DaoFactory.getProductDao();
		providerDao = DaoFactory.getProviderDao();
	}

	@Override
	public void addProductToPurchaseOrder(Product product, int quantity) {
		if(productWithQuantity.containsKey(product))
			productWithQuantity.remove(product);
			
		
		productWithQuantity.put(product, quantity);
		
	}
	
	@Override
	public void deleteProductFromPurchaseOrder(Product product) {
		
		try {
		if(!productWithQuantity.containsKey(product))
			throw new Exception("Cannot delete a product that is not contained in the list");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		productWithQuantity.remove(product);
		
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
		List<Provider> matchingProvider = new ArrayList<Provider>();
		List<Provider> providerMatchingTheLetters = sortLetterToMatchingObjects(providerName, Provider.class);
		if(providerMatchingTheLetters != null)
			for(Provider provider : providerMatchingTheLetters) {
				if(provider.getFullName().toLowerCase().replace(" ", "").contains(providerName.toLowerCase().replace(" ", ""))) {
					matchingProvider.add(provider);
				}
			}
		else matchingProvider.addAll(providerMatchingTheLetters);
		
		return matchingProvider;
	}

	@Override
	public List<Product> searchProductUsingThisName(String productName) {
		List<Product> matchingProducts = new ArrayList<Product>();
		List<Product> productMatchingTheLetters = sortLetterToMatchingObjects(productName, Product.class);
		if(productMatchingTheLetters != null)
			for(Product product : productMatchingTheLetters) {
				if(product.getProductName().toLowerCase().replace(" ", "").contains(productName.toLowerCase().replace(" ", ""))) {
					matchingProducts.add(product);
				}
			}
		else matchingProducts.addAll(productMatchingTheLetters);
		
		return matchingProducts;
	}

	private <T> List<T> sortLetterToMatchingObjects(String stringToSort, Class<T> cls ) {
		String lowerCaseStringWithoutSpaceCharacter = stringToSort.toLowerCase().replaceAll(" ", "");
		List<List<T>> listOfListOfObjectsContainingTheLetterOfThisName = new ArrayList<List<T>>();
		
		//Retrieving all the list of products containing the specified characteres of each list
		for(int i=0 ; i<lowerCaseStringWithoutSpaceCharacter.length();i++) {
			char character = lowerCaseStringWithoutSpaceCharacter.charAt(i);
			
			int characterListNumber = 0;
			try {
				characterListNumber = settingTheCharacterToAnHandledOne(character);
				if(cls.equals(Product.class) && !listOfListOfObjectsContainingTheLetterOfThisName.contains(productContainingLetter.get(characterListNumber)))
					listOfListOfObjectsContainingTheLetterOfThisName.add((List<T>) productContainingLetter.get(characterListNumber));
				else if(cls.equals(Provider.class) && !listOfListOfObjectsContainingTheLetterOfThisName.contains(providerContainingLetter.get(characterListNumber))) {
					listOfListOfObjectsContainingTheLetterOfThisName.add((List<T>) providerContainingLetter.get(characterListNumber));
				}
					
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}
		
		//Making an intersection of all lists to retrieve only matching products
		List<T> matchingObjects = null;
		if(listOfListOfObjectsContainingTheLetterOfThisName.size()>0)
			matchingObjects = listOfListOfObjectsContainingTheLetterOfThisName.get(0);
		for(List<T> list :  listOfListOfObjectsContainingTheLetterOfThisName) {
			matchingObjects.retainAll(list);
		}
		
		if(matchingObjects == null) {
			matchingObjects = new ArrayList<>();
			if(cls.equals(Product.class)) {
				for(List<Product> list : productContainingLetter) {
					matchingObjects.addAll( (Collection<? extends T>) list);
				}
			}else if(cls.equals(Provider.class)) {
				for(List<Provider> list : providerContainingLetter) {
					matchingObjects.addAll((Collection<? extends T>) list);
				}
			}
			
			
			//Delete duplicates
			matchingObjects = new ArrayList<>(new LinkedHashSet<>(matchingObjects));
		}
		
		return matchingObjects;
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
	public <T> List<T> retrieveAllObjectsSubset(Class<T> cls) {
		try {
			if(!cls.equals(Product.class) && !cls.equals(Provider.class) )
				throw new Exception("Class not suported by the retrieveAllObjectsSubset method");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		List<T> objectSubsetList = null;
		if(cls.equals(Product.class))
			productContainingLetter = new ArrayList<List<Product>>();
		else if(cls.equals(Provider.class))
			providerContainingLetter = new ArrayList<List<Provider>>();
		
		for(int i=0;i<26;i++) {
			if(cls.equals(Product.class))
				productContainingLetter.add(new ArrayList<Product>());
			else if(cls.equals(Provider.class))
				providerContainingLetter.add(new ArrayList<Provider>());
		}
		
		
		try {
			if(cls.equals(Product.class))
				objectSubsetList = (List<T>) productDao.findAllProductSubset();
			else if(cls.equals(Provider.class))
				objectSubsetList = (List<T>) providerDao.findAllProviderSubset();
			
			for(T object : objectSubsetList) {
				String lowerCaseObjectWithoutSpaceCharacter = null;
				if(cls.equals(Product.class))
					lowerCaseObjectWithoutSpaceCharacter = ((Product) object).getProductName().toLowerCase().replaceAll(" ", "");
				else if(cls.equals(Provider.class))
					lowerCaseObjectWithoutSpaceCharacter = ((Provider) object).getFullName().toLowerCase().replaceAll(" ", "");
				if(lowerCaseObjectWithoutSpaceCharacter.isBlank()) {
					break;
				}
				for(int i=0 ; i<lowerCaseObjectWithoutSpaceCharacter.length();i++) {
					char character = lowerCaseObjectWithoutSpaceCharacter.charAt(i);

					int characterListNumber = 0;
					try {
						characterListNumber = settingTheCharacterToAnHandledOne(character);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					List<T> letterList = null;
					if(cls.equals(Product.class))
						letterList = (List<T>) productContainingLetter.get(characterListNumber);
					else if(cls.equals(Provider.class)) 
						letterList = (List<T>) providerContainingLetter.get(characterListNumber);
					if(!letterList.contains(object)) {
						letterList.add(object);
					}
				}
				
				
			}
		
		
		} catch (SQLException e) {
			System.out.println("Cannot retrieve products subset from database");
		} catch (Exception e) {
			System.out.println("Cannot retrieve products subset");
		}
		
		if(objectSubsetList == null) {
			return null;
		}
		
		if(objectSubsetList.size() < 31) {
			return objectSubsetList;
		}
		
		return objectSubsetList.subList(0, 30);
	}

	@Override
	public boolean isProductAlreadyInThePurchaseOrder(Product product) {
		return productWithQuantity.containsKey(product);
	}
		
	
}
