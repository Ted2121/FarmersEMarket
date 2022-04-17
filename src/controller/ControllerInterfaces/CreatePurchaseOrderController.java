package controller.ControllerInterfaces;

import java.util.HashMap;

import model.Product;
import model.Provider;

public interface CreatePurchaseOrderController {
	void addProductToPurchaseOrder(Product product, int quantity);
	void createPurchaseOrder(Provider provider);
}
