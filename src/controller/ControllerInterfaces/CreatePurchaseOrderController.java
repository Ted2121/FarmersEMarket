package controller.ControllerInterfaces;

import java.util.HashMap;

import model.Product;
import model.Provider;

public interface CreatePurchaseOrderController {
	void addProductToPurchaseOrder(Product product, int quantity);
	void createPurchaseOrder(String date, Provider provider, HashMap<Product, Integer> productsWithQuantity);
}
