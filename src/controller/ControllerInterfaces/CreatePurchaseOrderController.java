package controller.ControllerInterfaces;


import controller.FastSearchHelperClass;
import model.Product;
import model.Provider;

public interface CreatePurchaseOrderController {
	void addProductToPurchaseOrder(Product product, int quantity);
	void deleteProductFromPurchaseOrder(Product product);
	boolean isProductAlreadyInThePurchaseOrder(Product product);
	void createPurchaseOrder(Provider provider);
}
