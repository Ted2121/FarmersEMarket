package model;

public class LineItem {
    private int quantity;
    private Product product;
    private SaleOrder saleOrder;
    private PurchaseOrder purchaseOrder;

    public LineItem(int quantity, Product product, SaleOrder saleOrder) {
        this.quantity = quantity;
        this.product = product;
        this.saleOrder = saleOrder;
    }

    public LineItem(int quantity, Product product, PurchaseOrder purchaseOrder) {
        this.quantity = quantity;
        this.product = product;
        this.purchaseOrder = purchaseOrder;
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

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
