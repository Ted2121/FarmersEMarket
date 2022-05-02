package controller.ControllerInterfaces;

import java.util.HashMap;
import java.util.List;

import model.Product;
import model.Provider;

public interface CreatePurchaseOrderController {
	List<Provider> searchProviderUsingThisName(String providerName);
	List<Product> searchProductUsingThisName(String productName);
	void addProductToPurchaseOrder(Product product, int quantity);
	void createPurchaseOrder(Provider provider);
}
