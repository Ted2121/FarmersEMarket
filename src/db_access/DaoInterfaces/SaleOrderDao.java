package db_access.DaoInterfaces;

import model.Customer;
import model.Order;
import model.SaleOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SaleOrderDao {
    Order findSaleOrderByCustomerId(int saleOrderId) throws SQLException;
    ArrayList<Order> findAllSaleOrders() throws SQLException;
    int createSaleOrder(SaleOrder objectToInsert) throws SQLException;
    void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException;
    void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException;
}
