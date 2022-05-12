package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db_access.DaoFactory;
import model.LineItem;
import model.ModelFactory;
import model.Order;
import model.Product;

public class HelperClass {

	private HelperClass() {};
	
	public static double calculateTotalPrice(HashMap<Product, Integer> productWithQuantity) {
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

	public static  List<LineItem> generateLineItems(HashMap<Product, Integer> productWithQuantity, Order order) {
		//First we instanciate a list of LineItems
		ArrayList<LineItem> generatedLineItems = new ArrayList<LineItem>();
		
		//For each product as a key in the hashmap
		for(Product key : productWithQuantity.keySet()) {
			//We create a LineItem using the ModelFactory
			LineItem lineItem = ModelFactory.getLineItemModel(productWithQuantity.get(key), key, order);
			//And add it to the list
			generatedLineItems.add(lineItem);
		}
		//Once every LineItems have been created, we return the list
		return generatedLineItems;
	}

	public static void exchangeProductSubsetWithQuantityHashMapToCompleteProducts(
			HashMap<Product, Integer> productWithQuantity) throws SQLException, Exception {
		for(Product key : productWithQuantity.keySet()) {
			Product completeProduct = null;
			completeProduct = DaoFactory.getProductDao().findProductById(key.getId(), false, false);
			key.setPurchasingPrice(completeProduct.getPurchasingPrice());
			key.setSellingPrice(completeProduct.getSellingPrice());
		}
	}
}
