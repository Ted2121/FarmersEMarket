package db_access.DaoInterfaces;

import model.LineItem;
import model.Product;
import model.SaleOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LineItemDao {
    LineItem findLineItemById(int idSaleOrder, int idProduct) throws SQLException;
    ArrayList<LineItem> findLineItemsBySaleOrder(SaleOrder saleOrder) throws SQLException;
    ArrayList<LineItem> findLineItemsByProduct(Product product) throws SQLException;
    ArrayList<LineItem> findAllLineItems() throws SQLException;
    boolean createLineItem(LineItem objectToInsert) throws SQLException;
    void updateLineItem(LineItem objectToUpdate) throws SQLException;
    void deleteLineItem(LineItem objectToDelete) throws SQLException;
}
