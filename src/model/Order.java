package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Order {
    private int id;
    private String orderDateTime;
    // price is the total amount
    // price = the sum of all lineItems(productPrice * quantity) in the order
    private double orderPrice;
    private ArrayList<LineItem> lineItems;

    public Order(int id) {
        this.id = id;
        this.orderDateTime = LocalDateTime.now().format(ModelFactory.getFormat());
    }

    public Order() {
        this.orderDateTime = LocalDateTime.now().format(ModelFactory.getFormat());
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

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
