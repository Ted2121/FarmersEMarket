package db_access.DaoInterfaces;

import model.Customer;
import model.Provider;
import model.PurchaseOrder;
import model.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderDao {
    PurchaseOrder findPurchaseOrderById(int id) throws SQLException;
    ArrayList<PurchaseOrder> findAllPurchaseOrders() throws SQLException;
    ArrayList<PurchaseOrder> findPurchaseOrdersByProvider(Provider provider) throws SQLException;
    int createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException;
    boolean updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException;
    boolean deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException;
}
