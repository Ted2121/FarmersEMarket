package test.ControllerTests;

import controller.ControllerFactory;
import controller.ControllerImplementation.CreateSaleOrderControllerImplementation;
import controller.ControllerInterfaces.CreateSaleOrderController;
import controller.ControllerInterfaces.SearchCustomerInterface;
import controller.ControllerInterfaces.SearchProductInterface;
import db_access.DaoFactory;
import model.*;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TestCreateSaleOrderControllerImplementation {
    private static CreateSaleOrderController controller;
    private static SearchProductInterface productSearchControllerPart;
    private static SearchCustomerInterface customerSearchControllerPart;
    private static int quantityOfEachLineItem;

    private static Customer newCustomer;
    private static Product newProduct;

    @BeforeClass
    public static void setUp() {
        controller = ControllerFactory.getCreateSaleOrderController();
        productSearchControllerPart = (SearchProductInterface) controller;
        customerSearchControllerPart = (SearchCustomerInterface) controller;
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

    @Test
    public void testSearchCustomerUsingThisName() {
        String name = "Jo";
        customerSearchControllerPart.customerSearchRefreshData();
        List<Customer> customersUsingTheName = customerSearchControllerPart.searchCustomerUsingThisName(name);
        assertTrue("Should return a list with more than 0 results", customersUsingTheName.size()>0);
    }

    @Test
    public void testSearchProductUsingThisName() {
        String name = "Ca";
        productSearchControllerPart.productSearchRefreshData();
        List<Product> productsUsingTheName = productSearchControllerPart.searchProductUsingThisName(name);
        assertTrue("Should return a list with more than 0 results", productsUsingTheName.size()>0);
    }

    @Test
    public void testAddDeleteProductFromSaleOrder() throws Exception {
        Product product = new Product();
        controller.addProductToSaleOrder(product, 2);
        assertTrue("The product should have been added to the SaleOrder", controller.isProductAlreadyInTheSaleOrder(product));
        controller.deleteProductFromSaleOrder(product);
        assertFalse("The product should have been removed from the SaleOrder", controller.isProductAlreadyInTheSaleOrder(product));

    }

    @Test
    public void testCustomerSearchRefreshData() throws SQLException {
        //Arrange
        newCustomer= ModelFactory.getCustomerModel("testName", "last name", "test", "test", "testAddress", 123);
        DaoFactory.getCustomerDao().createCustomer(newCustomer);

        //Act
        List<Customer> customerListBeforeRefresh = customerSearchControllerPart.searchCustomerUsingThisName("testName");
        customerSearchControllerPart.customerSearchRefreshData();
        List<Customer> customerListAfterRefresh = customerSearchControllerPart.searchCustomerUsingThisName("testName");

        //Assert
        assertTrue("Should be an empty list",customerListBeforeRefresh.isEmpty());
        assertFalse("Shouldn't be an empty list",customerListAfterRefresh.isEmpty());

    }

    @Test
    public void testProductSearchRefreshData() throws SQLException{
        //Arrange
        newProduct = ModelFactory.getProductModel("curiousName", 0, 0, Product.WeightCategory.ONE, Product.Unit.KG);
        DaoFactory.getProductDao().createProduct(newProduct);

        //Act
        List<Product> productListBeforeRefresh = productSearchControllerPart.searchProductUsingThisName("curiousName");
        productSearchControllerPart.productSearchRefreshData();
        List<Product> productListAfterRefresh = productSearchControllerPart.searchProductUsingThisName("curiousName");

        //Assert
        assertTrue("Should be an empty list",productListBeforeRefresh.isEmpty());
        assertFalse("Shouldn't be an empty list",productListAfterRefresh.isEmpty());
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        CreateSaleOrderControllerImplementation controllerImplementation = (CreateSaleOrderControllerImplementation) controller;
        SaleOrder saleOrder = controllerImplementation.getSaleOrder();
        for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(saleOrder, true)) {
            lineItem.setOrder(saleOrder);
            DaoFactory.getLineItemDao().deleteLineItem(lineItem);
            DaoFactory.getProductInformationDao().removeQuantityToProduct(lineItem.getProduct(), quantityOfEachLineItem);
        }

        DaoFactory.getSaleOrderDao().deleteSaleOrder(saleOrder);
        DaoFactory.getOrderDao().deleteOrder(saleOrder);

        if(newCustomer != null)
            DaoFactory.getCustomerDao().deleteCustomer(newCustomer);
        if(newProduct != null)
            DaoFactory.getProductDao().deleteProduct(newProduct);


    }
}
