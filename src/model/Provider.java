package model;

import java.util.ArrayList;

public class Provider extends Person{
    // this array list contains the history of purchase orders of one provider
    private ArrayList<PurchaseOrder> purchaseOrders;

    public Provider(int id, String firstName, String lastName, String city, String country) {
        super(id, firstName, lastName, city, country);
    }

    public Provider(String firstName, String lastName, String city, String country) {
        super(firstName, lastName, city, country);
    }
    
    public Provider(int id, String firstName, String lastName) {
    	super(id, firstName, lastName);
	}

	public ArrayList<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(ArrayList<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

	@Override
	public String toString() {
		return super.toString();
	}
    
    

}
