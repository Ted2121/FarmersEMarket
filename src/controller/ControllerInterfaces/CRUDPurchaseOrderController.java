package controller.ControllerInterfaces;

import java.util.HashMap;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import model.LineItem;
import model.Product;
import model.PurchaseOrder;

public interface CRUDPurchaseOrderController extends RetrievingSubsetController{
	void updatePurchaseOrder(PurchaseOrder purchaseOrder);
	void deletePurchaseOrder(PurchaseOrder purchaseOrder);
	List<PurchaseOrder> findAllPurchaseOrder();
	String[][] retrieveTableData();
	HashMap<Integer, PurchaseOrder> retrieveIdRelatedToPurchaseOrderHashMap();
	List<LineItem> finAllLineItemRelatedToThisPurchaseOrder(PurchaseOrder purchaseOrder);
	void deleteLineItemFromPurchaseOrder(LineItem lineItem);
	void addProductToPurchaseOrder(Product selectedProduct, int quantity);
	boolean isProductAlreadyInThePurchaseOrder(Product selectedProduct);
	void deleteProductInProductToAdd(Product product);
}
