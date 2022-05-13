package test.DaoTests;

import db_access.DaoFactory;
import db_access.DaoInterfaces.*;
import model.*;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSaleOrderDaoImplementation {

private static int generatedOrderId;

    static SaleOrderDao saleOrderDao;
    static int generatedSaleOrderId;
    static int id = 0;
    static SaleOrder saleOrderToUpdate;
    static SaleOrder saleOrderToDelete;

    @BeforeClass
    public static void setUp() throws Exception {
        saleOrderDao = DaoFactory.getSaleOrderDao();
        Customer testCustomer = DaoFactory.getCustomerDao().findAllCustomers(false).get(0);
        //for debugging
        // System.out.println(testCustomer.getFirstName());

        id = DaoFactory.getOrderDao().createEmptyOrder();

        saleOrderToUpdate = ModelFactory.getSaleOrderModel(id, testCustomer);
        saleOrderDao.createSaleOrder(saleOrderToUpdate);
        id = DaoFactory.getOrderDao().createEmptyOrder();
        saleOrderToDelete = ModelFactory.getSaleOrderModel(id, testCustomer);
        saleOrderDao.createSaleOrder(saleOrderToDelete);

    }

    @Test
    public void testFindAllSaleOrdersWithoutAssociations() throws Exception {
        ArrayList<SaleOrder> retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(false, false);
        assertTrue("list should have at least 1 object", retrievedSaleOrders.size()>0);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNull("Customer of the order should be null", saleOrder.getCustomer());
        }
    }

    @Test
    public void testFindAllSaleOrdersWithCustomerAssociations() throws Exception {
        ArrayList<SaleOrder> retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(true, false);
        assertTrue("list should have at least 1 object", retrievedSaleOrders.size()>0);

        for(SaleOrder saleOrder : retrievedSaleOrders) {
            assertNotNull("Customer should not be null", saleOrder.getCustomer());
        }
    }

    @Test
    public void testFindAllSaleOrdersWithLineItemAssociations() throws Exception {
        ArrayList<SaleOrder> retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(false, true);
        assertTrue("list should have at least 1 object", retrievedSaleOrders.size()>0);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            List<LineItem> lineItems = saleOrder.getLineItems();
            assertNotNull("The lineItems array list should not be null", lineItems);
            for(LineItem lineItem : lineItems) {
                assertNotNull("Shouldn't be null", lineItem);
            }
        }

    }

    @Test
    public void testFindAllSaleOrdersWithAllAssociations() throws Exception {
        ArrayList<SaleOrder> retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(true, true);
        assertTrue("list should have at least 1 object", retrievedSaleOrders.size()>0);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            List<LineItem> lineItems = saleOrder.getLineItems();
            assertNotNull("The lineItems array list should not be null", lineItems);
            for(LineItem lineItem : lineItems) {
                assertNotNull("LineItem shouldn't be null", lineItem);
            }
        }

        for(SaleOrder saleOrder : retrievedSaleOrders) {
            assertNotNull("Customer should not be null", saleOrder.getCustomer());
        }

    }

    @Test
    public void testFindSaleOrderByIdWithoutAssociations() throws Exception {
        SaleOrder retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), false, false);
        assertNotNull("SaleOrder should not be null", retrievedSaleOrder);
    }

    @Test
    public void testFindSaleOrderByIdWithCustomerAssociations() throws Exception {
        SaleOrder retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), true, false);
        assertNotNull("Sale order should not be null", retrievedSaleOrder);
        assertNotNull("Customer of the order should not be null", retrievedSaleOrder.getCustomer());
    }

    @Test
    public void testFindSaleOrderByIdWithLineItemAssociations() throws Exception {
        SaleOrder retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), false, true);
        assertNotNull("Sale order should not be null", retrievedSaleOrder);
        for(LineItem lineItem : retrievedSaleOrder.getLineItems() ) {
            assertNotNull("LineItem shouldn't be null", lineItem);
        }
    }

    @Test
    public void testFindSaleOrderByIdWithAllAssociations() throws Exception {
        SaleOrder retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), true, true);
        assertNotNull("Sale order should not be null", retrievedSaleOrder);
        for(LineItem lineItem : retrievedSaleOrder.getLineItems() ) {
            assertNotNull("LineItem shouldn't be null", lineItem);
        }
        assertNotNull("Customer of the order should not be null", retrievedSaleOrder.getCustomer());
    }

    @Test
    public void testFindSaleOrderByCustomerWithoutAssociations() throws Exception {
        List<SaleOrder> retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, false, false);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNull("Shouldn't have a customer set", saleOrder.getCustomer());
            assertNull("Shouldn't have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testFindSaleOrderByProviderWithCustomerAssociations() throws Exception {
        List<SaleOrder> retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, true, false);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNotNull("Should have a customer set", saleOrder.getCustomer());
            assertNull("Shouldn't have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testFindSaleOrderByCustomerWithLineItemAssociations() throws Exception {
        List<SaleOrder> retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, false, true);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNull("Shouldn't have a customer set", saleOrder.getCustomer());
            assertNotNull("Should have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testFindSaleOrderByCustomerWithAllAssociations() throws Exception {
        List<SaleOrder> retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, true, true);

        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNotNull("Should have a customer set", saleOrder.getCustomer());
            assertNotNull("Should have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testCreateSaleOrder() throws Exception {
        generatedSaleOrderId = DaoFactory.getOrderDao().createEmptyOrder();

        Customer testCustomer = DaoFactory.getCustomerDao().findAllCustomers(false).get(0);
        SaleOrder testSaleOrder = ModelFactory.getSaleOrderModel(generatedSaleOrderId, testCustomer);

        DaoFactory.getSaleOrderDao().createSaleOrder(testSaleOrder);
        SaleOrder retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(generatedSaleOrderId, false, false);

        assertNotNull("Should not return a null object", generatedSaleOrderId);
    }

    @Test
    public void testUpdateOrder() throws Exception {
        saleOrderToUpdate.setOrderPrice(200);
        DaoFactory.getSaleOrderDao().updateSaleOrder(saleOrderToUpdate);
        DaoFactory.getOrderDao().updateOrder(saleOrderToUpdate);
        SaleOrder retrievedUpdatedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), false, false);

        assertTrue("Should return the same price" ,saleOrderToUpdate.getOrderPrice() == retrievedUpdatedSaleOrder.getOrderPrice());
    }

    @Test
    public void testDeleteOrder() throws Exception {

        saleOrderDao.deleteSaleOrder(saleOrderToDelete);
        DaoFactory.getOrderDao().deleteOrder(saleOrderToDelete);
        SaleOrder retrievedDeletedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToDelete.getId(), false, false);
        assertNull("Should return a null object", retrievedDeletedSaleOrder );
    }

    @AfterClass
    public static void cleanUp() throws Exception {

        SaleOrder createdOrder = new SaleOrder(generatedSaleOrderId, null);

        saleOrderDao.deleteSaleOrder(saleOrderToUpdate);
        saleOrderDao.deleteSaleOrder(createdOrder);

        DaoFactory.getOrderDao().deleteOrder(createdOrder);
        DaoFactory.getOrderDao().deleteOrder(saleOrderToDelete);
        DaoFactory.getOrderDao().deleteOrder(saleOrderToUpdate);

        createdOrder = null;
        saleOrderDao = null;
        saleOrderToUpdate = null;
        saleOrderToDelete = null;
    }
}
