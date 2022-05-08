package model;

import java.util.ArrayList;

public class Customer extends Person{

    private String address;
    private int postalCode;
    private ArrayList<SaleOrder> saleOrders;


    public Customer(String firstName, String lastName, String city, String country, String address, int postalCode) {
        super(firstName, lastName, city, country);
        this.address = address;
        this.postalCode = postalCode;
    }

    public Customer(int id, String firstName, String lastName, String city, String country, String address, int postalCode) {
    	this( firstName,  lastName,  city,  country,  address,  postalCode);
        super.setId(id);
    }

    public Customer(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public ArrayList<SaleOrder> getSaleOrders() {
        return saleOrders;
    }

    public void setSaleOrders(ArrayList<SaleOrder> saleOrders) {
        this.saleOrders = saleOrders;
    }

}
