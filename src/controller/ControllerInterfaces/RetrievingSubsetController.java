package controller.ControllerInterfaces;

import java.util.List;

import model.Customer;
import model.Product;
import model.Provider;

public interface RetrievingSubsetController{
	<T>List<T> retrieveAllObjectsSubset(Class<T> cls);
	List<Provider> searchProviderUsingThisName(String providerName);
	List<Product> searchProductUsingThisName(String productName);
	List<Customer> searchCustomerUsingThisName(String customerName);
}
