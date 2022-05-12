package db_access.DaoInterfaces;

import model.SaleOrder;

import java.util.ArrayList;
import java.util.List;

public interface SaleOrderDao {
    SaleOrder findSaleOrderById(int saleOrderId, boolean retrieveCustomer, boolean retrieveLineItem) throws Exception;
    List<SaleOrder> findAllSaleOrders(boolean retrieveCustomer, boolean retrieveLineItem) throws Exception;
    void createSaleOrder(SaleOrder objectToInsert) throws Exception;
    void updateSaleOrder(SaleOrder objectToUpdate) throws Exception;
    void deleteSaleOrder(SaleOrder objectToDelete) throws Exception;
    List<SaleOrder> findSaleOrderByCustomerId(int customerId, boolean retrieveProvider, boolean retrieveLineItem) throws Exception;
}
