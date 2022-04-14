package model;

public class LineItem {
    private int quantity;
    private Product product;
    private Order order;


    public LineItem(int quantity, Product product, Order order) {
        this(quantity, product);
        this.order = order;
    }

    public LineItem(int quantity, Product product) {
        this(quantity);
        this.product = product;
    }
    
    public LineItem(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
