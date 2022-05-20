package controller;

import java.util.List;

import db_access.DaoFactory;
import db_access.DaoImplementation.ProductDaoImplementation;
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
			productFastSearch = new FastSearchHelperClass<>("Product",DaoFactory.getProductDao());
		}
		return productFastSearch;
	}
	
	public static FastSearchHelperClass<Provider> getProviderFastSearch() {
		if(providerFastSearch == null) {
			providerFastSearch = new FastSearchHelperClass<>("Provider",DaoFactory.getProviderDao());
		}
		return providerFastSearch;
	}
	
	public static FastSearchHelperClass<Customer> getCustomerFastSearch() {
		if(customerFastSearch == null) {
			customerFastSearch = new FastSearchHelperClass<>("Customer",DaoFactory.getCustomerDao());
		}
		return customerFastSearch;
	}
}
