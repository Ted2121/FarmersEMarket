package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaleOrder {
    private int id;
    private String orderDateTime;
    private int FK_Customer;
    // price is the total amount
    // price = the sum of all lineItems(productPrice * quantity) in the order
    private double price;
    private ArrayList<LineItem> lineItems;
    private Customer customer;

    public SaleOrder(double price, Customer customer) {
        this.orderDateTime = LocalDateTime.now().format(HelperClassForModel.getFormat());
        this.price = price;
        this.customer = customer;
    }

    public SaleOrder(int id, double price, Customer customer) {
        this.id = id;
        this.orderDateTime = LocalDateTime.now().format(HelperClassForModel.getFormat());
        this.price = price;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public int getFK_Customer() {
        return FK_Customer;
    }

    public void setFK_Customer(int FK_Customer) {
        this.FK_Customer = FK_Customer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
