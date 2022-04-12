package model;


import db_access.DaoImplementation.CustomerDaoImplementation;
import db_access.DaoImplementation.ProviderDaoImplementation;

import java.time.format.DateTimeFormatter;

public class ModelFactory {
    private ModelFactory(){}

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public static DateTimeFormatter getFormat() {
        return FORMAT;
    }

    public static LineItem createLineItem(){
        LineItem lineItem = new LineItem();
    }

    public static Person getProvider(int id, String firstName, String lastName, String city, String country){
        return new Provider(id, firstName, lastName, city, country);
}

    public static Person getCustomer(int id, String firstName, String lastName, String city, String country, String address, int postalCode){

         return new Customer(id, firstName, lastName, city, country, address, postalCode);
 }
}
