package test.ControllerTests;

import controller.ControllerFactory;
import controller.ControllerImplementation.CreateSaleOrderControllerImplementation;
import controller.ControllerInterfaces.CreateSaleOrderController;
import db_access.DaoFactory;
import model.*;
import org.junit.*;
import static org.junit.Assert.assertNotEquals;

public class TestCreateSaleOrderControllerImplementation {
    private static CreateSaleOrderController controller;
    private static int quantityOfEachLineItem;

    @BeforeClass
    public static void setUp() {
        controller = ControllerFactory.getCreateSaleOrderController();
        quantityOfEachLineItem = 3;
    }

    @Test
    public void testCreateSaleOrderTransaction() throws Exception {
        int numberOfSaleOrdersBeforeTest = DaoFactory.getSaleOrderDao().findAllSaleOrders(false, false).size();

        for(Product product : DaoFactory.getProductDao().findAllProducts(false, false)) {
            controller.addProductToSaleOrder(product, quantityOfEachLineItem);
        }
        Customer customer = DaoFactory.getCustomerDao().findAllCustomers(false).get(0);

        controller.createSaleOrder(customer);
        int numberOfSaleOrdersAfterTest = DaoFactory.getSaleOrderDao().findAllSaleOrders(false, false).size();

        assertNotEquals("Should retrieve 1 more SaleOrder", numberOfSaleOrdersBeforeTest, numberOfSaleOrdersAfterTest);
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        CreateSaleOrderControllerImplementation controllerImplementation = (CreateSaleOrderControllerImplementation) controller;
        SaleOrder saleOrder = controllerImplementation.getSaleOrder();
        for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(saleOrder, true)) {
            lineItem.setOrder(saleOrder);
            DaoFactory.getLineItemDao().deleteLineItem(lineItem);
            DaoFactory.getProductInformationDao().addQuantityToProduct(lineItem.getProduct(), quantityOfEachLineItem);
        }

        DaoFactory.getSaleOrderDao().deleteSaleOrder(saleOrder);
        DaoFactory.getOrderDao().deleteOrder(saleOrder);

    }
}
