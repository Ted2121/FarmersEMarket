package db_access.DaoInterfaces;

import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LineItemDao {
	LineItem findLineItemByOrderAndProductId(int orderId, int productId, boolean retrieveOrder, boolean retrieveProduct) throws SQLException, Exception;
	List<LineItem> findLineItemsByOrder(Order order, boolean retrieveProduct) throws SQLException, Exception ;
	List<LineItem> findLineItemsByProduct(Product product, boolean retrieveOrder) throws SQLException, Exception;
	List<LineItem> findAllLineItems(boolean retrieveOrder, boolean retrieveProduct) throws SQLException, Exception;
    void createLineItem(LineItem objectToInsert) throws SQLException;
    void updateLineItem(LineItem objectToUpdate) throws SQLException;
    void deleteLineItem(LineItem objectToDelete) throws SQLException;
}
