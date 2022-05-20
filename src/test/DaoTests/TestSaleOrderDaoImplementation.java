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

        id = DaoFactory.getOrderDao().createEmptyOrder();

        saleOrderToUpdate = ModelFactory.getSaleOrderModel(id, testCustomer);
        saleOrderDao.createSaleOrder(saleOrderToUpdate);
        id = DaoFactory.getOrderDao().createEmptyOrder();
        saleOrderToDelete = ModelFactory.getSaleOrderModel(id, testCustomer);
        saleOrderDao.createSaleOrder(saleOrderToDelete);

    }

    @Test
    public void testFindAllSaleOrdersWithoutAssociations() throws Exception {
        // Arrange
        ArrayList<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(false, false);

        // Assert
        assertTrue("list should have at least 1 object", retrievedSaleOrders.size()>0);


    }

    @Test
    public void testFindAllSaleOrdersWithCustomerAssociations() throws Exception {
        // Arrange
        ArrayList<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(true, false);

        // Assert
        assertTrue("list should have at least 1 object", retrievedSaleOrders.size()>0);

    }

    @Test
    public void testFindAllSaleOrdersWithLineItemAssociations() throws Exception {
        // Arrange
        ArrayList<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(false, true);

        // Assert
        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            List<LineItem> lineItems = saleOrder.getLineItems();
            assertNotNull("The lineItems array list should not be null", lineItems);

        }

    }

    @Test
    public void testFindAllSaleOrdersWithAllAssociations() throws Exception {
        // Arrange
        ArrayList<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = (ArrayList<SaleOrder>) DaoFactory.getSaleOrderDao().findAllSaleOrders(true, true);


        // Assert
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
        // Arrange
        SaleOrder retrievedSaleOrder;

        // Act
        retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), false, false);

        // Assert
        assertNotNull("SaleOrder should not be null", retrievedSaleOrder);
    }

    @Test
    public void testFindSaleOrderByIdWithCustomerAssociations() throws Exception {
        // Arrange
        SaleOrder retrievedSaleOrder;

        // Act
        retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), true, false);

        // Assert
        assertNotNull("Customer of the order should not be null", retrievedSaleOrder.getCustomer());
    }

    @Test
    public void testFindSaleOrderByIdWithLineItemAssociations() throws Exception {
        // Arrange
        SaleOrder retrievedSaleOrder;

        // Act
        retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), false, true);

        // Assert
        for(LineItem lineItem : retrievedSaleOrder.getLineItems() ) {
            assertNotNull("LineItem shouldn't be null", lineItem);
        }
    }

    @Test
    public void testFindSaleOrderByIdWithAllAssociations() throws Exception {
        // Arrange
        SaleOrder retrievedSaleOrder;

        // Act
        retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), true, true);

        // Assert
        for(LineItem lineItem : retrievedSaleOrder.getLineItems() ) {
            assertNotNull("LineItem shouldn't be null", lineItem);
        }
        assertNotNull("Customer of the order should not be null", retrievedSaleOrder.getCustomer());
    }

    @Test
    public void testFindSaleOrderByCustomerWithoutAssociations() throws Exception {
        // Arrange
        List<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, false, false);

        // Assert
        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNull("Shouldn't have a customer set", saleOrder.getCustomer());
            assertNull("Shouldn't have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testFindSaleOrderByProviderWithCustomerAssociations() throws Exception {
        // Arrange
        List<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, true, false);

        // Assert
        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNotNull("Should have a customer set", saleOrder.getCustomer());
        }
    }

    @Test
    public void testFindSaleOrderByCustomerWithLineItemAssociations() throws Exception {
        // Arrange
        List<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, false, true);

        // Assert
        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNull("Shouldn't have a customer set", saleOrder.getCustomer());
            assertNotNull("Should have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testFindSaleOrderByCustomerWithAllAssociations() throws Exception {
        // Arrange
        List<SaleOrder> retrievedSaleOrders;

        // Act
        retrievedSaleOrders = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(1, true, true);

        // Assert
        for(SaleOrder saleOrder : retrievedSaleOrders ) {
            assertNotNull("Should have a customer set", saleOrder.getCustomer());
            assertNotNull("Should have lineItems set", saleOrder.getLineItems());
        }
    }

    @Test
    public void testCreateSaleOrder() throws Exception {
        // Arrange
        generatedSaleOrderId = DaoFactory.getOrderDao().createEmptyOrder();
        Customer testCustomer;
        SaleOrder testSaleOrder;
        testCustomer = DaoFactory.getCustomerDao().findAllCustomers(false).get(0);
        testSaleOrder = ModelFactory.getSaleOrderModel(generatedSaleOrderId, testCustomer);

        // Act
        DaoFactory.getSaleOrderDao().createSaleOrder(testSaleOrder);
        SaleOrder retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(generatedSaleOrderId, false, false);

        // Assert
        assertNotNull("Should not return a null object", generatedSaleOrderId);
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Arrange
        SaleOrder retrievedUpdatedSaleOrder;

        // Act
        saleOrderToUpdate.setOrderPrice(200);
        DaoFactory.getSaleOrderDao().updateSaleOrder(saleOrderToUpdate);
        DaoFactory.getOrderDao().updateOrder(saleOrderToUpdate);
        retrievedUpdatedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToUpdate.getId(), false, false);

        // Assert
        assertTrue("Should return the same price" ,saleOrderToUpdate.getOrderPrice() == retrievedUpdatedSaleOrder.getOrderPrice());
    }

    @Test
    public void testDeleteOrder() throws Exception {

        // Arrange
        SaleOrder retrievedDeletedSaleOrder;

        // Act
        saleOrderDao.deleteSaleOrder(saleOrderToDelete);
        DaoFactory.getOrderDao().deleteOrder(saleOrderToDelete);
        retrievedDeletedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(saleOrderToDelete.getId(), false, false);
        // Assert
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

    }
}
