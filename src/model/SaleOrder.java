package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaleOrder {
    private int id;
    private String orderDate;
    private int FK_Customer;
    private double price;
    private ArrayList<LineItem> lineItems;
    private Customer customer;

    public SaleOrder(String orderDate, double price, Customer customer) {
        this.orderDate = LocalDateTime.now().format(HelperClassForModel.getFormat());
        this.price = price;
        this.customer = customer;
    }

    public SaleOrder(int id, String orderDate, double price, Customer customer) {
        this.id = id;
        this.orderDate = LocalDateTime.now().format(HelperClassForModel.getFormat());
        this.price = price;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
