package controller.ControllerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.ControllerInterfaces.CreateSaleOrderController;
import controller.ControllerInterfaces.SearchCustomerInterface;
import controller.FastSearchFactory;
import controller.FastSearchHelperClass;
import controller.HelperClass;
import controller.ControllerInterfaces.SearchProductInterface;
import db_access.DBConnection;
import db_access.DaoFactory;
import model.*;

public class CreateSaleOrderControllerImplementation implements CreateSaleOrderController, SearchProductInterface, SearchCustomerInterface {

    private Connection connection;
    private HashMap<Product, Integer> productWithQuantity;
    private SaleOrder saleOrder;
    private FastSearchHelperClass<Product> productFastSearch;
    private FastSearchHelperClass<Customer> customerFastSearch;


    public CreateSaleOrderControllerImplementation() {
        super();
        connection = DBConnection.getInstance().getDBCon();
        productFastSearch = FastSearchFactory.getProductFastSearch();
        customerFastSearch = FastSearchFactory.getCustomerFastSearch();
        //This hashmap will record all the selected products and their quantity
        this.productWithQuantity = new HashMap<Product, Integer>();
    }

    @Override
    public void addProductToSaleOrder(Product product, int quantity) {

        //If we already have this product in the list, we remove it
        if (productWithQuantity.containsKey(product))
            productWithQuantity.remove(product);

        //We add the product in the hashmap with its specific quantity
        productWithQuantity.put(product, quantity);

    }

    @Override
    public void deleteProductFromSaleOrder(Product product) {

        try {
            //If we try to delete a product that isn't contained in the hashmap, we throw an exception
            if (!productWithQuantity.containsKey(product))
                throw new Exception("Cannot delete a product that is not contained in the list");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //We remove the product from the hashmap
        productWithQuantity.remove(product);

    }

    @Override
    public void createSaleOrder(Customer customer) {

        //First, we retrieve a new SaleOrder from the model factory by specifying its customer
        saleOrder = ModelFactory.getSaleOrderModel(customer);

        try {
            //We retrieve the whole product to have its selling price
            HelperClass.exchangeProductSubsetWithQuantityHashMapToCompleteProducts(productWithQuantity);
        } catch (SQLException e) {
            System.out.println("Cannot retrieve the full product object due to database error");
        } catch (Exception e) {
            System.out.println("Cannot retrieve the full product object due to software");
        }


        //Then we calculate the total price of the order and set it (will return 0 if we don't have its selling price)
        saleOrder.setOrderPrice(HelperClass.calculateTotalPrice(productWithQuantity));

        //We set a list of LineItems which contains every generated LineItems from the hashmap
        List<LineItem> generatedLineItems = generateLineItems();

        //We may start the transaction
        try {
            //First we tell the database not to commit what command we will send
            connection.setAutoCommit(false);

            //Then we start to write our commands, using the Dao
            //Creating the Order in the database and setting the id to the saleOrder
            DaoFactory.getOrderDao().createOrder(saleOrder);
            //Creating the SaleOrder in the database
            DaoFactory.getSaleOrderDao().createSaleOrder(saleOrder);
            //For each LineItems
            for (LineItem lineItem : generatedLineItems) {
                //Creating LineItems in the database
                DaoFactory.getLineItemDao().createLineItem(lineItem);
                //Adding the quantity to productInformation in the database
                DaoFactory.getProductInformationDao().addQuantityToProduct(lineItem.getProduct(), lineItem.getQuantity());
            }

            //Once the order, saleOrder and LineItems have been added to the database, we commit all our changes
            connection.commit();


        } catch (SQLException e) {

            //If only one of the previous database methods doesn't work, we print this line
            System.out.println("Error during insertion of Order, SaleOrder or LineItems in the database");
            try {
                //And we try to rollback as if we never tried to start the transaction
                connection.rollback();
                System.out.println("Connection rolled back");
            } catch (SQLException e1) {
                //If the rollback cannot be done, we print this line
                System.out.println("System cannot rollback the database instruction");
            }
        } catch (Exception e) {
            //If there is an error due to the association of each class (Order linked to saleOrder etc...) we print this line
            System.out.println("Cannot set association, not a database error");
            try {
                //And we try to rollback as if we never tried to start the transaction
                connection.rollback();
                System.out.println("Connection rolled back");
            } catch (SQLException e1) {
                //If the rollback cannot be done, we print this line
                System.out.println("System cannot rollback the database instruction");
            }

        } finally {
            //If the transaction have been complete or not, we ask the database to autocommit again
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                //If the autocommit cannot be done, we print this line
                System.out.println("System can't autocommit");
            }
        }

    }

    private List<LineItem> generateLineItems() {
        //First we instantiate a list of LineItems
        ArrayList<LineItem> generatedLineItems = new ArrayList<LineItem>();

        //For each product as a key in the hashmap
        for (Product key : productWithQuantity.keySet()) {
            //We create a LineItem using the ModelFactory
            LineItem lineItem = ModelFactory.getLineItemModel(productWithQuantity.get(key), key, saleOrder);
            //And add it to the list
            generatedLineItems.add(lineItem);
        }
        //Once every LineItems have been created, we return the list
        return generatedLineItems;
    }

    //Created for test reasons
    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    @Override
    public boolean isProductAlreadyInTheSaleOrder(Product product) {
        //We return true if the product is already in the hashmap
        return productWithQuantity.containsKey(product);
    }


    @Override
    public List<Customer> searchCustomerUsingThisName(String customerName) {
        return customerFastSearch.searchUsingThisName(customerName);
    }

    @Override
    public List<Product> searchProductUsingThisName(String productName) {
        return productFastSearch.searchUsingThisName(productName);
    }
}