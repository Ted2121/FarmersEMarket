package model;

import java.util.ArrayList;

public class Provider extends Person{
    // this array list contains the history of purchase orders of one provider
    private ArrayList<PurchaseOrder> purchaseOrders;

    public Provider(int id, String firstName, String lastName, String city) {
        super(id, firstName, lastName, city);
    }

    public Provider(String firstName, String lastName, String city) {
        super(firstName, lastName, city);
    }

    public ArrayList<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(ArrayList<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

}
