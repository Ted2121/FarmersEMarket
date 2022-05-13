package model;


public class SaleOrder extends Order{

    private Customer customer;

    public SaleOrder(int id, Customer customer) {
        super(id);
        this.customer = customer;
    }

    public SaleOrder(Customer customer) {
        super();
        this.customer = customer;
    }

    public SaleOrder(int id){
        super(id);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
