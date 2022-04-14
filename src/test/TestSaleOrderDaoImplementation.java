package test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.SaleOrderDao;
import model.*;
import org.junit.BeforeClass;

import java.sql.SQLException;

public class TestSaleOrderDaoImplementation {
    static SaleOrderDao saleOrderDao;
    static int generatedSaleOrderId;
    static int generatedOrderId;
    static SaleOrder saleOrderToUpdate;
    static SaleOrder saleOrderToDelete;

    @BeforeClass
    public static void setUp() throws SQLException {
        Customer testCustomer = DaoFactory.getCustomerDao().findAllCustomers().get(0);

        generatedOrderId = DaoFactory.getOrderDao().createEmptyOrder();

        saleOrderToUpdate = ModelFactory.getSaleOrderModel(generatedOrderId, testCustomer);
        saleOrderDao.createSaleOrder(saleOrderToUpdate);

        generatedOrderId = DaoFactory.getOrderDao().createEmptyOrder();
        saleOrderToDelete = ModelFactory.getSaleOrderModel(generatedOrderId, testCustomer);
        saleOrderDao.createSaleOrder(saleOrderToDelete);
    }
}
