package model;

import java.util.ArrayList;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private final String fullName;
    private String address;
    private int postalCode;
    private String city;
    private ArrayList<SaleOrder> saleOrders;

    public Customer(String firstName, String lastName, String address, int postalCode, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.fullName = firstName + " " + lastName;

    }

    public Customer(int id, String firstName, String lastName, String address, int postalCode, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.fullName = firstName + " " + lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // use the full name instead where possible
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<SaleOrder> getSaleOrders() {
        return saleOrders;
    }

    public void setSaleOrders(ArrayList<SaleOrder> saleOrders) {
        this.saleOrders = saleOrders;
    }

    public String getFullName() {
        return fullName;
    }
}
