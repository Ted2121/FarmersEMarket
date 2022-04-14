package db_access.DaoInterfaces;

import model.SaleOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SaleOrderDao {
    SaleOrder findSaleOrderById(int saleOrderId, boolean retrieveCustomer, boolean retrieveLineItem) throws SQLException;
    List<SaleOrder> findAllSaleOrders(boolean retrieveCustomer, boolean retrieveLineItem) throws SQLException;
    void createSaleOrder(SaleOrder objectToInsert) throws SQLException;
    void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException;
    void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException;
}
