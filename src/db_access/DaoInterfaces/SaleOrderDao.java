package db_access.DaoInterfaces;

import model.Customer;
import model.SaleOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SaleOrderDao {
    SaleOrder findSaleOrderById(int saleOrderId) throws SQLException;
    ArrayList<SaleOrder> findAllSaleOrders() throws SQLException;
    //TODO do we need it?
    //ArrayList<SaleOrder> findSaleOrdersByCustomer(Customer customer) throws SQLException;
    int createSaleOrder(SaleOrder objectToInsert) throws SQLException;
    void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException;
    void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException;
}
