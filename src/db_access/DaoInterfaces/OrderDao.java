package db_access.DaoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import model.SaleOrder;

public interface OrderDao {
    int createOrder(SaleOrder objectToInsert) throws SQLException;
    void updateOrder(SaleOrder objectToUpdate) throws SQLException;
    void deleteOrder(SaleOrder objectToDelete) throws SQLException;
}
