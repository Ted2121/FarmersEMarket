package db_access.DaoInterfaces;

import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LineItemDao {
    LineItem findLineItemByOrderId(int orderId, int productId) throws SQLException;
    ArrayList<LineItem> findLineItemsByOrder(Order order) throws SQLException;
    ArrayList<LineItem> findLineItemsByProduct(Product product) throws SQLException;
    ArrayList<LineItem> findAllLineItems() throws SQLException;
    boolean createLineItem(LineItem objectToInsert) throws SQLException;
    void updateLineItem(LineItem objectToUpdate) throws SQLException;
    void deleteLineItem(LineItem objectToDelete) throws SQLException;
}
