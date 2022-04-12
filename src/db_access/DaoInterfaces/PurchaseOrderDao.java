package db_access.DaoInterfaces;

import model.Provider;
import model.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseOrderDao {
    Order findPurchaseOrderById(int purchaseOrderId) throws SQLException;
    List<Order> findAllPurchaseOrders() throws SQLException;

    int createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException;
    void updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException;
    void deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException;
}
