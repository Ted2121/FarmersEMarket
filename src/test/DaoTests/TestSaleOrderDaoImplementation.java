package test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.SaleOrderDao;
import model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestSaleOrderDaoImplementation {
//    private static SaleOrderDao saleOrderDao;
//    private static int generatedSaleOrderId;
//    private static int generatedOrderId;
//    private static SaleOrder saleOrderToUpdate;
//    private static SaleOrder saleOrderToDelete;
//    private static Customer testCustomer;
private static int generatedOrderId;
    private static int generatedCustomerId;
    private static Product product1;
    private static Product product2;
    private static LineItem lineItem1;
    private static LineItem lineItem2;
    private static Customer customer;
    private static SaleOrder saleOrder;
    private static ArrayList<LineItem> lineItems;
    private static SaleOrder retrievedSaleOrder;

    @BeforeClass
    public static void setUp() throws SQLException {
        product1 = ModelFactory.getProductModelWithoutId("potato", 10.0, 20.0, Product.WeightCategory.FIVE, Product.Unit.KG);
        product2 = ModelFactory.getProductModelWithoutId("tomato", 15.0, 25.0, Product.WeightCategory.ONE, Product.Unit.KG);

        lineItem1 = new LineItem(6, product1);
        lineItem2 = new LineItem(9, product2);

        lineItems = new ArrayList<>();
        lineItems.add(lineItem1);
        lineItems.add(lineItem2);

        customer = ModelFactory.getCustomerModel("John", "Doe", "Aalborg", "Denmark", "testaddress", 9000);
        generatedCustomerId = DaoFactory.getCustomerDao().createCustomer(customer);

        generatedOrderId = DaoFactory.getOrderDao().createEmptyOrder();
        saleOrder = ModelFactory.getSaleOrderModel(generatedOrderId);
        saleOrder.setCustomer(DaoFactory.getCustomerDao().findCustomerById(generatedCustomerId));
        saleOrder.setLineItems(lineItems);

    }

    @Test
    public void testInsertingAnOrderToDB() throws Exception {
        DaoFactory.getSaleOrderDao().createSaleOrder(saleOrder);
    }

    @Test
    public void testRetrievingAnOrderFromDB() throws Exception {
        retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(generatedOrderId, true, true);
        assertNotNull("was null", retrievedSaleOrder);
    }

    @Test
    public void testCheckingFirstLineItemOfTheRetrievedSaleOrder() throws Exception{
    	retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(generatedOrderId, true, true);
        assertEquals("potato", retrievedSaleOrder.getLineItems().get(0).getProduct().getProductName());
    }

    @Test
    public void testCheckingCustomerFullNameOfTheSaleOrder() throws Exception{
    	retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(generatedOrderId, true, true);
        assertEquals("John Doe", retrievedSaleOrder.getCustomer().getFullName());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DaoFactory.getSaleOrderDao().deleteSaleOrder(saleOrder);
        DaoFactory.getOrderDao().deleteOrder(saleOrder);
        DaoFactory.getCustomerDao().deleteCustomer(customer);
        product1 = null;
        product2 = null;
        lineItem1 = null;
        lineItem2 = null;
        saleOrder = null;
        retrievedSaleOrder = null;
        lineItems = null;
        customer = null;
    }

}
