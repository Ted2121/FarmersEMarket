package db_access.DaoInterfaces;

import model.SaleOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SaleOrderDao {
    SaleOrder findSaleOrderByCustomerId(int saleOrderId) throws SQLException;
    ArrayList<SaleOrder> findAllSaleOrders() throws SQLException;
    int createSaleOrder(SaleOrder objectToInsert) throws SQLException;
    void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException;
    void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException;
}
