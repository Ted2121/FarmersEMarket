package db_access.DaoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Order;
import model.SaleOrder;

public interface OrderDao {
    void createOrder(Order objectToCreate) throws SQLException;
    int createEmptyOrder() throws SQLException;
    void updateOrder(Order objectToUpdate) throws SQLException;
    void deleteOrder(Order objectToDelete) throws SQLException, Exception;
    
}
