package model;


public class SaleOrder extends Order{

    private Customer customer;

    public SaleOrder(int id, Customer Customer) {
        super(id);
        this.customer = Customer;
    }

    public SaleOrder(Customer Customer) {
        super();
        this.customer = Customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
