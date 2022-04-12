package db_access.DaoInterfaces;

import model.Order;
import model.Provider;
import model.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseOrderDao {
	PurchaseOrder findPurchaseOrderById(int purchaseOrderId) throws SQLException;
    List<PurchaseOrder> findAllPurchaseOrders() throws SQLException;

    void createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException;
    void updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException;
    void deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException;
}
