package db_access.DaoInterfaces;

import model.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseOrderDao {
	PurchaseOrder findPurchaseOrderById(int id, boolean retrieveProvider, boolean retrieveLineItem)  throws SQLException, Exception;
    List<PurchaseOrder> findAllPurchaseOrders(boolean retrieveProvider, boolean retrieveLineItem) throws SQLException, Exception;
    ArrayList<PurchaseOrder> findPurchaseOrderByProviderId(int id, boolean retrieveProvider, boolean retrieveLineItem) throws SQLException, Exception;
    void createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException;
    void updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException;
    void deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException;
	
	
}
