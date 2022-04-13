package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseOrder extends Order{


    private Provider provider;

    public PurchaseOrder(int id, Provider provider) {
        super(id);
        this.provider = provider;
    }

    public PurchaseOrder(int id) {
        super(id);
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
