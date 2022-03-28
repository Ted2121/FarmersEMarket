package model;

import java.util.HashMap;

// singleton for storage
public class Storage {
    private static HashMap<Product, Integer> productsInStorage = null;

    private Storage(){}

    public static HashMap<Product, Integer> getInstance(){
        if(productsInStorage == null){
            productsInStorage = new HashMap<Product, Integer>();
        }

        return productsInStorage;
    }
}
