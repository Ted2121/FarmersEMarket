package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseOrder extends Order{

//    private int FK_Provider;
    private Provider provider;

    public PurchaseOrder(int id, Provider provider) {
        super(id);
        this.provider = provider;
    }

    public PurchaseOrder(Provider provider) {
        super();
        this.provider = provider;
    }

//    public int getFK_Provider() {
//        return FK_Provider;
//    }
//
//    public void setFK_Provider(int FK_Provider) {
//        this.FK_Provider = FK_Provider;
//    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
