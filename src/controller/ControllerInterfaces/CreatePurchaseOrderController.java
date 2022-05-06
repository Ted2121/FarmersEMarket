package controller.ControllerInterfaces;

import java.util.HashMap;
import java.util.List;

import model.Product;
import model.Provider;

public interface CreatePurchaseOrderController extends RetrievingSubsetController{
	void addProductToPurchaseOrder(Product product, int quantity);
	void deleteProductFromPurchaseOrder(Product product);
	boolean isProductAlreadyInThePurchaseOrder(Product product);
	void createPurchaseOrder(Provider provider);
}
