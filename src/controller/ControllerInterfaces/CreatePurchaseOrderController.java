package controller.ControllerInterfaces;


import model.Product;
import model.Provider;

public interface CreatePurchaseOrderController extends RetrievingSubsetController{
	void addProductToPurchaseOrder(Product product, int quantity);
	void deleteProductFromPurchaseOrder(Product product);
	boolean isProductAlreadyInThePurchaseOrder(Product product);
	void createPurchaseOrder(Provider provider);
}
