package unofficialTest;

import db_access.DaoFactory;
import model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestGettingAnOrderFromDB {
    private int generatedOrderId;
    private int generatedCustomerId;
    private Product product1;
    private Product product2;
    private LineItem lineItem1;
    private LineItem lineItem2;
    private Customer customer;
    private SaleOrder saleOrder;
    private ArrayList<LineItem> lineItems;
    private SaleOrder retrievedSaleOrder;

    @BeforeClass
    public void setUp() throws SQLException {
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
    public void testInsertingAnOrderToDB() throws SQLException {
        DaoFactory.getSaleOrderDao().createSaleOrder(saleOrder);
    }

    @Test
    public void testRetrievingAnOrderFromDB() throws SQLException {
        retrievedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(generatedOrderId);
    }

    @Test
    public void testCheckingFirstLineItemOfTheRetrievedSaleOrder(){
        assertEquals("potato", retrievedSaleOrder.getLineItems().get(0).getProduct().getProductName());
    }

    @Test
    public void testCheckingCustomerFullNameOfTheSaleOrder(){
        assertEquals("John Doe", retrievedSaleOrder.getCustomer().getFullName());
    }

    @AfterClass
    public void tearDown() throws SQLException {
        DaoFactory.getSaleOrderDao().deleteSaleOrder(saleOrder);
        // getOrderDao().deleteOrder() should not take in an Order because it's an abstract class, and we never create one
        // maybe it should take in an int id, so I can pass in the generated id to delete it
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
