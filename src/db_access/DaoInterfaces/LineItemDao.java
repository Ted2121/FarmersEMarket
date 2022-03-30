package db_access.DaoInterfaces;

import model.LineItem;
import model.Product;
import model.PurchaseOrder;
import model.SaleOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LineItemDao {
    LineItem findLineItemBySaleOrderId(int saleOrderId, int productId) throws SQLException;
    LineItem findLineItemByPurchaseOrderId(int purchaseOrderId, int productId) throws SQLException;
    ArrayList<LineItem> findLineItemsBySaleOrder(SaleOrder saleOrder) throws SQLException;
    ArrayList<LineItem> findLineItemsByPurchaseOrder(PurchaseOrder purchaseOrder) throws SQLException;
    ArrayList<LineItem> findLineItemsByProduct(Product product) throws SQLException;
    ArrayList<LineItem> findAllLineItems() throws SQLException;
    boolean createLineItemForSaleOrder(LineItem objectToInsert) throws SQLException;
    boolean createLineItemForPurchaseOrder(LineItem objectToInsert) throws SQLException;
    void updateLineItem(LineItem objectToUpdate) throws SQLException;
    void deleteLineItem(LineItem objectToDelete) throws SQLException;
}
