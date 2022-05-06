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
		//This hashmap will records all the selected products and their quantity
		this.productWithQuantity = new HashMap<Product, Integer>();
		connection = DBConnection.getInstance().getDBCon();
		
		//To create a purchase order we need this 2 Dao
		productDao = DaoFactory.getProductDao();
		providerDao = DaoFactory.getProviderDao();
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
		
		//Then we calculate the total price of the order and set it
		purchaseOrder.setOrderPrice(calculateTotalPrice());
		
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

	private double calculateTotalPrice() {
		//We set the total price to 0
		double totalPrice = 0;
		//For each product as a key in the hashmap
		for(Product key : productWithQuantity.keySet()) {
			//We calculate its price and add it to the total price
			totalPrice += key.getPurchasingPrice() * productWithQuantity.get(key);
		}
		//Once every price of each product have been added to the total, we return it
		return totalPrice;
	}

	//Created for test reasons
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	@Override
	public List<Provider> searchProviderUsingThisName(String providerName) {
		//We set a new list of Provider wich will contains all the providers matching the search
		List<Provider> matchingProvider = new ArrayList<Provider>();
		//We ask a method to get all the providers containng each letter of the providerName
		List<Provider> providerMatchingTheLetters = sortLetterToMatchingObjects(providerName, Provider.class);
		//If we have a result
		if(providerMatchingTheLetters != null)
			//For each provider in the list of results
			for(Provider provider : providerMatchingTheLetters) {
				//We get the FullName of this provider, change capitals letter to normals letter, delete white spaces and try if they contained the specific string
				if(provider.getFullName().toLowerCase().replace(" ", "").contains(providerName.toLowerCase().replace(" ", ""))) {
					//If they contains it, we add them to the matching list
					matchingProvider.add(provider);
				}
			}
		//If we don't have any results, we add all the providers
		else matchingProvider.addAll(providerMatchingTheLetters);
		//Finally we retrun all the providers matching th search
		return matchingProvider;
	}

	@Override
	public List<Product> searchProductUsingThisName(String productName) {
		//We set a new list of Product wich will contains all the providers matching the search
		List<Product> matchingProducts = new ArrayList<Product>();
		//We ask a method to get all the Products containng each letter of the productName
		List<Product> productMatchingTheLetters = sortLetterToMatchingObjects(productName, Product.class);
		//If we have a result
		if(productMatchingTheLetters != null)
			//For each product in the list of results
			for(Product product : productMatchingTheLetters) {
				//We get the name of this products, change capitals letter to normals letter, delete white spaces and try if they contained the specific string
				if(product.getProductName().toLowerCase().replace(" ", "").contains(productName.toLowerCase().replace(" ", ""))) {
					//If they contains it, we add them to the matching list
					matchingProducts.add(product);
				}
			}
		//If we don't have any results, we add all the products
		else matchingProducts.addAll(productMatchingTheLetters);
		//Finally we retrun all the products matching th search
		return matchingProducts;
	}

	private <T> List<T> sortLetterToMatchingObjects(String stringToSort, Class<T> cls ) {
		//This methods use generics and can use eatcher Product or Provider class
		
		//First, we take the string, change capitals letter to normals letter and delete white spaces
		String lowerCaseStringWithoutSpaceCharacter = stringToSort.toLowerCase().replaceAll(" ", "");
		//We create a list of list of the objects we want to sort
		List<List<T>> listOfListOfObjectsContainingTheLetterOfThisName = new ArrayList<List<T>>();
		
		//Retrieving all the list of objects containing the specified characteres of each list
		for(int i=0 ; i<lowerCaseStringWithoutSpaceCharacter.length();i++) {
			//We take 1 caractere of the string
			char character = lowerCaseStringWithoutSpaceCharacter.charAt(i);
			//We set the index at 0;
			int characterListNumber = 0;
			try {
				//We use a methods to retrieve only handled charactere (usefull for יטכך)
				characterListNumber = settingTheCharacterToAnHandledOne(character);
				
				//If we need to sort Products, and we don't already have a list containing this letter
				if(cls.equals(Product.class) && !listOfListOfObjectsContainingTheLetterOfThisName.contains(productContainingLetter.get(characterListNumber)))
					//We add the list of this letter to the list of list of letters
					listOfListOfObjectsContainingTheLetterOfThisName.add((List<T>) productContainingLetter.get(characterListNumber));
				//Else if we need to sort Provider, and we don't already have a list containing this letter
				else if(cls.equals(Provider.class) && !listOfListOfObjectsContainingTheLetterOfThisName.contains(providerContainingLetter.get(characterListNumber))) {
					//We add the list of this letter to the list of list of letters
					listOfListOfObjectsContainingTheLetterOfThisName.add((List<T>) providerContainingLetter.get(characterListNumber));
				}
					
			} catch (Exception e) {
				//If a Exception occurs during settingTheCharacterToAnHandledOne method, we print the message
				System.out.println(e.getMessage());
			}	
		}
		
		//Then we will make an intersection of all lists to retrieve only products with each letters
		List<T> matchingObjects = null;
		//If we have a list wich contains more than 0 list
		if(listOfListOfObjectsContainingTheLetterOfThisName.size()>0)
			//we set the matching object to the first index
			matchingObjects = listOfListOfObjectsContainingTheLetterOfThisName.get(0);
		//For each list in the list of list of object containing a specific letter
		for(List<T> list :  listOfListOfObjectsContainingTheLetterOfThisName) {
			//We make the intersection between the matchingObjects and the list
			matchingObjects.retainAll(list);
		}
		
		//If no objects match the search
		if(matchingObjects == null) {
			//We create a new ArrayList
			matchingObjects = new ArrayList<>();
			//If we search for products
			if(cls.equals(Product.class)) {
				//We add all the products in all the products letter lists
				for(List<Product> list : productContainingLetter) {
					matchingObjects.addAll( (Collection<? extends T>) list);
				}
			//Else if we search for providers
			}else if(cls.equals(Provider.class)) {
				//We add all the providers in all the products letter lists
				for(List<Provider> list : providerContainingLetter) {
					matchingObjects.addAll((Collection<? extends T>) list);
				}
			}
			
			
			//Finally, we get rid of duplicates
			matchingObjects = new ArrayList<>(new LinkedHashSet<>(matchingObjects));
		}
		
		//And we return the matchings objects
		return matchingObjects;
	}

	private int settingTheCharacterToAnHandledOne(char character) throws Exception {
		//This methods take a characrete and if it's a non handled charactere will set it to an handled one
		switch(character) {
			case 239 -> character = 105; //ן -> i
			case 238 -> character = 105; //מ -> i
			case 237 -> character = 105; //ם -> i
			case 236 -> character = 105; //ל -> i
			case 235 -> character = 101; //כ -> e
			case 234 -> character = 101; //ך -> e
			case 233 -> character = 101; //י -> e
			case 232 -> character = 101; //ט -> e
			case 231 -> character = 99;  //ח -> c
		}
		//We retrieve the charactere - 97 to get the index in the list of list
		int numberOfTheListOfThisCharacter = character-97;
		//If the character is not between 0 and 25
		if(numberOfTheListOfThisCharacter < 0 || numberOfTheListOfThisCharacter >= 26) {
			//We throw an Exception
			throw new Exception("Character unhandled by the application: " + character);
		}
		//Finally we return the index of the list
		return numberOfTheListOfThisCharacter;
	}

	@Override
	public <T> List<T> retrieveAllObjectsSubset(Class<T> cls) {
		//This method takes a class as parameter and retrieve all its subsets
		try {
			//If the class is not a product or a provider, we throw an Exception
			if(!cls.equals(Product.class) && !cls.equals(Provider.class) )
				throw new Exception("Class not suported by the retrieveAllObjectsSubset method");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		List<T> objectSubsetList = null;
		//If the class id product
		if(cls.equals(Product.class))
			//We create a new ArrayList of Products
			productContainingLetter = new ArrayList<List<Product>>();
		//Else if the class id provider
		else if(cls.equals(Provider.class))
			//We create a new ArrayList of Provider
			providerContainingLetter = new ArrayList<List<Provider>>();
		
		//Then we instanciate a new List for all 26 letter of the alphabete (special charactere should have already been handled)
		for(int i=0;i<26;i++) {
			if(cls.equals(Product.class))
				productContainingLetter.add(new ArrayList<Product>());
			else if(cls.equals(Provider.class))
				providerContainingLetter.add(new ArrayList<Provider>());
		}
		
		
		try {
			//Then we use the Dao class to retrieve the subsets
			if(cls.equals(Product.class))
				objectSubsetList = (List<T>) productDao.findAllProductSubset();
			else if(cls.equals(Provider.class))
				objectSubsetList = (List<T>) providerDao.findAllProviderSubset();
			
			//For each objects of the subset list
			for(T object : objectSubsetList) {
				String lowerCaseObjectWithoutSpaceCharacter = null;
				//We get their name version without capital and blank space
				if(cls.equals(Product.class))
					lowerCaseObjectWithoutSpaceCharacter = ((Product) object).getProductName().toLowerCase().replaceAll(" ", "");
				else if(cls.equals(Provider.class))
					lowerCaseObjectWithoutSpaceCharacter = ((Provider) object).getFullName().toLowerCase().replaceAll(" ", "");
				//If the String is blanck, we can stop the loop
				if(lowerCaseObjectWithoutSpaceCharacter.isBlank()) {
					break;
				}
				//For each charactere of the whole string
				for(int i=0 ; i<lowerCaseObjectWithoutSpaceCharacter.length();i++) {
					//We get the charactere at the next index (start 0)
					char character = lowerCaseObjectWithoutSpaceCharacter.charAt(i);

					//we set the charactere index at 0
					int characterListNumber = 0;
					try {
						//We get the list number of the character
						characterListNumber = settingTheCharacterToAnHandledOne(character);
					} catch (Exception e) {
						//If we get an exception, we print it in the console
						System.out.println(e.getMessage());
					}
					
					List<T> letterList = null;
					//Then we retrieve the list of the letter we are testing
					if(cls.equals(Product.class))
						letterList = (List<T>) productContainingLetter.get(characterListNumber);
					else if(cls.equals(Provider.class)) 
						letterList = (List<T>) providerContainingLetter.get(characterListNumber);
					
					//If it doesn't already contains this objects
					if(!letterList.contains(object)) {
						//We add it to the list
						letterList.add(object);
					}
				}
				
				
			}
		
		
		} catch (SQLException e) {
			//If a database excecption occure, we print this line
			System.out.println("Cannot retrieve products subset from database");
		} catch (Exception e) {
			//If an other excecption occure, we print this line
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
		//We return true if the product is already in the hashmap
		return productWithQuantity.containsKey(product);
	}
		
	
}
