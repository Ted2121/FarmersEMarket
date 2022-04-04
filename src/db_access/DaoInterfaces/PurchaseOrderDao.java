package db_access.DaoInterfaces;

import model.Provider;
import model.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderDao {
    PurchaseOrder findPurchaseOrderById(int purchaseOrderId) throws SQLException;
    ArrayList<PurchaseOrder> findAllPurchaseOrders() throws SQLException;
    //TODO do we need this?
//    ArrayList<PurchaseOrder> findPurchaseOrdersByProvider(Provider provider) throws SQLException;
    int createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException;
    void updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException;
    void deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException;
}
