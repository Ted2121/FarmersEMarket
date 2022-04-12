package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaleOrder extends Order{
    private int FK_Customer;
    private Customer customer;

    public SaleOrder(int id, Customer Customer) {
        super(id);
        this.customer = Customer;
    }

    public SaleOrder(Customer Customer) {
        super();
        this.customer = Customer;
    }

    public int getFK_Customer() {
        return FK_Customer;
    }

    public void setFK_Customer(int FK_Customer) {
        this.FK_Customer = FK_Customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
