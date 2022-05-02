package controller.ControllerImplementation;

import controller.ControllerInterfaces.CreateSaleOrderController;
import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoImplementation.CustomerDaoImplementation;
import db_access.DaoInterfaces.CustomerDao;
import model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class CreateSaleOrderControllerImplementation implements CreateSaleOrderController {
    private HashMap<Product, Integer> productWithQuantity;
    private SaleOrder saleOrder;
    private Connection connection;
    private CustomerDao customerDao;

public CreateSaleOrderControllerImplementation() {

        this.productWithQuantity = new HashMap<>();
        connection = DBConnection.getInstance().getDBCon();
        customerDao = DaoFactory.getCustomerDao();
        }

    @Override
    public void addProductToSaleOrder(Product product, int quantity) {
        if(productWithQuantity.containsKey(product)) {
            int oldQuantity = productWithQuantity.get(product);
            productWithQuantity.replace(product, oldQuantity - quantity);
        }else {
        	productWithQuantity.put(product, quantity);
        }
    }

    @Override
    public void createSaleOrder(Customer customer) {
        this.saleOrder = ModelFactory.getSaleOrderModel(customer);
        saleOrder.setOrderPrice(calculateTotalPrice());
        ArrayList<LineItem> generatedLineItems = (ArrayList<LineItem>) generateLineItems();

        //Transaction
        try {
            connection.setAutoCommit(false);

            //Creating the Order in the database and setting the id to the saleOrder
            DaoFactory.getOrderDao().createOrder(saleOrder);
            //Creating the SaleOrder in the database
            DaoFactory.getSaleOrderDao().createSaleOrder(saleOrder);
            //For each LineItems
            for(LineItem lineItem : generatedLineItems) {
                //Creating LineItems in the database
                DaoFactory.getLineItemDao().createLineItem(lineItem);
                //Subtracting the quantity from the productInformation in the database
                DaoFactory.getProductInformationDao().removeQuantityToProduct(lineItem.getProduct(), lineItem.getQuantity());
            }

            connection.commit();


        }catch(SQLException e ) {
            System.out.println("Error during insertion of Order, PurchaseOrder or LineItems in the database");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("System cannot rollback the database instruction");
            }
        }catch(Exception e) {
            System.out.println("Cannot set association, not a database error");
            try {
                connection.rollback();
            }catch (SQLException e1) {
                System.out.println("System cannot rollback the database instruction");
            }

        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("System can't autocommit");
            }
        }

    }

    private Object generateLineItems() {
        ArrayList<LineItem> generatedLineItems = new ArrayList<LineItem>();
        for(Product key : productWithQuantity.keySet()) {

            LineItem lineItem = ModelFactory.getLineItemModel(productWithQuantity.get(key), key, saleOrder);
            generatedLineItems.add(lineItem);
        }
        return generatedLineItems;
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for(Product key : productWithQuantity.keySet()) {
            totalPrice += key.getSellingPrice() * productWithQuantity.get(key);
        }
        return totalPrice;
    }

    public Customer findCustomerByFullName(String fullName) throws SQLException {
        return customerDao.findCustomerByFullName(fullName);
    }

    public List<Customer> findAllCustomersWithThisName(String fullName)  {
    CustomerDao customerDao = DaoFactory.getCustomerDao();
        List<Customer> customerListMatchingTheSearch = null;
        try {
            customerListMatchingTheSearch = customerDao.findAllCustomersWithThisName(fullName, false);
        } catch (SQLException e) {
            System.out.println("Cannot retrieve provider in the Database");
        } catch (Exception e) {
            System.out.println("Error during provider search process");
        }
        return customerListMatchingTheSearch;
    }

    //Created for test reasons
    public SaleOrder getSaleOrder() {
        return saleOrder;
    }
}
