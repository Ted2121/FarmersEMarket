package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseOrder {
    private int id;
    private String orderDateTime;
    private int FK_Provider;
    // price is the total amount
    // price = the sum of all lineItems(productPrice * quantity) in the order
    private double price;
    private ArrayList<LineItem> lineItems;
    private Provider provider;

    public PurchaseOrder(double price, Provider provider) {
        this.orderDateTime = LocalDateTime.now().format(HelperClassForModel.getFormat());
        this.price = price;
        this.provider = provider;
    }

    public PurchaseOrder(int id, double price, Provider provider) {
        this.id = id;
        this.orderDateTime = LocalDateTime.now().format(HelperClassForModel.getFormat());
        this.price = price;
        this.provider = provider;
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

    public int getFK_Provider() {
        return FK_Provider;
    }

    public void setFK_Provider(int FK_Provider) {
        this.FK_Provider = FK_Provider;
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
