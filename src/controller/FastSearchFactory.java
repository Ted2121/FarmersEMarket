package controller;

import java.util.List;

import model.Customer;
import model.Product;
import model.Provider;

public class FastSearchFactory {
	
	private static FastSearchHelperClass<Product> productFastSearch;
	private static FastSearchHelperClass<Provider> providerFastSearch;
	private static FastSearchHelperClass<Customer> customerFastSearch;
	
	private FastSearchFactory() {}
	
	public static FastSearchHelperClass<Product> getProductFastSearch() {
		if(productFastSearch == null) {
			productFastSearch = new FastSearchHelperClass<>(Product.class);
		}
		return productFastSearch;
	}
	
	public static FastSearchHelperClass<Provider> getProviderFastSearch() {
		if(providerFastSearch == null) {
			providerFastSearch = new FastSearchHelperClass<>(Provider.class);
		}
		return providerFastSearch;
	}
	
	public static FastSearchHelperClass<Customer> getCustomerFastSearch() {
		if(customerFastSearch == null) {
			customerFastSearch = new FastSearchHelperClass<>(Customer.class);
		}
		return customerFastSearch;
	}
}
